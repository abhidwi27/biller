package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.domain.Month;
import com.app.biller.domain.Tower;

public interface ReferenceDataService {
	
	List<String> getEmployeeList(String billCycle, int dataType);	
	
	List<String> getWRList(String billCycle, int dataType);

	List<Tower> getTowerList();
	
	List<String> getWeekendList(String billCycle, int dataType);
	
	ArrayList<Month> getMonth();
	
	List<String> getYear();
	
	List<String> getTableHeader(int dataType);
	
	String getActiveBillCycle();
}
