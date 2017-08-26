package com.ibm.biller.services;

import com.ibm.biller.model.User;

public interface LoginService {
	
	User validateCredentials(String userid, String password);
	
	String getUserHome(int RoleID);

	
	
//	String resetUserPassword(String userid, String newpassword);
}
