package com.app.biller.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.biller.dao.ILCDataDao;
import com.app.biller.dao.SLADataDao;
import com.app.biller.domain.ILCData;
import com.app.biller.domain.Record;
import com.app.biller.domain.SLAData;
import com.app.biller.domain.SaveRecords;

@Service
public class DataValidationServiceImpl implements DataValidationService {

	private static final Logger logger = LoggerFactory.getLogger(DataValidationServiceImpl.class);

	@Autowired
	ILCDataDao ilcDataDao;

	@Autowired
	SLADataDao slaDataDao;

	@Override
	public ArrayList<ILCData> readILCData(String billCycle, int towerID , String accountId) {
		return ilcDataDao.readILCData(billCycle, towerID , accountId);

	}

	@Override
	public ArrayList<SLAData> readSLAData(String billCycle, int towerID) {
		return slaDataDao.readSLAData(billCycle, towerID);
	}

	@Override
	public void deleteSLAData(String billCycle, List<Integer> seqIDList) {
		slaDataDao.deleteSLAData(billCycle, seqIDList);
	}

	@Override
	public void updateSLAData(String billCycle, String userID, ArrayList<Record> updateRecords) {
		ArrayList<SLAData> updateSLADataList = GetSLADataListfromRecords("update", updateRecords, userID);
		slaDataDao.updateSLAData(billCycle, updateSLADataList);
		
		
	}

	@Override
	public void createNewSLARecord(String billCycle, String userID, ArrayList<Record> newRecords) {
		ArrayList<SLAData> newSLADataList = GetSLADataListfromRecords("new", newRecords, userID);
		slaDataDao.createNewSLARecord(billCycle,newSLADataList);		

	}

	@Override
	public List<ILCData> readCustomILCData(String billCycle, int towerID, String weekEndDate, String wrNo,
			String empName) {

		return ilcDataDao.readCustomILCData(billCycle, towerID, weekEndDate, wrNo, empName);
	}

	@Override
	public List<SLAData> readCustomSLAData(String billCycle, int towerID, String weekEndDate, String wrNo,
			String empName) {
		return slaDataDao.readCustomSLAData(billCycle, towerID, weekEndDate, wrNo, empName);
	}
	
	private ArrayList<SLAData> GetSLADataListfromRecords(String recordType, ArrayList<Record> records, String userID){
		
		ArrayList<SLAData> slaDataList = new ArrayList<SLAData>() ;
		SLAData slaData = new SLAData();
		String[] rowData;
				
			for(Record record : records) {
				rowData = record.getRowData();
				if(recordType.equals("update")) {
					slaData.setSeqID(Integer.parseInt(rowData[0]));
				}
				slaData.setWeekEndDate(rowData[1]);
				slaData.setAsm(rowData[2]);
				slaData.setAsd(rowData[3]);
				slaData.setAsmItwr(rowData[4]);
				slaData.setAsdItwr(rowData[5]);
				slaData.setEmpID(rowData[6]);
				slaData.setEmpName(rowData[7]);
				slaData.setActivity(rowData[8]);
				slaData.setWorkItem(rowData[9]);
				slaData.setOnOffShore(rowData[10]);
				slaData.setTotHrs(Integer.parseInt(rowData[11]));
				slaData.setShiftDetail(rowData[12]);
				slaData.setCategory(rowData[13]);
				slaData.setBillType(rowData[14]);
				slaData.setDm(rowData[15]);
				slaData.setAppArea(rowData[16]);
				slaData.setBusinessArea(rowData[17]);
				slaData.setTower(rowData[18]);
				slaData.setBam(rowData[19]);
				slaData.setRemarks(rowData[20]);
				slaData.setIsBillable(rowData[21]);
				slaData.setWrNo(rowData[22]);
				slaData.setPlannedHrs(Integer.parseInt(rowData[23]));
				slaData.setComments(rowData[24]);
				slaData.setWrDesc(rowData[25]);
				slaData.setCostCenter(rowData[26]);
				slaData.setFundType(rowData[27]);
				slaData.setVendorClass(rowData[28]);				
				slaData.setChangeLog("");
				slaData.setActive(1);
				slaData.setModifiedBy(userID);
				
				slaDataList.add(slaData);				
				rowData = null;
				slaData = null;			
				
			} 			
			return slaDataList;
			
			
		
		
	}
	
}
