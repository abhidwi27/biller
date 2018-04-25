package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.domain.Account;
import com.app.biller.domain.Month;
import com.app.biller.domain.Tower;
import com.app.biller.domain.User;
import com.app.biller.ui.ItwrReference;

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
}

