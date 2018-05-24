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

import com.app.biller.controller.LoginController;
import com.app.biller.domain.GroupApproval;

@Repository("groupApprovalDao")
public class GroupApprovalDaoImpl implements GroupApprovalDao {

	@Value("${SELECT_GROUP_APPROVAL}")
	String selectGroupApproval;

	@Value("${UPDATE_BILLCYCLE_STATUS}")
	String updateBillCycleStatus;

	@Value("${INSERT_GROUP_APPROVAL}")
	String insertGroupApproval;
	
	@Value("${CHECK_GROUP_APPROVAL_ENTRY}")
	String checkGroupApprovalEntry;
	
	@Value("${RESET_GROUP_APPROVAL}")
	String resetGroupApproval;
	
	private static final Logger logger = LoggerFactory.getLogger(GroupApprovalDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public GroupApproval getGroupApprovals(String billCycle) {
		logger.info("Getting group approvals for billCycle " +billCycle);
		RowMapper<GroupApproval> groupMap = new RowMapper<GroupApproval>() {
			@Override
			public GroupApproval mapRow(ResultSet rs, int rowNum) throws SQLException {
				GroupApproval group = new GroupApproval();
				group.setDmApproval(rs.getInt("dm_approval"));
				group.setBamApproval(rs.getInt("bam_approval"));
				group.setSrBamApproval(rs.getInt("srbam_approval"));
				group.setPmoApproval(rs.getInt("pmo_approval"));
				return group;
			}
		};

		List<GroupApproval> allGroupStatus;

		allGroupStatus = (List<GroupApproval>) jdbcTemplate.query(selectGroupApproval, new Object[] { billCycle },
				groupMap);

		if (allGroupStatus.size() == 0) {
			GroupApproval group = new GroupApproval();
			group.setBamApproval(0);
			group.setBamApproval(0);
			group.setSrBamApproval(0);
			group.setPmoApproval(0);
			logger.warn("Approval process not yet started for billCycle " +billCycle);
			return group;
		} else {
			return allGroupStatus.get(0);
		}
	}

	public void setBillCycleStatus(String billCycle, int billCycleStatus) {
		logger.info("Setting approval status as "+ billCycleStatus + " for billCycle " +billCycle);
		int result = jdbcTemplate.update(updateBillCycleStatus, new Object[] { billCycle, billCycleStatus });
		if(result == 0) {
			logger.warn("Approval status coundn't set as "+ billCycleStatus + " for billCycle " +billCycle);
		}
	}

	public void createGroupApproval(String billCycle, String userID) {
		int count = this.checkGroupApprovalEntry(billCycle);
		
		if(count == 0) {
			int result = jdbcTemplate.update(insertGroupApproval, new Object[] { billCycle, userID });
			if(result !=0) {
				logger.info("Entry in Group Approval Table for billCycle " +billCycle);
			}else {
				logger.warn("Entry cound't be inserted in Group Approval Table for billCycle " +billCycle);
			}	
		}else {
			this.resetGroupApproval(billCycle);
		}
	}
	
	public int checkGroupApprovalEntry(String billCycle) {
		int count = jdbcTemplate.queryForObject(checkGroupApprovalEntry, new Object[] { billCycle }, Integer.class);
		
		if(count == 0) {			
				logger.info("No Entry found in Group Approval Table for billCycle " +billCycle);
		}else {
				logger.warn("Entry already exists in Group Approval Table for billCycle " +billCycle);
		}
		return count;
		
	}
	
	public void resetGroupApproval(String billCycle) {
		int count = jdbcTemplate.update(resetGroupApproval, new Object[] { billCycle });
		
		if(count > 0) {			
				logger.info("Group Approval Table has been reset for billCycle " +billCycle);
		}else {
				logger.warn("Group Approval Table couldn't be reset for billCycle " +billCycle);
		}		
		
	}

	public void updateGroupApproval(String billCycle, String groupName, int status) {
		String groupColName = groupName + "_approval";
		String sql = "update biller.blr_group_approval set " + groupColName + " = " + status + " where bill_cycle = '"
				+ billCycle + "'";
		int result = jdbcTemplate.update(sql);
		if (result == 0) {
			logger.info("Staus updated for " + groupName + " in Group Approval Table for billCycle " +billCycle + " as " + status);
		}else {
			logger.warn("Status couldn't be updated  in Group Approval Table for"  + groupName +  " billCycle " +billCycle + " as " + status);
		}
	}

}
