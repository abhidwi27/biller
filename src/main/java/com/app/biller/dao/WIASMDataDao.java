package com.app.biller.dao;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.domain.WIASMData;

public interface WIASMDataDao {

	void uploadWIASMData(ArrayList<WIASMData> wiASMModelList, String billCycle, String userId, String uploadDataType);	

	//ArrayList<ILCData> readILCData(String billCycle, int towerID , int accountId);

	/*ArrayList<ILCData> readCustomILCData(String billCycle, int towerID, String weekEndData, String wrNo,
			String empName, int billable, String remarks, int accountId);*/

	/*List<String> getEmployeeList(String billCycle,int towerID);

	List<String> getWRList(String billCycle, int towerID);

	List<String> getWeekendList(String billCycle, int towerID);
	
	List<String> getRemarksList(String billCycle, int towerID); */
}