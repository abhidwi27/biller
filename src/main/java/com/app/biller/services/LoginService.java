package com.app.biller.services;

import com.app.biller.model.User;

public interface LoginService {
	
	User validateCredentials(String userid, String password);
	
	String getUserHome(int RoleID);

	
	
//	String resetUserPassword(String userid, String newpassword);
}
