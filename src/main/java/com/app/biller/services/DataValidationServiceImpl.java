package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.app.biller.dao.ILCDataDao;
import com.app.biller.dao.SLADataDao;
import com.app.biller.model.ILCData;
import com.app.biller.model.SLAData;

@Service
public class DataValidationServiceImpl implements DataValidationService {

	private static final Logger logger = LoggerFactory.getLogger(DataValidationServiceImpl.class);

	@Autowired
	ILCDataDao ilcDataDao;
	
	@Autowired
	SLADataDao slaDataDao;

	

	

	@Override
	public ArrayList<ILCData> readILCData(String billCycle, String towerID) {
		 return ilcDataDao.readILCData(billCycle, towerID);
		
	}

	@Override
	public ArrayList<SLAData> readSLAData(String billCycle, String towerID) {
		return slaDataDao.readSLAData(billCycle, towerID);
	}

	@Override
	public String deleteSLAData(String billCycle, int seqID[]) {
		return "success";
	}

	@Override
	public String updateSLAData(String billCycle, String towerID, String userID, ArrayList<?> records) {
		return null;
	}

	@Override
	public String createNewSLARecord(String billCycle, String userID, ArrayList<?> records) {
		return null;

	}

	@Override
	public List<ILCData> readCustomILCData(String billCycle, String towerID, String[] weekEndDate, String[] wrNo,
			String[] empID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SLAData> readCustomSLAData(String billCycle, String towerID, String[] weekEndDate, String[] wrNo,
			String[] empID) {
		// TODO Auto-generated method stub
		return null;
	}
}
