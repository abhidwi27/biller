package com.app.biller.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.app.biller.model.Tower;

public class TowerDaoImpl {
	
	@Value("${GET_TOWER_LIST}")
	String towerListSql;
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Tower> getTowerList(){
		
		RowMapper<Tower> towerMap = new RowMapper<Tower>() {
			@Override
			public Tower mapRow(ResultSet rs, int rowNum) throws SQLException {
				Tower tower = new Tower();
				tower.setTowerID(rs.getInt("tower_id"));
				tower.setTowerDesc(rs.getString("tower_desc"));
				return tower;
			}
		};

		List<Tower> towerList;

		towerList = (List<Tower>) jdbcTemplate.query(towerListSql, towerMap);
		
		return towerList;		
		
	}

}
