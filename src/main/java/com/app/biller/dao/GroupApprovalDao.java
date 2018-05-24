package com.app.biller.dao;

import com.app.biller.domain.GroupApproval;

public interface GroupApprovalDao {

	void setBillCycleStatus(String billCycle, int billCycleStatus);

	void createGroupApproval(String billCycle, String userID);

	int checkGroupApprovalEntry(String billCycle);
	
	void updateGroupApproval(String billCycle, String groupName, int approvalStatus);
	
	void resetGroupApproval(String billCycle);

	GroupApproval getGroupApprovals(String billCycle);
}
