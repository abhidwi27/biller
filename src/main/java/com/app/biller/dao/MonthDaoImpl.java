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

import com.app.biller.model.Month;
import com.app.biller.model.Tower;

@Repository("monthDao")
public class MonthDaoImpl implements MonthDao{
	
	
	@Value("${GET_MONTH}")
	String getMonth;
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public ArrayList<Month> getMonthList(){
		RowMapper<Month> monthMap = new RowMapper<Month>() {
			@Override
			public Month mapRow(ResultSet rs, int rowNum) throws SQLException {
				Month month = new Month();
				month.setMonthID(rs.getString("month_id"));
				month.setMonthDesc(rs.getString("month_desc"));
				return month;
			}
		};

		ArrayList<Month> monthList;

		monthList = (ArrayList<Month>) jdbcTemplate.query(getMonth, monthMap);
		
		return monthList;
	}

}
