package com.app.biller.dao;

public interface LockDataDao {
	
	String checkLock(String billCycle, int towerID);
	
	void setLock(String billCycle , String userID, int towerID);
	
	void unSetLock(String userID, String billCycle, int towerID);
}
