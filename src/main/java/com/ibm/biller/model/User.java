package com.ibm.biller.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String userID;
	private String name;
	private int roleID;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
}
