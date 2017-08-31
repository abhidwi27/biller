package com.app.biller.services;

public interface DataLockService {

	String checkLock(String billCycle, int towerID);

	void setLock(String billCycle, String userID, int towerID);

	void unSetLock(String userID, String billCycle, int towerID);
}
