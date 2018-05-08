package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.biller.dao.ILCDataDao;
import com.app.biller.dao.ReferenceDataDao;
import com.app.biller.dao.SLADataDao;
import com.app.biller.dao.UserDao;
import com.app.biller.domain.Account;
import com.app.biller.domain.Header;
import com.app.biller.domain.Month;
import com.app.biller.domain.Tower;
import com.app.biller.domain.User;
import com.app.biller.ui.ItwrReference;

@Service
public class ReferenceDataServiceImpl implements ReferenceDataService {

	@Autowired
	ILCDataDao ilcDataDao;

	@Autowired
	SLADataDao slaDataDao;

	@Autowired
	ReferenceDataDao referenceDataDao;
	
	@Autowired
	UserDao userDao;

	public List<String> getEmployeeList(String billCycle, int dataType, int towerID) {
		if (dataType == 0) {
			return ilcDataDao.getEmployeeList(billCycle, towerID);
		}
		return slaDataDao.getEmployeeList(billCycle, towerID);
	}

	public List<String> getWRList(String billCycle, int dataType, int towerID) {
		if (dataType == 0) {
			return ilcDataDao.getWRList(billCycle, towerID);
		}
		return slaDataDao.getWRList(billCycle, towerID);
	}

	public List<Tower> getTowerList() {
		return referenceDataDao.getTowerList();
	}

	public List<String> getWeekendList(String billCycle, int dataType, int towerID) {
		if (dataType == 0) {
			return ilcDataDao.getWeekendList(billCycle, towerID);
		}
		return slaDataDao.getWeekendList(billCycle, towerID);
	}
	
	public List<String> getRemarksList(String billCycle, int dataType, int towerID) {
		if (dataType == 0) {
			return ilcDataDao.getRemarksList(billCycle, towerID);
		}
		return slaDataDao.getRemarksList(billCycle, towerID);
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
	
	public List<User> getDelegateUserList(String userID) {
		return userDao.getDelegateUserList(userID);
	}
	
	public String getMonthForBillCycle(String billCycle) {
		return referenceDataDao.getMonthForBillCycle(billCycle);
	}
	
	public List<Account> getAccountList(){
		return referenceDataDao.getAccountList();
	}
	
	public List<ItwrReference> getItwrReferenceData(String wrNo){
		return referenceDataDao.getItwrReferenceData(wrNo);
	}
	
	public List<Header> getHeaderForBulkUpdate(){
		return referenceDataDao.getHeaderForBulkUpdate();
	}
	
	public List<String> getBulkUpdateData(String billCycle, int headerId){
		String column = referenceDataDao.getColumnNameById(headerId);
		return referenceDataDao.getBulkUpdateData(billCycle, column);
	}
}
