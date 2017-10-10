package com.app.biller.ui;


import java.util.List;
import java.util.Map;

import com.app.biller.domain.GroupApproval;
import com.app.biller.domain.UserApproval;

public class ApprovalStatus {
	
	private GroupApproval groupApproval;
	private Map<String,List<UserApproval>> userApprovalList;
	
	public GroupApproval getGroupApproval() {
		return groupApproval;
	}
	public void setGroupApproval(GroupApproval groupApproval) {
		this.groupApproval = groupApproval;
	}
	public Map<String, List<UserApproval>> getUserApprovalList() {
		return userApprovalList;
	}
	public void setUserApprovalList(Map<String, List<UserApproval>> userApprovalList) {
		this.userApprovalList = userApprovalList;
	}
	

}
