package com.app.biller.services;

import java.util.List;

import com.app.biller.domain.GroupApproval;
import com.app.biller.domain.UserApproval;
import com.app.biller.ui.ApprovalStatus;

public interface DataApprovalService {

	List<UserApproval> getUserApprovalByRole(String billCycle, int roleID);

	boolean setUserApproval(String billCycle, String userID, int roleID, String roleDesc);

	void rejectUserApproval(String billCycle, String rejectedBy, String rejectedFor);

	GroupApproval getGroupApprovals(String billCycle);
	
	ApprovalStatus getApprovalStatus(String billCycle);
}
