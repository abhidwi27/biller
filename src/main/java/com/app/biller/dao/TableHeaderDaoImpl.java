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

@Repository("tableHeaderDao")
public class TableHeaderDaoImpl implements TableHeaderDao {
	
	

	@Value("${GET_ILC_TABLE_HEADER}")
	String ilcTableHeader;

	@Value("${GET_SLA_TABLE_HEADER}")
	String slaTableHeader;
	
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<String> getILCTableHeader(){
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		
		List<String> header =  jdbcTemplate.query(ilcTableHeader, rowMap);
		
		return header;
	}

	

	@Override
	public List<String> getSLATableHeader() {
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		
		List<String> header =  jdbcTemplate.query(slaTableHeader, rowMap);
		
		return header;
	}

}
