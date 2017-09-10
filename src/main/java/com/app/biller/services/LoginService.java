package com.app.biller.services;

import com.app.biller.domain.User;

public interface LoginService {

	User validateCredentials(String userID, String password);

	String getUserHome(int roleID);

	// String resetUserPassword(String userid, String newpassword);
}
