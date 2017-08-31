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

@Repository("dataLockDao")
public class LockDataDaoImpl implements LockDataDao {

	private static final Logger logger = LoggerFactory.getLogger(LockDataDaoImpl.class);

	@Value("${CHECK_LOCK}")
	private String checkLock;

	@Value("${SET_LOCK}")
	private String setLock;

	@Value("${UNSET_LOCK}")
	private String unsetLock;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public String checkLock(String billCycle, int towerID) {
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		List<String> lockedBy = jdbcTemplate.query(checkLock, new Object[] { billCycle, towerID }, rowMap);

		if (lockedBy.isEmpty()) {
			return "";
		} else if (lockedBy.size() == 1) {
			return lockedBy.get(0);
		} else {
			return "";
		}
	}

	public void setLock(String billCycle, String userID, int towerID) {
		jdbcTemplate.update(setLock, new Object[] { billCycle, towerID, userID });
	}

	public void unSetLock(String userID, String billCycle, int towerID) {
		jdbcTemplate.update(unsetLock, new Object[] { billCycle, userID, towerID });
	}
}
