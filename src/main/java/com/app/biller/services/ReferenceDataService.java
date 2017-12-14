package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.domain.Month;
import com.app.biller.domain.Tower;
import com.app.biller.domain.User;

public interface ReferenceDataService {
	
	List<String> getEmployeeList(String billCycle, int dataType);	
	
	List<String> getWRList(String billCycle, int dataType);

	List<Tower> getTowerList();
	
	List<String> getWeekendList(String billCycle, int dataType);
	
	ArrayList<Month> getMonth();
	
	List<String> getYear();
	
	List<String> getTableHeader(int dataType);
	
	String getActiveBillCycle();
	
	List<User> getDelegateUserList(String userID);
	
	String getMonthForBillCycle(String billCycle);
}
