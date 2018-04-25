package com.app.biller.dao;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.domain.Account;
import com.app.biller.domain.Month;
import com.app.biller.domain.Tower;
import com.app.biller.ui.ItwrReference;

public interface ReferenceDataDao {

	List<String> getILCTableHeader();

	List<String> getSLATableHeader();

	List<Tower> getTowerList();

	ArrayList<Month> getMonthList();

	List<String> getYearList();
	
	String getMonthForBillCycle(String billCycle);
	
	List<Account> getAccountList();
	
	List<ItwrReference> getItwrReferenceData(String wrNo);
}
