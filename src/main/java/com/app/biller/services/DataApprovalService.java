package com.app.biller.services;

import java.util.List;

import com.app.biller.domain.GroupApproval;
import com.app.biller.domain.User;
import com.app.biller.domain.UserApproval;
import com.app.biller.ui.ApprovalStatus;

public interface DataApprovalService {

	List<UserApproval> getUserApprovalByRole(String billCycle, int roleID);

	boolean setUserApproval(String billCycle, String approveBy, String approveFor, int roleID, String roleDesc);

	void rejectUserApproval(String billCycle, String rejectedBy, String rejectedFor);

	GroupApproval getGroupApprovals(String billCycle);
	
	ApprovalStatus getApprovalStatus(String billCycle);
	
	int setDelegation(String delegatedBy, String delegatedTo);

	int unsetDelegation(String delegatedBy);
	
	List<User> getDelegateByUserList(String userID);
	
	List<User> getRejectForUserList(String billCycle);
	
	int checkPriorApproval(String billCycle, String userID);
	
	void createGroupApproval(String billCycle, String userID);
}
