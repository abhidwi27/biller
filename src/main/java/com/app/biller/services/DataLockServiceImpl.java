package com.app.biller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.biller.dao.LockDataDaoImpl;

@Service
public class DataLockServiceImpl implements DataLockService {

	@Autowired
	LockDataDaoImpl lockDao;

	public String checkLock(String billCycle, int towerID) {
		return lockDao.checkLock(billCycle, towerID);
	}

	public void setLock(String billCycle, String userID, int towerID) {
		lockDao.setLock(billCycle, userID, towerID);
	}

	public void unSetLock(String userID, String billCycle, int towerID) {
		lockDao.unSetLock(userID, billCycle, towerID);
	}
}
