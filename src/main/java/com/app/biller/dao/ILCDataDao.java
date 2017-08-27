package com.app.biller.dao;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.model.ILCData;

public interface ILCDataDao {

	void insertILCData(ArrayList<ILCData> ilcModelList, String billCycle, String billCycleType, String userId);
	
	List<ILCData> readILCData();
}
