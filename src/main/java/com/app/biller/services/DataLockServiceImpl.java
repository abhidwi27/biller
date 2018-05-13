package com.app.biller.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.biller.dao.LockDataDao;
import com.app.biller.domain.Tower;
import com.app.biller.domain.User;

@Service
public class DataLockServiceImpl implements DataLockService {
	
	private static final Logger logger = LoggerFactory.getLogger(DataLockServiceImpl.class);
	
	@Autowired
	LockDataDao lockDataDao;

	public User checkLockForTower(String billCycle, int towerID) {
		return lockDataDao.checkLockForTower(billCycle, towerID);
	}

	public void setLock(String billCycle, String userID, int towerID) {
		lockDataDao.setLock(billCycle, userID, towerID);
	}

	public void unSetLock(String userID, String billCycle, int towerID) {
		lockDataDao.unSetLock(userID, billCycle, towerID);
	}
	
	public Tower checkLockByUser(String userID, String billCycle) {
		return lockDataDao.checkLockByUser(userID, billCycle);
	}
}
