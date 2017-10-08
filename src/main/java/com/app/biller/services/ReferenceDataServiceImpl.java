package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.biller.dao.ILCDataDao;
import com.app.biller.dao.ReferenceDataDao;
import com.app.biller.dao.SLADataDao;
import com.app.biller.domain.Month;
import com.app.biller.domain.Tower;

@Service
public class ReferenceDataServiceImpl implements ReferenceDataService {

	@Autowired
	ILCDataDao ilcDataDao;

	@Autowired
	SLADataDao slaDataDao;

	@Autowired
	ReferenceDataDao referenceDataDao;

	public List<String> getEmployeeList(String billCycle, int dataType) {
		if (dataType == 0) {
			return ilcDataDao.getEmployeeList(billCycle);
		}
		return slaDataDao.getEmployeeList(billCycle);
	}

	public List<String> getWRList(String billCycle, int dataType) {
		if (dataType == 0) {
			return ilcDataDao.getWRList(billCycle);
		}
		return slaDataDao.getWRList(billCycle);
	}

	public List<Tower> getTowerList() {
		return referenceDataDao.getTowerList();
	}

	public List<String> getWeekendList(String billCycle, int dataType) {
		if (dataType == 0) {
			return ilcDataDao.getWeekendList(billCycle);
		}
		return slaDataDao.getWeekendList(billCycle);
	}

	public ArrayList<Month> getMonth() {
		return referenceDataDao.getMonthList();

	}

	public List<String> getYear() {
		return referenceDataDao.getYearList();
	}

	public List<String> getTableHeader(int dataType) {
		if (dataType == 0) {
			return referenceDataDao.getILCTableHeader();
		}
		return referenceDataDao.getSLATableHeader();
	}
	
	public String getActiveBillCycle() {
		return slaDataDao.getActiveBillCycle();
	}
}
