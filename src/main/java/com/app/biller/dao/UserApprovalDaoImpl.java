package com.app.biller.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.app.biller.domain.User;
import com.app.biller.domain.UserApproval;

@Repository("userApprovalDao")
public class UserApprovalDaoImpl implements UserApprovalDao {
	
	private static final Logger logger = LoggerFactory.getLogger(UserApprovalDaoImpl.class);
	
	@Value("${COUNT_USER_APPROVALS}")
	String countUserApprovals;

	@Value("${COUNT_USER_REJECTIONS}")
	String countUserRejections;

	@Value("${INSERT_USER_APPROVAL}")
	String insertUserApproval;

	@Value("${UPDATE_USER_APPROVAL}")
	String updateUserApproval;

	@Value("${UPDATE_USER_REJECTION}")
	String updateUserRejection;

	@Value("${SELECT_USER_APPROVAL_BY_ROLE}")
	String selectUserApprovalByRole;

	@Value("${SELECT_PENDING_USER_APPROVAL_BY_ROLE}")
	String selectPendingUserApprovalByRole;
	
	@Value("${GET_REJECT_FOR_LIST}")
	String getRejectForList;
	
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public int checkPriorApproval(String billCycle, String userID) {
		logger.info("Checking prior approval by userID " + userID + " for billCyle " + billCycle);
		int isAlreadyApproved = jdbcTemplate.queryForObject(countUserApprovals, new Object[] { billCycle, userID },
				Integer.class);
		return isAlreadyApproved;
	}

	public int checkRejection(String billCycle, String userID) {
		logger.info("Checking rejection by userID " + userID + " for billCyle " + billCycle);
		int isRejected = jdbcTemplate.queryForObject(countUserRejections, new Object[] { billCycle, userID }, Integer.class);
		return isRejected;
	}

	public ArrayList<UserApproval> getUserApprovalByRole(String billCycle, int roleID) {

		ArrayList<UserApproval> userApprovalDetail = (ArrayList<UserApproval>) jdbcTemplate.query(selectUserApprovalByRole,
				new Object[] { billCycle, roleID}, new RowMapper<UserApproval>() {

					@Override
					public UserApproval mapRow(ResultSet rs, int rowNum) throws SQLException {
						UserApproval sign = new UserApproval();
						sign.setUserID(rs.getString("user_id"));
						sign.setUserName(rs.getString("name"));
						sign.setRoleID(Integer.parseInt(rs.getString("role_id")));
						sign.setApprovalStatus(Integer.parseInt(rs.getString("approval_status")));
						return sign;
					}
				});
		return userApprovalDetail;
	}

	public int getPendingApprovalsByRole(String billCycle, int roleID) {
		Integer count = (Integer) jdbcTemplate.queryForObject(selectPendingUserApprovalByRole, new Object[] { billCycle, roleID },
				Integer.class);
		return count;
	}

	public void setUserApproval(String billCycle, String approveBy, String approveFor) {
		logger.info("Setting user approval by userID " + approveBy + " for billCyle " + billCycle + " for user " + approveFor);
		int result = jdbcTemplate.update(insertUserApproval, new Object[] { billCycle, approveFor, approveBy });
		if(result != 0) {
			logger.info("Approval set by userID " + approveBy + " for billCyle " + billCycle + " for user " + approveFor);
		}else {
			logger.info("Approval couldn't be set by userID " + approveBy + " for billCyle " + billCycle + " for user " + approveFor);
		}
	}

	public void updateUserApproval(String billCycle, String userID) {
		logger.info("Updating user approval by userID " + userID + " for billCyle " + billCycle);
		int result = jdbcTemplate.update(updateUserApproval, new Object[] { userID, billCycle, userID });
		if(result != 0) {
			logger.info("Updated user approval by userID " + userID + " for billCyle " + billCycle);
		}else {
			logger.info("Couldn't update user approval by userID " + userID + " for billCyle " + billCycle);
		}
	}

	public void rejectUserApproval(String billCycle, String rejectedBy, String rejectedFor) {
		logger.info("Rejecting user approval by userID " + rejectedBy + " for billCyle " + billCycle + " rejectedFor " + rejectedFor);
		int result = jdbcTemplate.update(updateUserRejection, new Object[] { rejectedBy, rejectedBy, billCycle, rejectedFor });
		if(result != 0) {
			logger.info("Rejection successful by userID " + rejectedBy + " for billCyle " + billCycle + " rejectedFor " + rejectedFor);
		}else {
			logger.info("Rejection unsuceessful by userID " + rejectedBy + " for billCyle " + billCycle + " rejectedFor " + rejectedFor);
		}
	}
	
	public List<User> getRejectForUserList(String billCycle){
		
		List<User> rejectForUserList = (List<User>) jdbcTemplate.query(getRejectForList,
				new Object[] { billCycle}, new RowMapper<User>() {

					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setUserID(rs.getString("userID"));
						user.setName(rs.getString("Name"));
						user.setRoleID(Integer.parseInt(rs.getString("roleID")));
						user.setRoleDesc(rs.getString("roleDesc"));
						return user;
					}
				});
		return rejectForUserList;
		
	}
}
