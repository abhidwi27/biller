package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.domain.Account;
import com.app.biller.domain.Header;
import com.app.biller.domain.Month;
import com.app.biller.domain.Tower;
import com.app.biller.domain.User;
import com.app.biller.ui.ItwrReference;
import com.app.biller.ui.WIASMReference;

public interface ReferenceDataService {
	
	List<String> getEmployeeList(String billCycle, int dataType, int towerID);	
	
	List<String> getWRList(String billCycle, int dataType, int towerID);

	List<Tower> getTowerList();
	
	List<String> getWeekendList(String billCycle, int dataType, int towerID);
	
	List<String> getRemarksList(String billCycle, int dataType, int towerID);
		
	ArrayList<Month> getMonth();
	
	List<String> getYear();
	
	List<String> getTableHeader(int dataType);
	
	String getActiveBillCycle();
	
	List<User> getDelegateUserList(String userID);
	
	String getMonthForBillCycle(String billCycle);
	
	List<Account> getAccountList();
	
	List<ItwrReference> getItwrReferenceData(String wrNo);
	
	List<String> getBulkUpdateData(String billCycle, int headerId);
	
	List<Header> getHeaderForBulkUpdate();
	

	List<WIASMReference> getwiasmReferenceData(String wrNo);

	List<Header> getHeaderForCustomise();
	
	List<String> getLevel1Values(String billCycle, int dataType, int towerID, int accountId, int key1);
	
	List<String> getLevel2Values(String billCycle, int dataType, int towerID, int accountId, int key1, String [] val1, int key2);
	
	List<String> getLevel3Values(String billCycle, int dataType, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3);
	
	List<String> getLevel4Values(String billCycle, int dataType, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3, String [] val3, int key4);
	
	List<String> getLevel5Values(String billCycle, int dataType, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3, String [] val3, int key4, String [] val4, int key5);
}

