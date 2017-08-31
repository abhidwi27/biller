package com.app.biller.dao;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.model.ILCData;

public interface ILCDataDao {

	void createILCData(ArrayList<ILCData> ilcModelList, String billCycle, String userId);	
	
	List<ILCData> readILCData(String billCycle, String towerID);
	
	List<ILCData> readCustomILCData(String billCycle, String towerID, String weekEndDate[], String wrNo[], String empID[], String modifiedBy[]);
}
