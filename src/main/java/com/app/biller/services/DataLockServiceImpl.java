package com.app.biller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.biller.dao.LockDataDao;

@Service
public class DataLockServiceImpl implements DataLockService {

	@Autowired
	LockDataDao lockDataDao;

	public String checkLock(String billCycle, int towerID) {
		return lockDataDao.checkLock(billCycle, towerID);
	}

	public void setLock(String billCycle, String userID, int towerID) {
		lockDataDao.setLock(billCycle, userID, towerID);
	}

	public void unSetLock(String userID, String billCycle, int towerID) {
		lockDataDao.unSetLock(userID, billCycle, towerID);
	}
}
