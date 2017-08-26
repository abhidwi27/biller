package com.app.biller.services;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserAdminServiceImpl implements UserAdminService {

	private static final Logger logger = LoggerFactory.getLogger(UserAdminServiceImpl.class);

	@Override
	public String createUser(HttpServletRequest request) {
		return null;
	}

	@Override
	public String updateUser() {
		return null;
	}

	@Override
	public String deactiveUser() {
		return null;
	}

	@Override
	public String resetUserPassword(String userid, String newpassword) {
		// TODO: Provide Reset Password Feature in Future
		return null;
	}	
}
