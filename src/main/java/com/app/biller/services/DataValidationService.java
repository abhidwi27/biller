package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.domain.ILCData;
import com.app.biller.domain.Record;
import com.app.biller.domain.SLAData;
import com.app.biller.domain.SaveRecords;

public interface DataValidationService {

	List<ILCData> readILCData(String billCycle, int towerID , String accountId);

	List<ILCData> readCustomILCData(String billCycle, int towerID, String weekEndDate, String wrNo, String empName);

	List<SLAData> readSLAData(String billCycle, int towerID);

	List<SLAData> readCustomSLAData(String billCycle, int towerID, String weekEndDate, String wrNo, String empName);

	void updateSLAData(String billCycle,  String userID, ArrayList<Record> records);

	void deleteSLAData(String billCycle, List<Integer> seqIDList);

	void createNewSLARecord(String billCycle, String userID, ArrayList<Record> records);	
}
