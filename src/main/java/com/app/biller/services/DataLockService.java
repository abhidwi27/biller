package com.app.biller.services;

import java.util.Map;

import com.app.biller.domain.Tower;
import com.app.biller.domain.User;

public interface DataLockService {

	User checkLockForTower(String billCycle, int towerID);

	void setLock(String billCycle, String userID, int towerID);

	void unSetLock(String userID, String billCycle, int towerID);
	
	Tower checkLockByUser( String userID, String billCycle);
}
