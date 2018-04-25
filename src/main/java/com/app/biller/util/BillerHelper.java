package com.app.biller.util;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.biller.dao.UserDao;
import com.app.biller.domain.User;

public final class BillerHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(BillerHelper.class);
	
	private BillerHelper() {		
	}
	
	@Autowired
	private static UserDao userDao;

	public static User getUserProfile(HttpSession userSession) {
		User loggedInUser = (User) userSession.getAttribute("userProfile");
		return loggedInUser;
	}

	public static String getUserEmailId(String userId) {
		return userDao.getEmailID(userId);
	}
}
