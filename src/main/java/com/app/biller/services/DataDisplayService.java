package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.model.Month;
import com.app.biller.model.Tower;

public interface DataDisplayService {
	
	public List<String> getEmployeeList(String billCycle, int dataType);	
	
	public List<String> getWRList(String billCycle, int dataType);

	public ArrayList<Tower> getTowerList(String billCycle);
	
	public List<String> getWeekendList(String billCycle, int dataType);
	
	public ArrayList<Month> getMonth();
	
	public List<String> getYear();
}
