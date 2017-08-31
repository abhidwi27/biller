package com.app.biller.dao;

import java.util.List;

import com.app.biller.model.UserApproval;

public interface UserApprovalDao {

	List<UserApproval> getUserApprovalByRole(String billCycle, int roleID);

	int getPendingApprovalsByRole(String billCycle, int roleID);

	void setUserApproval(String billCycle, String userID);
	
	void updateUserApproval(String billCycle, String userID);

	void rejectUserApproval(String billCycle, String rejectedBy, String rejectedFor);
	
	int checkPriorApproval(String billCycle, String userID);
	
	int checkRejection(String billCycle, String userID);
}
