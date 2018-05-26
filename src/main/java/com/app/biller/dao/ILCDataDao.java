package com.app.biller.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.app.biller.domain.ILCData;

public interface ILCDataDao {

	void createILCData(ArrayList<ILCData> ilcModelList, String billCycle, String userId, String uploadDataType) throws DataAccessException;	

	ArrayList<ILCData> readILCData(String billCycle, int towerID , int accountId);

	ArrayList<ILCData> readCustomILCData(String billCycle, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3, String [] val3, int key4, String [] val4, int key5, String [] val5);

	List<String> getEmployeeList(String billCycle,int towerID);

	List<String> getWRList(String billCycle, int towerID);

	List<String> getWeekendList(String billCycle, int towerID);
	
	List<String> getRemarksList(String billCycle, int towerID);
	
	List<String> getLevel1Values(String billCycle, int towerID, int accountId, int key1);
	
	List<String> getLevel2Values(String billCycle, int towerID, int accountId, int key1, String [] val1, int key2);
	
	List<String> getLevel3Values(String billCycle, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3);
	
	List<String> getLevel4Values(String billCycle, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3, String [] val3, int key4);
	
	List<String> getLevel5Values(String billCycle, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3, String [] val3, int key4, String [] val4, int key5);
}
