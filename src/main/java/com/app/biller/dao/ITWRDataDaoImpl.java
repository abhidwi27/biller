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


import com.app.biller.domain.ITWRData;
import com.app.biller.util.BillerUtil;


@Repository("itwrDataDao")

public class ITWRDataDaoImpl implements ITWRDataDao {

	private static final Logger logger = LoggerFactory.getLogger(ILCDataDaoImpl.class);

	@Value("${DELETE_ITWR_DATA}")
	private String deleteITWRData;

	@Value("${INSERT_ITWR_DATA}")
	private String insertITWRData;

	
	

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void createITWRData(ArrayList<ITWRData> itwrModelList, String billCycle, String userId, String uploadDataType) throws DataAccessException {

		try {
			logger.info("Deleting ITWR Data..");
			int result = jdbcTemplate.update(deleteITWRData);
			if(result != 0) {
				logger.info("ITWR Data deleted successfully");
			}else {
				logger.info("ITWR Data couldn't be deleted, may be table is already empty");
			}
		} catch (DataAccessException dae) {
			logger.info("Delete ITWRData Exception: ", dae);
			throw dae;
		}
		
		if(itwrModelList == null) {
			logger.error("itwrModelList is null, ITWRData can not be inserted");
			throw new NullPointerException("Null itwrModelList");
		}
		
		try {
			int[] batchUpdateResult = jdbcTemplate.batchUpdate(insertITWRData, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, itwrModelList.get(i).getReq_no());
					ps.setString(2, itwrModelList.get(i).getReq_title());
					ps.setString(3, itwrModelList.get(i).getCoo_intake_no());
					ps.setString(4, itwrModelList.get(i).getIt_sme());
					ps.setString(5, itwrModelList.get(i).getBus_area());
					ps.setString(6, itwrModelList.get(i).getWork_type());
					ps.setString(7, itwrModelList.get(i).getDemand_type());
					ps.setString(8, itwrModelList.get(i).getFund_type());
					ps.setString(9, itwrModelList.get(i).getCost_center());
					ps.setString(10, itwrModelList.get(i).getVendor_class());
					ps.setString(11, itwrModelList.get(i).getAsm());
					ps.setString(12, itwrModelList.get(i).getPrimary_vendor());
					ps.setString(13, itwrModelList.get(i).getAsd());
					ps.setString(14, itwrModelList.get(i).getOverall_status());
					ps.setString(15, itwrModelList.get(i).getVendor_est_hours());
					ps.setString(16, itwrModelList.get(i).getVendor_actual_hours());
					ps.setString(17, itwrModelList.get(i).getPcr_est_hours());
					ps.setString(18, itwrModelList.get(i).getPcr_actual_hours());
					//ps.setString(20, itwrModelList.get(i).getWrNo());
					//ps.setString(21, itwrModelList.get(i).getIsTicket());
					//ps.setString(22, itwrModelList.get(i).getStaffType());
					//ps.setString(23, itwrModelList.get(i).getIsCTC());
				
				}
	
				@Override
				public int getBatchSize() {
					int batchSize = itwrModelList.size();
					logger.error("ITWR Batch Size is " + batchSize);
					return batchSize;
				}
			});
			logger.info("ITWR Batch update: records updated = " + batchUpdateResult.length );
		}catch(DataAccessException dae) {
			logger.error("ITWR Batch Update Error", dae);
			throw dae;			
		}
	}
}
