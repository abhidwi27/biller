package com.app.biller.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.biller.dao.GroupApprovalDao;
import com.app.biller.dao.UserApprovalDao;
import com.app.biller.dao.UserDao;
import com.app.biller.domain.GroupApproval;
import com.app.biller.domain.User;
import com.app.biller.domain.UserApproval;
import com.app.biller.ui.ApprovalStatus;

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
	
	public ApprovalStatus getApprovalStatus(String billCycle) {
		ApprovalStatus approvalStatus = new ApprovalStatus();
		Map<String,List<UserApproval>> userApprovalList = new HashMap<String,List<UserApproval>>();
					
		userApprovalList.put("dmApprovalList",getUserApprovalByRole(billCycle, 2));
		userApprovalList.put("bamApprovalList",getUserApprovalByRole(billCycle, 3));
		userApprovalList.put("srBamApprovalList",getUserApprovalByRole(billCycle, 4));
		userApprovalList.put("pmoApprovalList",getUserApprovalByRole(billCycle, 8));
		
		approvalStatus.setGroupApproval(getGroupApprovals(billCycle));
		approvalStatus.setUserApprovalList(userApprovalList);	
			
		return approvalStatus;
	}
}
