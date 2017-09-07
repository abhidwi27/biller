package com.app.biller.dao;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.model.ILCData;

public interface ILCDataDao {

	void createILCData(ArrayList<ILCData> ilcModelList, String billCycle, String userId);	
	
	ArrayList<ILCData> readILCData(String billCycle, String towerID);
	
	ArrayList<ILCData> readCustomILCData(String billCycle, int towerID, String weekEndData, String wrNo, String empName);
	
	List<String> getEmployeeList(String billCycle);
	
	List<String> getWRList(String billCycle);
	
	List<String> getWeekendList(String billCycle);
	
	
}
