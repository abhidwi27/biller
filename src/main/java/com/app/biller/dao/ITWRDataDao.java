package com.app.biller.dao;

import java.util.ArrayList;

import org.springframework.dao.DataAccessException;

import com.app.biller.domain.ITWRData;

public interface ITWRDataDao {

	void createITWRData(ArrayList<ITWRData> ilcModelList, String billCycle, String userId, String uploadDataType) throws DataAccessException;	

	
}