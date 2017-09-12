package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.biller.dao.ILCDataDao;
import com.app.biller.dao.SLADataDao;
import com.app.biller.domain.ILCData;
import com.app.biller.domain.SLAData;

@Service
public class DataValidationServiceImpl implements DataValidationService {

	private static final Logger logger = LoggerFactory.getLogger(DataValidationServiceImpl.class);

	@Autowired
	ILCDataDao ilcDataDao;

	@Autowired
	SLADataDao slaDataDao;

	@Override
	public ArrayList<ILCData> readILCData(String billCycle, int towerID) {
		return ilcDataDao.readILCData(billCycle, towerID);

	}

	@Override
	public ArrayList<SLAData> readSLAData(String billCycle, int towerID) {
		return slaDataDao.readSLAData(billCycle, towerID);
	}

	@Override
	public String deleteSLAData(String billCycle, int seqID[]) {
		return "success";
	}

	@Override
	public String updateSLAData(String billCycle, int towerID, String userID, ArrayList<?> records) {
		return null;
	}

	@Override
	public String createNewSLARecord(String billCycle, String userID, ArrayList<?> records) {
		return null;

	}

	@Override
	public List<ILCData> readCustomILCData(String billCycle, int towerID, String weekEndDate, String wrNo,
			String empName) {

		return ilcDataDao.readCustomILCData(billCycle, towerID, weekEndDate, wrNo, empName);
	}

	@Override
	public List<SLAData> readCustomSLAData(String billCycle, int towerID, String weekEndDate, String wrNo,
			String empName) {
		return slaDataDao.readCustomSLAData(billCycle, towerID, weekEndDate, wrNo, empName);
	}
}
