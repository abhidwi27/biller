package com.app.biller.dao;

import com.app.biller.model.GroupApproval;

public interface GroupApprovalDao {

	void setBillCycleStatus(String billCycle, int billCycleStatus);

	void createGroupApproval(String billCycle, String userID);

	void updateGroupApproval(String billCycle, String groupName, int approvalStatus);

	GroupApproval getGroupApprovals(String billCycle);
}
