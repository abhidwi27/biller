package com.app.biller.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.app.biller.domain.ILCData;

public interface ILCDataDao {

	void createILCData(ArrayList<ILCData> ilcModelList, String billCycle, String userId, String uploadDataType) throws DataAccessException;	

	ArrayList<ILCData> readILCData(String billCycle, int towerID , int accountId);

	ArrayList<ILCData> readCustomILCData(String billCycle, int towerID, String weekEndData, String wrNo,
			String empName, int billable, String remarks, int accountId);

	List<String> getEmployeeList(String billCycle,int towerID);

	List<String> getWRList(String billCycle, int towerID);

	List<String> getWeekendList(String billCycle, int towerID);
	
	List<String> getRemarksList(String billCycle, int towerID);
}
