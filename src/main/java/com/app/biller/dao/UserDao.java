package com.app.biller.dao;

import com.app.biller.domain.User;

public interface UserDao {

	String getPassword(String userid);

	int setPassword(String userid, String password);

	User createUserProfile(String userid);
}
