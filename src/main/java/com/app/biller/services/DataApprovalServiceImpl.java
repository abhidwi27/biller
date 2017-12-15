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

	public boolean setUserApproval(String billCycle, String approveBy, String approveFor, int roleID, String roleDesc) {

		int approvalStatus;
		int isAlreadyApproved = userApprovalDao.checkPriorApproval(billCycle, approveFor);
		int isRejected = userApprovalDao.checkRejection(billCycle, approveFor);

		if (isAlreadyApproved == 1) {
			return false;
		} else if (isAlreadyApproved == 0 && isRejected == 0) {
			userApprovalDao.setUserApproval(billCycle, approveBy, approveFor);
		} else if (isAlreadyApproved == 0 && isRejected == 1) {
			userApprovalDao.updateUserApproval(billCycle, approveFor);
		}

		int pendingApprovalCount = userApprovalDao.getPendingApprovalsByRole(billCycle, roleID);
		int userCountForRole = userDao.getUserCountByRole(roleID);
		if (pendingApprovalCount == 0) {
			approvalStatus = 2;
		} else if (pendingApprovalCount == userCountForRole ){
			approvalStatus = 0;
		} else {
			approvalStatus = 1;
		}
		groupApprovalDao.updateGroupApproval(billCycle, roleDesc, approvalStatus);
		GroupApproval group = groupApprovalDao.getGroupApprovals(billCycle);
		if (group.getDmApproval() == 2 && group.getBamApproval() == 2 && group.getSrBamApproval() == 2
				&& group.getPmoApproval() == 2) {
			groupApprovalDao.setBillCycleStatus(billCycle, 1);
		}
		return true;
	}

	public void rejectUserApproval(String billCycle, String rejectedBy, String rejectedFor) {

		userApprovalDao.rejectUserApproval(billCycle, rejectedBy, rejectedFor);

		int approvalStatus;
		User rejectedUser = userDao.createUserProfile(rejectedFor);
		int pendingApprovalCount = userApprovalDao.getPendingApprovalsByRole(billCycle, rejectedUser.getRoleID());
		int userCountForRole = userDao.getUserCountByRole(rejectedUser.getRoleID());
		if (pendingApprovalCount == 0) {
			approvalStatus = 2;
		} else if (pendingApprovalCount == userCountForRole ){
			approvalStatus = 0;
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
	
	public int updateDelegateUser(String delegateBy, String delegateTo, int delegateStatus) {		
		int result;		
		if (delegateStatus == 0) {
			result = userDao.unsetDelegateUser(delegateBy );
		}else {
			result = userDao.setDelegateUser( delegateBy, delegateTo);
		}
		return result;
	}
	
	
	public List<User> getDelegateByUserList(String userID) {
		return userDao.getDelegateBy(userID);
	}
	
	public List<User> getRejectForUserList(String billCycle){
		return userApprovalDao.getRejectForUserList(billCycle);
	}
}
