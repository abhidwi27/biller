package com.app.biller.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Value("${SELECT_USER_PROFILE}")
	private String selectUserProfile;

	@Value("${SELECT_USER_PASSWORD}")
	private String selectUserPassword;

	@Value("${UPDATE_USER_PASSWORD}")
	private String updateUserPassword;

	@Value("${GET_DELEGATE_USER_LIST}")
	private String getDelegateUserList;

	@Value("${SET_DELEGATE_USER}")
	private String setDelegateUser;

	@Value("${UNSET_DELEGATE_USER}")
	private String unsetDelegateUser;

	@Value("${GET_DELEGATE_BY_USER_LIST}")
	private String getDelegateByUserList;

	@Value("${GET_EMAIL_ID}")
	private String getEmailID;

	@Value("${GET_PMO_EMAIL_ID}")
	private String getPmoEmailID;

	@Value("${GET_USER_COUNT_BY_ROLE}")
	private String getUserCountByRole;
	
	@Value("${GET_EMAIL_ID__LIST_BY_ROLE}")
	private String getEmailIdListByRole;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public String getPassword(String userid) {
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		List<String> strLst = jdbcTemplate.query(selectUserPassword, new Object[] {userid }, rowMap);
		if (strLst.isEmpty()) {
			return "";
		} else if (strLst.size() == 1) { // list contains exactly 1 element
			return strLst.get(0);
		} else { // list contains more than 1 elements
			return "";
		}
	}

	@Override
	public int setPassword(String userid, String password) {
		int result = jdbcTemplate.update(updateUserPassword, new Object[] {password, userid});
		return result;
	}

	@Override
	public User createUserProfile(String userid) {
		logger.info("Creating user profile for " + userid);
		User user = jdbcTemplate.queryForObject(selectUserProfile, new Object[] {userid}, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rownumber) throws SQLException {
				User usr = new User();
				usr.setUserID(rs.getString("user_id"));
				usr.setName(rs.getString("Name"));
				usr.setRoleID(rs.getInt("role_id"));
				usr.setRoleDesc(rs.getString("role_short_desc"));
				return usr;
			}
		});
		logger.info("User profile successfully created for  " + userid);
		return user;
	}

	@Override
	public List<User> getDelegateUserList(String userid) {
		logger.info("Getting list of users whom " + userid + " can delegate to");
		RowMapper<User> delegateUserList = new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rownumber) throws SQLException {
				User user = new User();
				user.setUserID(rs.getString("userID"));
				user.setName(rs.getString("Name"));
				user.setRoleID(rs.getInt("roleID"));
				user.setRoleDesc("roleDesc");
				return user;
			}
		};
		List<User> strDelegateList = jdbcTemplate.query(getDelegateUserList, new Object[] {userid, userid},
				delegateUserList);
		if (strDelegateList.isEmpty()) {
			logger.info("There is no user for " + userid + " to delegate to");
			return null;
		} else { // list contains more than 1 elements
			logger.info("Delegate list prepared for " + userid);
			return strDelegateList;
		}
	}

	@Override
	public int setDelegateUser(String delegatedBy, String delegatedTo) {
		logger.info("Setting delegation to " + delegatedBy + " for " + delegatedBy);
		int result = jdbcTemplate.update(setDelegateUser, new Object[]{delegatedTo, delegatedBy});
		if(result != 0) {
			logger.info(delegatedBy + " successfully delegated to " + delegatedTo);
		}else {
			logger.warn("Coundn't delegated to " + delegatedTo + " by " + delegatedBy);
		}
		return result;
	}

	@Override
	public int unsetDelegateUser(String delegateBy) {
		logger.info("Un-setting delegation to " + delegateBy);
		int result = jdbcTemplate.update(unsetDelegateUser, new Object[]{delegateBy});
		if(result != 0) {
			logger.info("un-set delegation complete for " + delegateBy);
		}else {
			logger.info("Couldn't un-set delegate for " + delegateBy);
		}
		return result;
	}

	@Override
	public List<User> getDelegateBy(String userid) {
		logger.info("Getting list of users who have delegated to " + userid);
		RowMapper<User> delegateUserList = new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rownumber) throws SQLException {
				User user = new User();
				user.setUserID(rs.getString("userID"));
				user.setName(rs.getString("Name"));
				user.setRoleID(rs.getInt("roleID"));
				user.setRoleDesc("roleDesc");
				return user;
			}
		};
		List<User> strDelegatedBy = jdbcTemplate.query(getDelegateByUserList, new Object[] {userid},
				delegateUserList);
		if (strDelegatedBy.isEmpty()) {
			logger.warn("There is no user who has delegated to " + userid);
			return null;
		} else { // list contains more than 1 elements
			logger.info("List prepared of users who has delegated to " + userid);
			return strDelegatedBy;
		}
	}

	@Override
	public String getEmailID(String userID) {
		RowMapper<String> emailMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		List<String> emailID = jdbcTemplate.query(getEmailID, new Object[]{userID}, emailMap);
		if (emailID.isEmpty()) {
			logger.warn("No email found for " + userID);
			return null;
		} else { // list contains more than 1 elements
			return emailID.get(0);
		}
	}

	public String getPmoEmailID() {
		RowMapper<String> emailMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		List<String> emailID = jdbcTemplate.query(getPmoEmailID, new Object[]{}, emailMap);
		if (emailID.isEmpty()) {
			logger.warn("No Email ID found for PMO");
			return null;
		} else { // list contains more than 1 elements
			return emailID.get(0);
		}

	}

	public int getUserCountByRole(int roleID) {
		int result = jdbcTemplate.queryForObject(getUserCountByRole, new Object[] {roleID}, Integer.class);
		if(result == 0) {
			logger.warn("No user found in the system for role ID " + roleID);
		}
		return result;
	}
	
	public List<String> getEmailListByRole(int roleID){
		
		List<String> emailIDList = null;
		RowMapper<String> emailMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		emailIDList = jdbcTemplate.query(getEmailIdListByRole, new Object[]{roleID}, emailMap);
		if (emailIDList.isEmpty()) {
			logger.warn("No Email ID found for selected role " + roleID );
			return null;
		} else { // list contains more than 1 elements
			return emailIDList;
		}
	}
}
