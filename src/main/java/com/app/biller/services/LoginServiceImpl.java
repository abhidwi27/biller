package com.app.biller.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.biller.dao.UserDao;
import com.app.biller.domain.User;

@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	UserDao userDao;

	@Override
	public User validateCredentials(String userID, String userPassword) {
		User user = null;
		if (userPassword.equals(userDao.getPassword(userID))) {
			user = userDao.createUserProfile(userID);
		}
		return user;
	}

	@Override
	public String getUserHome(int roleID) {
		String viewName = "home";
		switch (roleID) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
			viewName = "home";
			break;
		}
		return viewName;
	}
}
