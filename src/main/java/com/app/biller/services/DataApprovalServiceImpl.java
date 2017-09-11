package com.app.biller.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.biller.dao.GroupApprovalDao;
import com.app.biller.dao.UserApprovalDao;
import com.app.biller.dao.UserDao;
import com.app.biller.domain.GroupApproval;
import com.app.biller.domain.User;
import com.app.biller.domain.UserApproval;

@Service
public class DataApprovalServiceImpl implements DataApprovalService {

	@Autowired
	UserApprovalDao userApprovalDao;

	@Autowired
	GroupApprovalDao groupApprovalDao;

	@Autowired
	UserDao userDao;

	public List<UserApproval> getUserApprovalByRole(String billCycle, int roleID) {
		return userApprovalDao.getUserApprovalByRole(billCycle, roleID);
	}

	public boolean setUserApproval(String billCycle, String userID, int roleID, String roleDesc) {

		int approvalStatus;
		int isAlreadyApproved = userApprovalDao.checkPriorApproval(billCycle, userID);
		int isRejected = userApprovalDao.checkRejection(billCycle, userID);

		if (isAlreadyApproved == 1) {
			return false;
		} else if (isAlreadyApproved == 0 && isRejected == 0) {
			userApprovalDao.setUserApproval(billCycle, userID);
		} else if (isAlreadyApproved == 0 && isRejected == 1) {
			userApprovalDao.updateUserApproval(billCycle, userID);
		}

		int pendingApprovalCount = userApprovalDao.getPendingApprovalsByRole(billCycle, roleID);

		if (pendingApprovalCount == 0) {
			approvalStatus = 2;
		} else {
			approvalStatus = 1;
		}
		groupApprovalDao.updateGroupApproval(billCycle, roleDesc, approvalStatus);

		GroupApproval group = groupApprovalDao.getGroupApprovals(billCycle);

		if (group.getDmApprvoal() == 2 && group.getBamApproval() == 2 && group.getSrBamApproval() == 2
				&& group.getPmoApproval() == 2) {
			groupApprovalDao.setBillCycleStatus(billCycle, 1);
		}

		return true;

	}

	public void rejectUserApproval(String billCycle, String rejectedBy, String rejectedFor) {

		userApprovalDao.rejectUserApproval(billCycle, rejectedBy, rejectedFor);

		int approvalStatus;
		User rejectedUser = userDao.createUserProfile(rejectedFor);
		int pendingApprovalcount = userApprovalDao.getPendingApprovalsByRole(billCycle, rejectedUser.getRoleID());

		if (pendingApprovalcount == 0) {
			approvalStatus = 2;
		} else {
			approvalStatus = 1;
		}
		groupApprovalDao.updateGroupApproval(billCycle, rejectedUser.getRoleDesc(), approvalStatus);
	}

	public GroupApproval getGroupApprovals(String billCycle) {
		return groupApprovalDao.getGroupApprovals(billCycle);
	}

	@Override
	public int getPendingApprvoalByRole(String billCycle, int roleID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean setUserApproval(String billCycle, String userID, int roleID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateGroupApproval(String billCycle, String columnName, int status) {
		// TODO Auto-generated method stub
		
	}

}
