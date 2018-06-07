package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.app.biller.ui.WIASMReference;

@Service
public class ReferenceDataServiceImpl implements ReferenceDataService {
	
	private static final Logger logger = LoggerFactory.getLogger(ReferenceDataServiceImpl.class);
	
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
	
	public List<WIASMReference> getwiasmReferenceData(String wrkItem){
		return referenceDataDao.getwiasmReferenceData(wrkItem);
	}
	
	public List<Header> getHeaderForBulkUpdate(){
		return referenceDataDao.getHeaderForBulkUpdate();
	}
	
	public List<String> getBulkUpdateData(String billCycle, int headerId){
		String column = referenceDataDao.getColumnNameById(headerId);
		return referenceDataDao.getBulkUpdateData(billCycle, column);
	}
	
	public List<Header> getHeaderForCustomise(){
		
		return referenceDataDao.getHeaderForCustomise();
	}
	
	public List<String> getLevel1Values(String billCycle, int dataType, int towerID, int accountId, int key1){
		if(dataType == 1) {
			return slaDataDao.getLevel1Values(billCycle, towerID, accountId, key1);
		}else {
			return ilcDataDao.getLevel1Values(billCycle, towerID, accountId, key1);
		}
	}
	
	public List<String> getLevel2Values(String billCycle, int dataType, int towerID, int accountId, int key1, String [] val1, int key2){
		if(dataType == 1) {
			return slaDataDao.getLevel2Values(billCycle, towerID, accountId, key1, val1,  key2);
		}else {
			return ilcDataDao.getLevel2Values(billCycle, towerID, accountId, key1, val1,  key2);
		}
	}
	
	public List<String> getLevel3Values(String billCycle, int dataType, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3){
		if(dataType == 1) {
			return slaDataDao.getLevel3Values(billCycle, towerID, accountId, key1, val1,  key2, val2, key3);
		}else {
			return ilcDataDao.getLevel3Values(billCycle, towerID, accountId, key1, val1,  key2, val2, key3);
		}
	}
	
	public List<String> getLevel4Values(String billCycle, int dataType, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3, String [] val3, int key4){
		if(dataType == 1) {
			return slaDataDao.getLevel4Values(billCycle, towerID, accountId, key1, val1,  key2, val2, key3, val3, key4);
		}else {
			return ilcDataDao.getLevel4Values(billCycle, towerID, accountId, key1, val1,  key2, val2, key3, val3, key4);
		}
	}
	
	public List<String> getLevel5Values(String billCycle, int dataType, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3, String [] val3, int key4, String [] val4, int key5){
		if(dataType == 1) {
			return slaDataDao.getLevel5Values(billCycle, towerID, accountId, key1, val1,  key2, val2, key3, val3, key4, val4, key5);
		}else {
			return ilcDataDao.getLevel5Values(billCycle, towerID, accountId, key1, val1,  key2, val2, key3, val3, key4, val4, key5);
		}
	}
	
	public String getDelegateStatus(String userID) {
		
		return referenceDataDao.getDelegateStatus(userID);
		
	}
}
