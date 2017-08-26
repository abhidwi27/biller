package com.app.biller.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.biller.dao.UserDao;
import com.app.biller.model.User;

@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	UserDao userDao;

	@Override
	public User validateCredentials(String userid, String userPassword) {
		User user = null;
		if (userPassword.equals(userDao.getPassword(userid))) {
			user = userDao.createUserProfile(userid);
		}
		return user;
	}

	//TODO: Use this method to get User Home based on Role
	@Override
	public String getUserHome(int roleID) {
		logger.info("Role is " + roleID);
		String viewName = "Data";
		switch (roleID) {		 
		   case	1:
		   case 2:
		   case 3:
		   case 4:
		   case 5:
		   case 6:
			   viewName = "Data";
				break;
		   case 7:
		   case 8:
			   viewName = "Home";
				break;			   
		 }   		
		return viewName;
	}
}
