package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.app.biller.dao.ILCDataDao;
import com.app.biller.model.ILCData;
import com.app.biller.model.SLAData;

@Component
public class DataValidationServiceImpl implements DataValidationService {

	private static final Logger logger = LoggerFactory.getLogger(DataValidationServiceImpl.class);

	@Autowired
	ILCDataDao ilcDataDao;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<ILCData> readILCData(String billCycle, String towerID) {
		// return ilcDataDao.readILCData();
		// List<ILCData> ilcData = new ArrayList<ILCData>();
		// ilcData = DisplayReportDAO.getReport();
		// ModelAndView mv = new ModelAndView("/tableData");
		// mv.addObject("ilcDataList", ilcData);
		// return ilcData;
		return null;
	}

	@Override
	public List<SLAData> readSLAData(String billCycle, String towerID) {
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
