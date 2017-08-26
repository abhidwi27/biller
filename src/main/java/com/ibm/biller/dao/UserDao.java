package com.ibm.biller.dao;

import com.ibm.biller.model.User;

public interface UserDao {

	String getPassword(String userid);

	int setPassword(String userid, String password);

	User createUserProfile(String userid);
}
