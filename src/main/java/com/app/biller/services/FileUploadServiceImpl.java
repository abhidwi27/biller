package com.app.biller.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import com.app.biller.dao.WIASMDataDao;
import com.app.biller.domain.WIASMData;
import com.app.biller.dao.ITWRDataDao;
import com.app.biller.domain.ITWRData;
import com.app.biller.dao.ILCDataDao;
import com.app.biller.domain.ILCData;
import com.app.biller.dao.SLADataDao;
import com.app.biller.domain.SLAData;
import com.microsoft.schemas.office.visio.x2012.main.CellType;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

	@Autowired
	ILCDataDao ilcDataDao;
	
	@Autowired
	SLADataDao slaDataDao;
	
	@Autowired
	ITWRDataDao itwrDataDao;
	
	@Autowired
	WIASMDataDao wiasmDataDao;
	
	@Autowired
	DataApprovalService dataApprovalService;
	
	
	@Override
	public String uploadFiles(MultipartHttpServletRequest request) throws Exception {
		String fileUploadResult = null;
		logger.info("Uploading Files...");
		String newpath = System.getProperty("user.dir");
		String rootdir[] = newpath.split("\\\\",2);
		String uploadRootPath = rootdir[0] + "\\billerData\\Uploads";
		File uploadRootDir = new File(uploadRootPath);

		// Create directory if it does not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		Iterator<String> itr = request.getFileNames();
		List<MultipartFile> files = request.getFiles(itr.next());
		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {
				String fileName = multipartFile.getOriginalFilename();
				// Create the file on server
				File excelFile = new File(uploadRootDir.getAbsolutePath() + File.separator + fileName);
				try {
					multipartFile.transferTo(excelFile);
					logger.info("Files uploaded successfully..");
					fileUploadResult = "Success";
				} catch (Exception e) {
					logger.error("Exception occured while uploading files", e);
					throw new Exception(e);
				}
			}
		}
		return fileUploadResult;
	}

	@Override
	public String uploadILCData(String billCycle, String userId, String uploadDataType,String reportWeekend) throws DataAccessException {
		createILCDataSheet(reportWeekend,uploadDataType);
		
		ilcDataDao.createILCData(extractILCData(), billCycle, userId, uploadDataType);
		itwrDataDao.createITWRData(extractITWRData(), billCycle, userId, uploadDataType);
		wiasmDataDao.uploadWIASMData(extractWIASMData(), billCycle, userId, uploadDataType);
		
		return "ILC Report generated successfully";
	}

	@Override
	public String uploadSLAData(String billCycle, String userId,String uploadDataType,String reportWeekend) throws Exception {
		createILCDataSheet(reportWeekend,uploadDataType);

		ArrayList<SLAData> slaDataList = extractSLAData();

		slaDataDao.createSLAData(slaDataList, billCycle, userId, uploadDataType);
		dataApprovalService.createGroupApproval(billCycle, userId);

		return "SLA Report generated successfully";
	}

	private void createILCDataSheet(String reportWeekend, String uploadDataType) {
		logger.info("Creating ILC Datasheet..");
		String[] command = { "cmd.exe", "/C", "Start","/wait", "C:\\biller\\src\\main\\webapp\\Workbench\\trigger.bat",
				reportWeekend, uploadDataType };
		Process process = null;
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		try {
			process = processBuilder.start();
			process.waitFor();
			//int exitStatus = process.waitFor();
			Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
			logger.info("ILC Data sheet created successfully");
		} catch (InterruptedException e) {
			logger.error("InterruptedException: ", e);
			//e.printStackTrace();
		} catch (IOException e) {
			logger.error("IOException: ", e);
			//e.printStackTrace();
		} finally {
			process.destroy();
		}
	}

	private ArrayList<ILCData> extractILCData() {

		FileInputStream ilcInput = null;
		XSSFWorkbook ilcBook = null;
		XSSFSheet ilcSheet;
		XSSFRow row;
		ArrayList<ILCData> ilcDataList = null;
		Map<String, String> rowData;
		Iterator<Row> rowIterator;
		Iterator<Cell> cellIterator;
		ILCData ilcData;
		Cell cell;
		DataFormatter formatter;
		String colName;
		String colVal;
		String numColName;
		String numColVal;
		CellType BLANK;
		boolean dataExists;
		String curRowData;
		int curRow;
		int cellType;
		logger.info("Extracting ILC Data..");
		try {
			//ilcInput = new FileInputStream(new File("C:\\invoice\\uploads\\FFIC ILC Report.xlsx"));
			ilcInput = new FileInputStream(new File("C:\\biller\\src\\main\\webapp\\Workbench\\FFIC ILC Report.xlsx"));
			ilcBook = new XSSFWorkbook(ilcInput);
			ilcSheet = ilcBook.getSheet("WNPPT");
			rowIterator = ilcSheet.iterator();
			ilcDataList = new ArrayList<ILCData>();
			dataExists = true;
			curRow = 1;

			while (dataExists) {
				row = (XSSFRow) rowIterator.next();
				cellIterator = row.cellIterator();
				rowData = new HashMap<String, String>();
				ilcData = new ILCData();

				while (cellIterator.hasNext()) {
					cell = cellIterator.next();
					cellType = ilcSheet.getRow(curRow).getCell(cell.getColumnIndex()).getCellType();
					switch (cellType) {
					case 1:
						colName = (ilcSheet.getRow(0).getCell(cell.getColumnIndex()).getStringCellValue()).trim();
						colVal = (ilcSheet.getRow(curRow).getCell(cell.getColumnIndex()).getStringCellValue()).trim();
						rowData.put(colName, colVal);
						break;
					case 0:
						numColName = ilcSheet.getRow(0).getCell(cell.getColumnIndex()).getStringCellValue();
						if(numColName.equals("Total Hrs") ) {
						numColVal = Double.toString(Math.abs(ilcSheet.getRow(curRow).getCell(cell.getColumnIndex()).getNumericCellValue()));
						}else {
							numColVal = Long.toString(Math.round(ilcSheet.getRow(curRow).getCell(cell.getColumnIndex()).getNumericCellValue()));
						}
						rowData.put(numColName, numColVal);
						break;
					default:
						break;
					}
				}
				ilcData = populateILCDataModel(rowData, ilcData);
				ilcDataList.add(ilcData);
				curRow++;
				//curRowData = ilcSheet.getRow(curRow).getCell(0).getStringCellValue();
				Row curRowDataType = ilcSheet.getRow(curRow);
				dataExists = (curRowDataType != null);
				rowData = null;
				ilcData = null;
			}
			logger.info("ILC Data extracted successfully");		

		} catch (FileNotFoundException fnfe) {
			logger.error("FileNotFoundException: ", fnfe);
			
		} catch (NullPointerException npe) {
			logger.info("Ignoring NullPointerException in Create ILC Data");
			
		}catch (IOException ioe) {
			logger.error("IOException: " , ioe);
			
		}finally {
			try {
				ilcBook.close();
				ilcInput.close();
			} catch (IOException e) {
				logger.error("Exception occured while closing: " , e);
			}
		}		
		return ilcDataList;
	}

	
	
	private ArrayList<SLAData> extractSLAData() throws Exception{
		
		
		FileInputStream slaInput = null;
		XSSFWorkbook slaBook = null;
		XSSFSheet slaSheet;
		XSSFRow row;
		ArrayList<SLAData> sladatalist=null;
		Map<String, String> rowData;
		Iterator<Row> rowIterator;
		Iterator<Cell> cellIterator;
		SLAData slaData;
		Cell cell;
		DataFormatter formatter;
		String colName;
		String colVal;
		String numColName;
		String numColVal;
		CellType BLANK;
		boolean dataExists;
		String curRowData;
		int curRow;
		int cellType;
		logger.info("Extracting SLA Data...");
		try {
			//ilcInput = new FileInputStream(new File("C:\\invoice\\uploads\\FFIC ILC Report.xlsx"));
			slaInput = new FileInputStream(new File("C:\\biller\\src\\main\\webapp\\Workbench\\FFIC SLA Report.xlsx"));
			slaBook = new XSSFWorkbook(slaInput);
			slaSheet = slaBook.getSheet("WNPPT - Billed Hours");
			rowIterator = slaSheet.iterator();
			sladatalist = new ArrayList<SLAData>();
			dataExists = true;
			curRow = 1;

			while (dataExists) {
				row = (XSSFRow) rowIterator.next();
				cellIterator = row.cellIterator();
				rowData = new HashMap<String, String>();
				slaData = new SLAData();

				while (cellIterator.hasNext()) {
					cell = cellIterator.next();
					cellType = slaSheet.getRow(curRow).getCell(cell.getColumnIndex()).getCellType();
					switch (cellType) {
					case 1:
						colName = (slaSheet.getRow(0).getCell(cell.getColumnIndex()).getStringCellValue()).trim();
						colVal = (slaSheet.getRow(curRow).getCell(cell.getColumnIndex()).getStringCellValue()).trim();
						rowData.put(colName, colVal);
						break;
					case 0:
						numColName = slaSheet.getRow(0).getCell(cell.getColumnIndex()).getStringCellValue();
						if(numColName.equals("Hours") ) {
							numColVal = Double.toString(Math.abs(slaSheet.getRow(curRow).getCell(cell.getColumnIndex()).getNumericCellValue()));
							}else {
						numColVal = Long.toString(Math
								.round(slaSheet.getRow(curRow).getCell(cell.getColumnIndex()).getNumericCellValue()));
							}
						rowData.put(numColName, numColVal);
						break;
					default:
						break;
					}
				}
				
				slaData = populateSLADataModel(rowData, slaData);				
				sladatalist.add(slaData);
				curRow++;
				//curRowData = slaSheet.getRow(curRow).getCell(0).getStringCellValue();
				Row curRowDataType = slaSheet.getRow(curRow);
				dataExists = (curRowDataType != null);
				rowData = null;
				slaData = null;
			}			
			logger.info("SLA  Data extracted successfully..");
			
		} catch (FileNotFoundException fnfe) {
			logger.info("FileNotFoundException: ", fnfe);
			
		}catch (NullPointerException npe) {
			logger.info("NullPointerException: " , npe);
			
		}catch (IOException ioe) {
			logger.error("IOException: " , ioe);
			
		} finally {
			try {
				slaBook.close();
				slaInput.close();
			} catch (Exception e) {
				logger.info("Exception occured while closing: " + e);
			}
		}
		return sladatalist;
		
	}
	
	
	
	
	
	private SLAData populateSLADataModel(Map<String,String> rowData, SLAData slaModel) {
		
		slaModel.setWeekEndDate(rowData.get("Week ending"));
		slaModel.setAsm(rowData.get("ASM"));
		slaModel.setAsd(rowData.get("ASD"));
		slaModel.setAsmItwr(rowData.get("ASM (ITWR)"));
		slaModel.setAsdItwr(rowData.get("ASD (ITWR)"));
		slaModel.setEmpID(rowData.get("Emp ID"));
		slaModel.setEmpName(rowData.get("Emp Name"));
		slaModel.setActivity(rowData.get("Activity Description"));
		slaModel.setWorkItem(rowData.get("Workitem ID"));
		slaModel.setOnOffShore(rowData.get("On/Offshore"));
		slaModel.setTotHrs(Double.parseDouble(rowData.get("Hours")));
		slaModel.setShiftDetail(rowData.get("ShiftDetails"));
		slaModel.setCategory(rowData.get("Category"));
		slaModel.setBillType(rowData.get("Bill Type"));
		slaModel.setDm(rowData.get("exculsively on"));
		slaModel.setAppArea(rowData.get("APP Area"));
		slaModel.setBusinessArea(rowData.get("BA"));
		slaModel.setTower(rowData.get("Tower"));
		slaModel.setBam(rowData.get("BAMs"));
		slaModel.setRemarks(rowData.get("Remarks"));
		slaModel.setIsBillable(rowData.get("Billable"));
		slaModel.setWrNo(rowData.get("WR"));
		slaModel.setPlannedHrs(Integer.parseInt(rowData.get("Plan Effort")));
		slaModel.setComments(rowData.get("Comments"));
		slaModel.setWrDesc(rowData.get("ITWR Description"));
		slaModel.setCostCenter(rowData.get("Cost Centre"));
		slaModel.setFundType(rowData.get("Funding Type"));
		slaModel.setVendorClass(rowData.get("Vendor Classification"));
		slaModel.setAccountId(rowData.get("Account Id"));
				
		
		return slaModel;
		
	}
	
	private ILCData populateILCDataModel(Map<String, String> rowData, ILCData ilcModel) {

		ilcModel.setEmpID(rowData.get("Emp Ser Num"));
		ilcModel.setEmpName(rowData.get("EMPLOYEE_NAME"));
		ilcModel.setClaimCode(rowData.get("Work Item"));
		ilcModel.setActivity(rowData.get("Activity"));
		ilcModel.setWeekEndDate(rowData.get("Week Ending Date"));
		//ilcModel.setTotHrs(Integer.parseInt(rowData.get("Total Hrs")));
		ilcModel.setTotHrs(Double.parseDouble(rowData.get("Total Hrs")));
		ilcModel.setShiftType(rowData.get("ShiftDetails"));
		ilcModel.setUsInd(rowData.get("US/INDIA"));
		ilcModel.setOnOffShore(rowData.get("On/Offshore"));
		ilcModel.setBillingType(rowData.get("Billing Type"));
		ilcModel.setCategory(rowData.get("Category"));
		ilcModel.setBam(rowData.get("BAM"));
		ilcModel.setAppArea(rowData.get("APP AREA"));
		ilcModel.setBusinessArea(rowData.get("Business Area"));
		ilcModel.setMonth(rowData.get("Month"));
		ilcModel.setQuarter(rowData.get("Quarter"));
		ilcModel.setDm(rowData.get("DM"));
		ilcModel.setAsm(rowData.get("ASM"));
		ilcModel.setAsd(rowData.get("ASD"));
		ilcModel.setWrNo(rowData.get("WR #"));
		ilcModel.setIsTicket(rowData.get("Is Ticket ?"));
		ilcModel.setStaffType(rowData.get("BASE/Staff Aug"));
		ilcModel.setIsCTC(rowData.get("CTC WR"));
		ilcModel.setIsRTC(rowData.get("RTC WR"));
		ilcModel.setPlannedHrs(Integer.parseInt(rowData.get("Planned Hours")));
		ilcModel.setIsBillable(rowData.get("Billable?"));
		ilcModel.setRemarks(rowData.get("Remarks"));
		ilcModel.setCtcOrRtc(rowData.get("CTC/RTC"));
		ilcModel.setWorkType(rowData.get("Work Type"));
		ilcModel.setWrDesc(rowData.get("WR Description"));
		ilcModel.setCostCenter(rowData.get("Cost Center"));
		ilcModel.setCategory2(rowData.get("Category 2"));
		ilcModel.setOnOffLanded(rowData.get("OnOffLanded"));
		ilcModel.setTower(rowData.get("Tower"));
		ilcModel.setAsmItwr(rowData.get("ASM (ITWR)"));
		ilcModel.setAsdItwr(rowData.get("ASD (ITWR)"));
		ilcModel.setItwrActual(Integer.parseInt(rowData.get("ITWR Actuals")));
		ilcModel.setGroupType(rowData.get("Group"));
		ilcModel.setVendorClass(rowData.get("Vendor Classification"));
		ilcModel.setWrIncDef(rowData.get("WR/INC/DEF"));
		ilcModel.setAccountId(rowData.get("Account ID"));
		return ilcModel;
	}

private ArrayList<ITWRData> extractITWRData(){
		
		FileInputStream itwrInput = null;
		XSSFWorkbook itwrBook = null;
		XSSFSheet itwrSheet;
		XSSFRow row;
		ArrayList<ITWRData> itwrModellist=null;
		Map<String, String> rowDataItwr;
		Iterator<Row> rowIteratorItwr;
		Iterator<Cell> cellIteratorItwr;
		ITWRData itwrData;
		Cell cellItwr;
		DataFormatter formatter;
		String colName;
		String colVal;
		String numColName;
		String numColVal;
		int nullcheck;
		CellType BLANK;
		boolean dataExists;
		String curRowData;
		int curRow;
		int cellType;
		logger.info("Extracting ITWR Data..");

		try {
			//ilcInput = new FileInputStream(new File("C:\\invoice\\uploads\\FFIC ILC Report.xlsx"));
			itwrInput = new FileInputStream(new File("C:\\billerData\\Uploads\\ITWR for ILC.xlsx"));
			itwrBook = new XSSFWorkbook(itwrInput);
			itwrSheet = itwrBook.getSheet("Sheet1");
			rowIteratorItwr = itwrSheet.iterator();
			itwrModellist = new ArrayList<ITWRData>();
			dataExists = true;
			curRow = 1;

			while (dataExists) {
				row = (XSSFRow) rowIteratorItwr.next();
				cellIteratorItwr = row.cellIterator();
				rowDataItwr = new HashMap<String, String>();
				itwrData = new ITWRData();

				while (cellIteratorItwr.hasNext()) {
					cellItwr = cellIteratorItwr.next();
					//int cell1= itwrSheet.getRow(curRow).getCell(cell.getColumnIndex()).CELL_TYPE_BLANK;
					//cellType = itwrSheet.getRow(curRow).getCell(cell.getColumnIndex()).getCellType();
					
					//nullcheck = itwrSheet.getRow(curRow).getCell(cellItwr.getColumnIndex()).CELL_TYPE_BLANK;
					
					cellType = itwrSheet.getRow(curRow).getCell(cellItwr.getColumnIndex()).getCellType();
					
					switch (cellType) {
					case 1:
						colName = (itwrSheet.getRow(0).getCell(cellItwr.getColumnIndex()).getStringCellValue()).trim();
						colVal = (itwrSheet.getRow(curRow).getCell(cellItwr.getColumnIndex()).getStringCellValue()).trim();
						rowDataItwr.put(colName, colVal);
						break;
					case 0:
						numColName = itwrSheet.getRow(0).getCell(cellItwr.getColumnIndex()).getStringCellValue();
						
						numColVal = Long.toString(Math
								.round(itwrSheet.getRow(curRow).getCell(cellItwr.getColumnIndex()).getNumericCellValue()));
							
						rowDataItwr.put(numColName, numColVal);
						break;
					case 3:
						colName = (itwrSheet.getRow(0).getCell(cellItwr.getColumnIndex()).getStringCellValue()).trim();
						colVal = "NA";
						rowDataItwr.put(colName, colVal);
						break;
					default:
						break;
					}
				}
				
				itwrData = populateITWRDataModel(rowDataItwr, itwrData);				
				itwrModellist.add(itwrData);
				curRow++;
				//curRowData = slaSheet.getRow(curRow).getCell(0).getStringCellValue();
				Row curRowDataType = itwrSheet.getRow(curRow);
				dataExists = (curRowDataType != null);
				rowDataItwr = null;
				itwrData = null;
			}
			logger.info("ITWR Data extracted..");
		} catch (FileNotFoundException fnfe) {
			logger.info("FileNotFoundException: ", fnfe);
			
		}catch (NullPointerException npe) {
			logger.info("Ignoring NullPointerException in Create ITWR Data");
			
		}catch (IOException ioe) {
			logger.error("IOException: " , ioe);
			
		} finally {
			try {
				itwrBook.close();
				itwrInput.close();
			} catch (Exception e) {
				logger.info("Exception occured while closing: " + e);
			}
		}
		return itwrModellist;
	}

	private ITWRData populateITWRDataModel(Map<String, String> rowData, ITWRData itwrModel) {
		
		itwrModel.setReq_no(rowData.get("Request #"));
		itwrModel.setReq_title(rowData.get("Request Title"));
		itwrModel.setCoo_intake_no(rowData.get("COO Intake #"));
		itwrModel.setIt_sme(rowData.get("Business/IT SME"));
		itwrModel.setBus_area(rowData.get("Business Area"));
		itwrModel.setWork_type(rowData.get("WorkType"));
		itwrModel.setDemand_type(rowData.get("Demand Type"));
		itwrModel.setFund_type(rowData.get("Funding Type"));
		itwrModel.setCost_center(rowData.get("Cost Center for Allocation Costs"));
		itwrModel.setVendor_class(rowData.get("Vendor Classification"));
		itwrModel.setAsm(rowData.get("Primary Resource Manager"));
		itwrModel.setPrimary_vendor(rowData.get("Primary Vendor"));
		itwrModel.setAsd(rowData.get("Primary Director"));
		itwrModel.setOverall_status(rowData.get("Overall Status Indicator"));
		itwrModel.setVendor_est_hours(rowData.get("Vendor Total Effort Hours (Estimated)"));
		itwrModel.setVendor_actual_hours(rowData.get("Vendor Total Effort Hours (Actual)"));
		itwrModel.setPcr_est_hours(rowData.get("PCR's Total Effort Hours (Estimated)"));
		itwrModel.setPcr_actual_hours(rowData.get("PCR's Total Effort Hours (Actual)"));
		
		return itwrModel;
	}



	
	
	private ArrayList<WIASMData> extractWIASMData() {

		FileInputStream wiASMInput = null;
		XSSFWorkbook wiASMBook = null;
		XSSFSheet wiASMSheet;
		XSSFRow row;
		ArrayList<WIASMData> wiASMDataList = null;
		Map<String, String> rowData;
		Iterator<Row> rowIterator;
		Iterator<Cell> cellIterator;
		WIASMData wiASMData;
		Cell cell;
		DataFormatter formatter;
		String colName;
		String colVal;
		String numColName;
		String numColVal;
		CellType BLANK;
		boolean dataExists;
		String curRowData;
		int curRow;
		int cellType;
		logger.info("Extracting WIASM Data...");
		try {
			//ilcInput = new FileInputStream(new File("C:\\invoice\\uploads\\FFIC ILC Report.xlsx"));
			wiASMInput = new FileInputStream(new File("C:\\billerData\\Uploads\\WI Vs ASM.xlsx"));
			wiASMBook = new XSSFWorkbook(wiASMInput);
			wiASMSheet = wiASMBook.getSheet("WNPPT New WI");
			rowIterator = wiASMSheet.iterator();
			wiASMDataList = new ArrayList<WIASMData>();
			dataExists = true;
			curRow = 1;

			while (dataExists) {
				row = (XSSFRow) rowIterator.next();
				cellIterator = row.cellIterator();
				rowData = new HashMap<String, String>();
				wiASMData = new WIASMData();

				while (cellIterator.hasNext()) {
					cell = cellIterator.next();
					cellType = wiASMSheet.getRow(curRow).getCell(cell.getColumnIndex()).getCellType();
					switch (cellType) {
					case 1:
						colName = (wiASMSheet.getRow(0).getCell(cell.getColumnIndex()).getStringCellValue()).trim();
						colVal = (wiASMSheet.getRow(curRow).getCell(cell.getColumnIndex()).getStringCellValue()).trim();
						rowData.put(colName, colVal);
						break;
					case 0:
						numColName = wiASMSheet.getRow(0).getCell(cell.getColumnIndex()).getStringCellValue();
						if(numColName.equals("Total Hrs") ) {
						numColVal = Double.toString(Math.abs(wiASMSheet.getRow(curRow).getCell(cell.getColumnIndex()).getNumericCellValue()));
						}else {
							numColVal = Long.toString(Math.round(wiASMSheet.getRow(curRow).getCell(cell.getColumnIndex()).getNumericCellValue()));
						}
						rowData.put(numColName, numColVal);
						break;
					default:
						break;
					}
				}
				wiASMData = populateWIASMDataModel(rowData, wiASMData);
				wiASMDataList.add(wiASMData);
				curRow++;
				//curRowData = wiASMSheet.getRow(curRow).getCell(0).getStringCellValue();
				Row curRowDataType = wiASMSheet.getRow(curRow);
				dataExists = (curRowDataType != null);
				rowData = null;
				wiASMData = null;
			}
			logger.info("WIASM Data extracted ...");
		} catch (FileNotFoundException fnfe) {
			logger.info("FileNotFoundException: " + fnfe.getStackTrace());
			
		} catch (NullPointerException npe) {
			logger.info("Ignoring NullPointerException in Create WIASM Data");
			
		}catch (IOException ioe) {
			logger.error("IOException: " , ioe);
			
		} finally {
			try {
				wiASMBook.close();
				wiASMInput.close();
			} catch (Exception e) {
				logger.info("Exception occured while closing: " + e);
			}
		}
		return wiASMDataList;
	}
	
	
	
	private WIASMData populateWIASMDataModel(Map<String, String> rowData, WIASMData wiASMModel) {

		wiASMModel.setAcc_id(rowData.get("Account ID"));
		wiASMModel.setWrkItem_id(rowData.get("Work Item ID"));
		wiASMModel.setWrkItem_desc(rowData.get("Work Item Description"));
		wiASMModel.setCategory(rowData.get("Category"));
		wiASMModel.setOn_off_shore(rowData.get("On/ Off shore"));
		wiASMModel.setBilling_type(rowData.get("Billing Type"));
		wiASMModel.setApplication(rowData.get("Application"));
		wiASMModel.setBuss_area(rowData.get("Business Area"));
		wiASMModel.setBam(rowData.get("Business Area Manager"));
		wiASMModel.setDm(rowData.get("Delivery Manager"));
		wiASMModel.setAsm(rowData.get("ASM"));
		wiASMModel.setAsd(rowData.get("ASD"));
		
		return wiASMModel;
	}



}





