package com.app.biller.dao;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.domain.WIASMData;

public interface WIASMDataDao {

	void uploadWIASMData(ArrayList<WIASMData> wiASMModelList, String billCycle, String userId, String uploadDataType) throws Exception;	

}
