package com.app.biller.services;

import java.util.List;

import com.app.biller.model.GroupApproval;
import com.app.biller.model.UserApproval;

public interface DataApprovalService {

	List<UserApproval> getUserApprovalByRole(String billCycle, int roleID);

	int getPendingApprvoalByRole(String billCycle, int roleID);

	boolean setUserApproval(String billCycle, String userID, int roleID);

	void rejectUserApproval(String billCycle, String rejectedBy, String rejectedFor);

	GroupApproval getGroupApprovals(String billCycle);

	void updateGroupApproval(String billCycle, String columnName, int status);
}
