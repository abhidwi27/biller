package com.app.biller.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.biller.dao.ILCDataDao;
import com.app.biller.model.ILCData;

@Component
public class DataValidationServiceImpl implements DataValidationService {

	private static final Logger logger = LoggerFactory.getLogger(DataValidationServiceImpl.class);

	@Autowired
	ILCDataDao ilcDataDao;

	@Override
	public List<ILCData> readILCData() {
		return ilcDataDao.readILCData();
		// List<ILCData> ilcData = new ArrayList<ILCData>();
		// ilcData = DisplayReportDAO.getReport();
		// ModelAndView mv = new ModelAndView("/tableData");
		// mv.addObject("ilcDataList", ilcData);
		// return ilcData;
	}

	@Override
	public String updateILCData(String userId) {
		// String lockedBy = EditTableDAO.checkTableLock();
		// if( lockedBy.equals("")){
		// EditTableDAO.lockTable(userID);
		// return "success";
		// }
		// else{
		// System.out.println("Table is locked by " + lockedBy);
		// return lockedBy;
		// }
		return null;
	}

	@Override
	public String signoffILCData(String userId) {
		return "success";
	}

}
