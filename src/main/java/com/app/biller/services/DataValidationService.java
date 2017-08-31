package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;
import com.app.biller.model.ILCData;
import com.app.biller.model.SLAData;

public interface DataValidationService {

	List<ILCData> readILCData(String billCycle, String towerID);

	List<ILCData> readCustomILCData(String billCycle, String towerID, String weekEndDate[], String wrNo[], String empID[]);

	List<SLAData> readSLAData(String billCycle, String towerID);

	List<SLAData> readCustomSLAData(String billCycle, String towerID, String weekEndDate[], String wrNo[], String empID[]);

	String updateSLAData(String billCycle,  String towerID, String userID, ArrayList<?> records);

	String deleteSLAData(String billCycle, int recordID[]);

	String createNewSLARecord(String billCycle, String userID, ArrayList<?> records);
}
