package com.app.biller.dao;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.domain.ILCData;
import com.app.biller.domain.SLAData;

public interface SLADataDao {
	
	void createSLAData(ArrayList<SLAData> slaModelList, String billCycle, String userId, String uploadDataType);

	ArrayList<SLAData> readSLAData(String billCycle, int towerID);

	void updateSLAData(String billCycle, ArrayList<SLAData> records);

	void deleteSLAData(String billCycle, List<Integer> seqIDList);

	void createNewSLARecord(String billCycle, ArrayList<SLAData> records);

	List<SLAData> readCustomSLAData(String billCycle, int towerID, String weekEndDate, String wrNo, String empName);

	List<String> getEmployeeList(String billCycle, int towerID);

	List<String> getWRList(String billCycle, int towerID);

	List<String> getWeekendList(String billCycle, int towerID);
	
	String getActiveBillCycle();
}
