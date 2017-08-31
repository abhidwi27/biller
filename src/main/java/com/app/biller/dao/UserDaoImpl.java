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

import com.app.biller.model.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Value("${LOGIN_FETCH_USER}")
	private String fetchUser;

	@Value("${LOGIN_FETCH_PASSWORD}")
	private String fetchPassword;

	@Value("${LOGIN_SET_PASSWORD}")
	private String setPassword;

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
		List<String> strLst = jdbcTemplate.query(fetchPassword, new Object[] { userid }, rowMap);
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
		int result = jdbcTemplate.update(setPassword, new Object[] { password, userid });
		return result;
	}

	@Override
	public User createUserProfile(String userid) {
		User user = jdbcTemplate.queryForObject(fetchUser, new Object[] { userid }, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rownumber) throws SQLException {
				User usr = new User();
				usr.setUserID(rs.getString("user_id"));
				usr.setName(rs.getString("first_name"));
				usr.setRoleID(rs.getInt("role_id"));
				return usr;
			}
		});
		return user;
	}
}