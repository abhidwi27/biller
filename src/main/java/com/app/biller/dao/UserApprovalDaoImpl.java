package com.app.biller.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.app.biller.model.UserApproval;

@Repository("userApprovalDao")
public class UserApprovalDaoImpl implements UserApprovalDao {

	@Value("${CHECK_PRIOR_APPROVAL}")
	String checkPriorApproval;

	@Value("${CHECK_REJECTED}")
	String checkRejected;

	@Value("${INSERT_USER_APPROVAL}")
	String insertUserApproval;

	@Value("${UPDATE_USER_APPROVAL}")
	String updateUserApproval;

	@Value("${REJECT_USER_APPROVAL}")
	String rejectApproval;

	@Value("${USER_APPROVAL_BY_ROLE}")
	String userApproval;

	@Value("${PENDING_APPROVAL_BY_ROLE}")
	String pendingApproval;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public int checkPriorApproval(String billCycle, String userID) {
		int isAlreadyApproved = jdbcTemplate.queryForObject(checkPriorApproval, new Object[] { billCycle, userID },
				Integer.class);
		return isAlreadyApproved;
	}

	public int checkRejection(String billCycle, String userID) {
		int isRejected = jdbcTemplate.queryForObject(checkRejected, new Object[] { billCycle, userID }, Integer.class);
		return isRejected;
	}

	public ArrayList<UserApproval> getUserApprovalByRole(String billCycle, int roleID) {

		ArrayList<UserApproval> userApprovalDetail = (ArrayList<UserApproval>) jdbcTemplate.query(userApproval,
				new Object[] { billCycle, roleID, roleID }, new RowMapper<UserApproval>() {

					@Override
					public UserApproval mapRow(ResultSet rs, int rowNum) throws SQLException {
						UserApproval sign = new UserApproval();
						sign.setUserID(rs.getString("user_id"));
						sign.setUserName(rs.getNString("Name"));
						sign.setRoleID(Integer.parseInt(rs.getString("role_id")));
						sign.setBillCycle(rs.getString("bill_cycle"));
						return sign;
					}
				});
		return userApprovalDetail;
	}

	public int getPendingApprovalsByRole(String billCycle, int roleID) {
		Integer count = (Integer) jdbcTemplate.queryForObject(pendingApproval, new Object[] { billCycle, roleID },
				Integer.class);
		return count;
	}

	public void setUserApproval(String billCycle, String userID) {
		jdbcTemplate.update(insertUserApproval, new Object[] { billCycle, userID, userID });
	}

	public void updateUserApproval(String billCycle, String userID) {
		jdbcTemplate.update(updateUserApproval, new Object[] { userID, billCycle, userID });
	}

	public void rejectUserApproval(String billCycle, String rejectedBy, String rejectedFor) {
		jdbcTemplate.update(rejectApproval, new Object[] { rejectedBy, rejectedBy, billCycle, rejectedFor });
	}
}
