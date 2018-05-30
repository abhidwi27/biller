package com.app.biller.util;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.app.biller.dao.UserDao;
import com.app.biller.domain.User;

@Service("billerHelper")
public class BillerHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(BillerHelper.class);
	
	private BillerHelper() {		
	}
	
	@Autowired
	UserDao userDao;

	public User getUserProfile(HttpSession userSession) {
		User loggedInUser = (User) userSession.getAttribute("userProfile");
		return loggedInUser;
	}

	public String getUserEmailId(String userId) {
		return userDao.getEmailID(userId);
	}
}
