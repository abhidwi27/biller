package com.app.biller.dao;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.domain.SLAData;

public interface SLADataDao {

	ArrayList<SLAData> readSLAData(String billCycle, String towerID);

	void updateSLAData(String billCycle, String towerID, ArrayList<SLAData> records);

	String deleteSLAData(String billCycle, int seqID[]);

	void createNewSLARecord(String billCycle, String userID, ArrayList<SLAData> records);

	List<?> readCustomSLAData(String billCycle, String towerID, String weekEndDate, String wrNo, String empName);

	List<String> getEmployeeList(String billCycle);

	List<String> getWRList(String billCycle);

	List<String> getWeekendList(String billCycle);
}
