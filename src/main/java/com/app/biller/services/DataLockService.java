package com.app.biller.services;

import com.app.biller.domain.Tower;
import com.app.biller.domain.User;

public interface DataLockService {

	User checkLockForTower(String billCycle, int towerID, int accountId);

	void setLock(String billCycle, String userID, int towerID, int accountId);

	void unSetLock(String userID, String billCycle, int towerID, int accountId);
	
	Tower checkLockByUser( String userID, String billCycle, int accountId);
}
