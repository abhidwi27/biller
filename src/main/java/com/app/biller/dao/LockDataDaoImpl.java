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

import com.app.biller.domain.Tower;
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
	
	@Value("${UNSET_DATA_LOCK}")
	private String unsetDataLock;
	
	@Value("${CHECK_LOCK_BY_USER}")
	private String checkLockByUser;
	
	@Value("${CHECK_LOCK_ENTRY}")
	private String checkLockEntry;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public User checkLockForTower(String billCycle, int towerID, int accountId) {
		logger.info("Checking lock for billCycle " + billCycle + " towerID " + towerID);
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
		
		List<User> lockedBy =  jdbcTemplate.query(selectDataLock, new Object[] { billCycle, towerID, accountId }, userMap);

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
			logger.info("No user found who had locked for billCycle " + billCycle + " towerID " + towerID);
			return null;
		}
	}
	
	public Tower checkLockByUser(String userID, String billCycle, int accountId) {
		logger.info("Checking lock by user billCycle " + billCycle);
		RowMapper<Tower> rowMap = new RowMapper<Tower>() {
			@Override
			public Tower mapRow(ResultSet rs, int rownumber) throws SQLException {
				Tower towerInfo = new Tower ();
				towerInfo.setTowerID(rs.getInt(1));
				towerInfo.setTowerName(rs.getString(2));
				return towerInfo;
			}
		};
		List<Tower> tower = jdbcTemplate.query(checkLockByUser, new Object[] { userID, billCycle, accountId }, rowMap);

		if (tower.isEmpty()) {
			logger.info("No tower locked by userID " + userID);
			return null;
		} else if (tower.size() == 1) {
			return tower.get(0);
		} else {
			return null;
		}
	}

	public void setLock(String billCycle, String userID, int towerID, int accountId) {
		
		int count = jdbcTemplate.queryForObject(checkLockEntry, new Object[] {billCycle, towerID, accountId }, Integer.class);
		
		if(count == 0) {
		int result = jdbcTemplate.update(insertDataLock, new Object[] { billCycle, towerID, userID, accountId });
			if(result != 0) {
				logger.info("Lock established by userID " + userID + " for towerID " + towerID + " for billCycle " + billCycle);
			}else {
				logger.info("Lock couldn't be establish by userID " + userID + " for towerID " + towerID + " for billCycle " + billCycle);
			}		
		}else {
			int result = jdbcTemplate.update(updateDataLock, new Object[] { userID , billCycle, towerID, accountId});
			if(result != 0) {
				logger.info("Lock updated by userID " + userID + " for towerID " + towerID + " for billCycle " + billCycle);
			}else {
				logger.info("Lock couldn't be updated by userID " + userID + " for towerID " + towerID + " for billCycle " + billCycle);
			}
		}
	}

	public void unSetLock(String userID, String billCycle, int towerID, int accountId) {
		int result = jdbcTemplate.update(unsetDataLock, new Object[] { billCycle, userID, towerID, accountId });
		if(result != 0) {
			logger.info("Lock unset by userID " + userID + " for towerID " + towerID + " for billCycle " + billCycle);
		}else {
			logger.info("Lock couldn't be unset by userID " + userID + " for towerID " + towerID + " for billCycle " + billCycle);
		}
	}
	
}
