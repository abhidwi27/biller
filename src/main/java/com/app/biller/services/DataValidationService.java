package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.domain.ILCData;
import com.app.biller.domain.SLAData;

public interface DataValidationService {

	List<ILCData> readILCData(String billCycle, int towerID);

	List<ILCData> readCustomILCData(String billCycle, int towerID, String weekEndDate, String wrNo, String empName);

	List<SLAData> readSLAData(String billCycle, int towerID);

	List<SLAData> readCustomSLAData(String billCycle, int towerID, String weekEndDate, String wrNo, String empName);

	String updateSLAData(String billCycle,  int towerID, String userID, ArrayList<?> records);

	String deleteSLAData(String billCycle, int recordID[]);

	String createNewSLARecord(String billCycle, String userID, ArrayList<?> records);	
}
