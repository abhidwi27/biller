package com.app.biller.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.app.biller.domain.GroupApproval;

@Repository("groupApprovalDao")
public class GroupApprovalDaoImpl implements GroupApprovalDao {

	@Value("${SELECT_GROUP_APPROVAL}")
	String selectGroupApproval;

	@Value("${UPDATE_BILLCYCLE_STATUS}")
	String updateBillCycleStatus;

	@Value("${INSERT_GROUP_APPROVAL}")
	String insertGroupApproval;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public GroupApproval getGroupApprovals(String billCycle) {

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

			return group;
		} else {
			return allGroupStatus.get(0);
		}
	}

	public void setBillCycleStatus(String billCycle, int billCycleStatus) {
		jdbcTemplate.update(updateBillCycleStatus, new Object[] { billCycle, billCycleStatus });
	}

	public void createGroupApproval(String billCycle, String userID) {
		jdbcTemplate.update(insertGroupApproval, new Object[] { billCycle, userID });
	}

	public void updateGroupApproval(String billCycle, String groupName, int status) {
		String groupColName = groupName + "_approval";
		String sql = "update biller.blr_group_approval set " + groupColName + " = " + status + " where bill_cycle = '"
				+ billCycle + "'";
		jdbcTemplate.update(sql);
	}

}
