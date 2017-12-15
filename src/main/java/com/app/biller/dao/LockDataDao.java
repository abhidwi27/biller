package com.app.biller.dao;

import java.util.Map;

import com.app.biller.domain.User;

public interface LockDataDao {
	
	User checkLockForTower(String billCycle, int towerID);
	
	void setLock(String billCycle , String userID, int towerID);
	
	void unSetLock(String userID, String billCycle, int towerID);
	
	String checkLockByUser(String userID, String billCycle);
}
