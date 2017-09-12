package com.app.biller.dao;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.domain.SLAData;

public interface SLADataDao {

	ArrayList<SLAData> readSLAData(String billCycle, int towerID);

	void updateSLAData(String billCycle, int towerID, ArrayList<SLAData> records);

	String deleteSLAData(String billCycle, int seqID[]);

	void createNewSLARecord(String billCycle, String userID, ArrayList<SLAData> records);

	List<SLAData> readCustomSLAData(String billCycle, int towerID, String weekEndDate, String wrNo, String empName);

	List<String> getEmployeeList(String billCycle);

	List<String> getWRList(String billCycle);

	List<String> getWeekendList(String billCycle);
}
