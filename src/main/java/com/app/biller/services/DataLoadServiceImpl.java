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
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.app.biller.dao.ILCDataDao;
import com.app.biller.model.ILCData;
import com.microsoft.schemas.office.visio.x2012.main.CellType;

@Component
public class DataLoadServiceImpl implements DataLoadService {

	private static final Logger logger = LoggerFactory.getLogger(DataLoadServiceImpl.class);

	@Autowired
	ILCDataDao ilcDataDao;

	@Override
	public String uploadFiles(MultipartHttpServletRequest request) {
		// File Upload directory on Server.
		// String uploadRootPath = request.getServletContext().getRealPath("uploads");
		String uploadRootPath = "C:/invoice/uploads";
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
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "Success";
	}

	@Override
	public String loadILCData(String billCycle, String dataType, String userId) {
		createILCDataSheet();
		ilcDataDao.insertILCData(extractILCData(), billCycle, dataType, userId);
		return "ILC Report generated successfully";
	}

	private void createILCDataSheet() {

		// String[] command = { "cmd.exe", "/C", "Start",
		// "C:\\Users\\IBM_ADMIN\\Desktop\\Workbench\\trigger.bat" };
		String[] command = { "cmd.exe", "/C", "Start", "C:\\invoice\\uploads\\trigger.bat" };
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(command);
			Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
		} catch (IOException e) {
			e.printStackTrace();
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

		try {
			ilcInput = new FileInputStream(new File("C:\\invoice\\uploads\\FFIC ILC Report.xlsx"));
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
						numColVal = Long.toString(Math
								.round(ilcSheet.getRow(curRow).getCell(cell.getColumnIndex()).getNumericCellValue()));
						rowData.put(numColName, numColVal);
						break;
					default:
						break;
					}
				}
				curRow++;
				curRowData = ilcSheet.getRow(curRow).getCell(0).getStringCellValue();
				dataExists = (curRowData != "");
				ilcData = populateILCDataModel(rowData, ilcData);
				ilcDataList.add(ilcData);
				rowData = null;
				ilcData = null;
			}

			return ilcDataList;

		} catch (FileNotFoundException fnfe) {
			logger.info("FileNotFoundException: " + fnfe.getStackTrace());
			return ilcDataList;
		} catch (Exception e) {
			logger.info("Exception: " + e.getStackTrace());
			return ilcDataList;
		} finally {
			try {
				ilcBook.close();
				ilcInput.close();
			} catch (Exception e) {
				logger.info("Exception occured while closing: " + e);
			}
		}
	}

	private ILCData populateILCDataModel(Map<String, String> rowData, ILCData ilcModel) {

		ilcModel.setEmpID(rowData.get("Emp Ser Num"));
		ilcModel.setEmpName(rowData.get("EMPLOYEE_NAME"));
		ilcModel.setClaimCode(rowData.get("Work Item"));
		ilcModel.setActivity(rowData.get("Activity"));
		ilcModel.setWeekEndDate(rowData.get("Week Ending Date"));
		ilcModel.setTotHrs(Integer.parseInt(rowData.get("Total Hrs")));
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
		return ilcModel;
	}

}
