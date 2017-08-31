package com.app.biller.dao;

import java.util.ArrayList;
import java.util.List;

public interface SLADataDao {

	List<?> readSLAData(String billCycle, String towerID);
	
	String updateSLAData(String billCycle, String towerID, ArrayList<?> records);

	String deleteSLAData(String billCycle, int seqID[]);

	String createNewSLARecord(String billCycle, String userID, ArrayList<?> records);

	List<?> readCustomSLAData(String billCycle, String towerID, String weekEndDate[], String wrNo[], String empID[], String modifiedBy[]);
}
