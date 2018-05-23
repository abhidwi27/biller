package com.app.biller.dao;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.domain.SLAData;

public interface SLADataDao {
	
	void createSLAData(ArrayList<SLAData> slaModelList, String billCycle, String userId, String uploadDataType) throws Exception;

	ArrayList<SLAData> readSLAData(String billCycle, int towerID, int accountId);
	
	String getModifiedBy(int seqId);

	void updateSLAData(String billCycle, ArrayList<SLAData> records);

	void deleteSLAData(String billCycle, List<Integer> seqIDList);

	void createNewSLARecord(String billCycle, ArrayList<SLAData> records);

	List<SLAData> readCustomSLAData(String billCycle, int towerID, String weekEndDate, String wrNo, String empName, int billable, String remarks, int accountId);

	List<String> getEmployeeList(String billCycle, int towerID);

	List<String> getWRList(String billCycle, int towerID);

	List<String> getWeekendList(String billCycle, int towerID);
	
	List<String> getRemarksList(String billCycle, int towerID);
	
	String getActiveBillCycle();
}
