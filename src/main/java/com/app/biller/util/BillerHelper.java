package com.app.biller.util;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.biller.domain.User;

public final class BillerHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(BillerHelper.class);
	
	private BillerHelper() {		
	}
	
	public static User getUserProfile(HttpSession userSession) {
		User loggedInUser = (User) userSession.getAttribute("userProfile");
		return loggedInUser;
	}
}
