package com.app.biller.services;

import javax.servlet.http.HttpServletRequest;

public interface UserAdminService {
	
	/*String createUser(HttpServletRequest request);
	
	String updateUser();
	
	String deActivateUser();*/
	
	String resetPassword(String userid, String newpassword);
}
