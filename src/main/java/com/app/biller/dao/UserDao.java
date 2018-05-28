package com.app.biller.dao;


import java.util.List;


import com.app.biller.domain.User;

public interface UserDao {

	String getPassword(String userid);

	int setPassword(String userid, String password);

	User createUserProfile(String userid);
	
	List<User> getDelegateUserList(String userid);
	
	int setDelegateUser(String delegateBy, String delegateTo);
	
	int unsetDelegateUser(String delegateBy);
	
	List<User> getDelegateBy(String userID);
	
	String getEmailID(String userID);
	
	String getPmoEmailID();
	
	int getUserCountByRole(int roleID);
	
	List<String> getEmailListByRole(int roleID);
	
}
