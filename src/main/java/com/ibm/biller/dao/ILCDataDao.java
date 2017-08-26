package com.ibm.biller.dao;

import java.util.ArrayList;
import java.util.List;

import com.ibm.biller.model.ILCData;

public interface ILCDataDao {

	void insertILCData(ArrayList<ILCData> ilcModelList, String billCycle, String billCycleType, String userId);
	
	ArrayList<ILCData> readILCData();
}
