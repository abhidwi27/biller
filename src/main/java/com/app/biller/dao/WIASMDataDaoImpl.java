package com.app.biller.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.app.biller.domain.WIASMData;
import com.app.biller.util.BillerUtil;

@Repository("wiASMDataDao")

public class WIASMDataDaoImpl implements WIASMDataDao  {

	private static final Logger logger = LoggerFactory.getLogger(WIASMDataDaoImpl.class);

	@Value("${DELETE_WIASM_DATA}")
	private String deleteWIASMData;

	@Value("${INSERT_WIASM_DATA}")
	private String insertWIASMData;

	

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void uploadWIASMData(ArrayList<WIASMData> wiASMModelList, String billCycle, String userId, String uploadDataType) throws DataAccessException {

		try {
			logger.info("Deleting WIASM Data..");
			int result = jdbcTemplate.update(deleteWIASMData);
			if(result != 0) {
				logger.info("WIASM Data deleted successfully..");
			}else {
				logger.warn("WIASM data couldn't be deleted, may be table is already empty	");
			}
		} catch (DataAccessException dae) {
			logger.info("Delete WIASMData Exception: ",dae);
			throw dae;
		}
		
		if(wiASMModelList == null) {
			logger.error("wiASMModelList is null, WIASMData can not be inserted");
			throw new NullPointerException("null wiASMModelList");
		}
		
		try {		
			int[] batchUpdateResult = jdbcTemplate.batchUpdate(insertWIASMData, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, wiASMModelList.get(i).getAcc_id());
					ps.setString(2, wiASMModelList.get(i).getWrkItem_id());
					ps.setString(3, wiASMModelList.get(i).getWrkItem_desc());
					ps.setString(4, wiASMModelList.get(i).getCategory());
					ps.setString(5, wiASMModelList.get(i).getOn_off_shore());
					ps.setString(6, wiASMModelList.get(i).getBilling_type());
					ps.setString(7, wiASMModelList.get(i).getApplication());
					ps.setString(8, wiASMModelList.get(i).getBuss_area());
					ps.setString(9, wiASMModelList.get(i).getBam());
					ps.setString(10, wiASMModelList.get(i).getDm());
					ps.setString(11, wiASMModelList.get(i).getAsm());
					ps.setString(12, wiASMModelList.get(i).getAsd());
					//ps.setString(41, billCycle);
					
				}
	
				@Override
				public int getBatchSize() {
					int batchSize = wiASMModelList.size();
					logger.info("WIASM Batch Update Size is " +batchSize);
					return batchSize;
				}
			});
			
			logger.info("WIASM Batch update: records updated = " + batchUpdateResult.length );
			
		}catch(DataAccessException dae) {
			logger.error("WIASM Batch Update Error", dae);
			throw dae;
		}

		
	}

	
}
