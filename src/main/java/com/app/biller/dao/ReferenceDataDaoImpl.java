package com.app.biller.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.app.biller.domain.Month;
import com.app.biller.domain.Tower;

@Repository("referenceDataDao")
public class ReferenceDataDaoImpl implements ReferenceDataDao {

	@Value("${SELECT_ILC_TABLE_HEADER}")
	String selectIlcTableHeader;

	@Value("${SELECT_SLA_TABLE_HEADER}")
	String selectSlaTableHeader;

	@Value("${SELECT_TOWER_LIST}")
	String selectTowerList;

	@Value("${SELECT_MONTH_LIST}")
	String selectMonthList;

	@Value("${SELECT_YEAR_LIST}")
	String selectYearList;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<String> getILCTableHeader() {
		RowMapper<String> rowMap = getRowMapper();
		List<String> header = jdbcTemplate.query(selectIlcTableHeader, rowMap);
		return header;
	}

	@Override
	public List<String> getSLATableHeader() {
		RowMapper<String> rowMap = getRowMapper();
		List<String> header = jdbcTemplate.query(selectSlaTableHeader, rowMap);
		return header;
	}

	public List<Tower> getTowerList() {
		RowMapper<Tower> towerMap = new RowMapper<Tower>() {
			@Override
			public Tower mapRow(ResultSet rs, int rowNum) throws SQLException {
				Tower tower = new Tower();
				tower.setTowerID(rs.getInt("tower_id"));
				tower.setTowerName(rs.getString("tower_desc"));
				return tower;
			}
		};

		List<Tower> towerList = (List<Tower>) jdbcTemplate.query(selectTowerList, towerMap);
		return towerList;
	}

	public ArrayList<Month> getMonthList() {
		RowMapper<Month> monthMap = new RowMapper<Month>() {
			@Override
			public Month mapRow(ResultSet rs, int rowNum) throws SQLException {
				Month month = new Month();
				month.setMonthID(rs.getString("month_id"));
				month.setMonthName(rs.getString("month_desc"));
				return month;
			}
		};

		ArrayList<Month> monthList = (ArrayList<Month>) jdbcTemplate.query(selectMonthList, monthMap);
		return monthList;
	}

	public List<String> getYearList() {
		RowMapper<String> rowMap = getRowMapper();
		List<String> year = jdbcTemplate.query(selectYearList, rowMap);
		return year;
	}

	private RowMapper<String> getRowMapper() {
		RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		return rowMapper;
	}
}
