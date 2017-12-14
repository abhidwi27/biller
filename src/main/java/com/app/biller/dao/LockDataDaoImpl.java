package com.app.biller.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.app.biller.domain.User;

@Repository("dataLockDao")
public class LockDataDaoImpl implements LockDataDao {

	private static final Logger logger = LoggerFactory.getLogger(LockDataDaoImpl.class);

	@Value("${SELECT_DATA_LOCK}")
	private String selectDataLock;

	@Value("${INSERT_DATA_LOCK}")
	private String insertDataLock;

	@Value("${UPDATE_DATA_LOCK}")
	private String updateDataLock;
	
	@Value("${CHECK_LOCK_BY_USER}")
	private String checkLockByUser;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public User checkLockForTower(String billCycle, int towerID) {
		RowMapper<User> userMap = new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rownumber) throws SQLException {
					User user = new User();
					user.setName(rs.getString("Name"));
					user.setRoleID(rs.getInt("roleID"));
					user.setUserID(rs.getString("userID"));	
					user.setRoleDesc(rs.getString("roleDesc"));
					return user;
			}
		};
		
		List<User> lockedBy =  jdbcTemplate.query(selectDataLock, new Object[] { billCycle, towerID }, userMap);

		/*if (lockedBy.isEmpty()) {
			return "";
		} else if (lockedBy.size() == 1) {
			return lockedBy.get(0);
		} else {
			return "";
		}*/
		
		if (!lockedBy.isEmpty() && lockedBy.size() == 1) {
			return lockedBy.get(0);
		} else {
			return null;
		}
	}
	
	public String checkLockByUser(String userID, String billCycle) {
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		List<String> tower = jdbcTemplate.query(checkLockByUser, new Object[] { userID, billCycle }, rowMap);

		if (tower.isEmpty()) {
			return "";
		} else if (tower.size() == 1) {
			return tower.get(0);
		} else {
			return "";
		}
	}

	public void setLock(String billCycle, String userID, int towerID) {
		jdbcTemplate.update(insertDataLock, new Object[] { billCycle, towerID, userID });
	}

	public void unSetLock(String userID, String billCycle, int towerID) {
		jdbcTemplate.update(updateDataLock, new Object[] { billCycle, userID, towerID });
	}
}
