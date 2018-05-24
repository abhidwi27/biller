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

//import com.app.biller.domain.ILCData;
import com.app.biller.domain.SLAData;

@Repository("slaDataDao")
public class SLADataDaoImpl implements SLADataDao {
	
	private static final Logger logger = LoggerFactory.getLogger(SLADataDaoImpl.class);
	
	@Value("${SELECT_SLA_EMPLOYEE_LIST}")
	private String selectSLAEmployeeList;
	
	@Value("${SELECT_ALL_SLA_EMPLOYEE_LIST}")
	private String selectAllSLAEmployeeList;
	

	@Value("${SELECT_SLA_WR_LIST}")
	private String selectSLAWrList;
	
	@Value("${SELECT_ALL_SLA_WR_LIST}")
	private String selectAllSLAWrList;

	@Value("${SELECT_SLA_WEEKEND_LIST}")
	private String selectSLAWeekendList;
	
	@Value("${SELECT_ALL_SLA_WEEKEND_LIST}")
	private String selectAllSLAWeekendList;
	
	@Value("${SELECT_SLA_REMARKS_LIST}")
	private String selectSLARemarksList;
	
	@Value("${SELECT_ALL_SLA_REMARKS_LIST}")
	private String selectAllSLARemarksList;

	@Value("${SELECT_SLA_DATA}")
	private String selectSLAData;
	
	@Value("${SELECT_ALL_SLA_DATA}")
	private String selectAllSLAData;

	@Value("${INSERT_SLA_DATA}")
	private String insertSLAData;

	@Value("${UPDATE_SLA_DATA}")
	private String updateSLAData;
	
	@Value("${DELETE_SLA_DATA}")
	private String deleteSLAData;
	
	@Value("${GET_MAX_SEQID}")
	private String getMaxSeqID;
	
	@Value("${INSERT_NEW_SLA_RECORD}")
	private String insertNewSLARecord;
	
	@Value("${GET_ACTIVE_BILLCYCLE}")
	private String getActiveBillCycle;
	
	@Value("${GET_MODIFIED_BY}")
	private String getModifiedBy;
	
	@Value("${DELETE_SLA_DATA_FOR_BILL_CYCLE}")
	private String deleteSlaDataForBillCycle;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	@Override
	public void createSLAData(ArrayList<SLAData> SLAModelList, String billCycle, String userId, String uploadDataType) throws Exception {
		logger.info("Creating SLA Data for billCycle " + billCycle);
		
		if(SLAModelList == null) {
			logger.error("SLAModelList is null, SLAData can not be inserted");
			throw new Exception("null SLAModelList");
		}
		try {
			int[] batchUpdateResult = jdbcTemplate.batchUpdate(insertSLAData, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					
					ps.setString(1, SLAModelList.get(i).getWeekEndDate());
					ps.setString(2, SLAModelList.get(i).getAsm());
					ps.setString(3, SLAModelList.get(i).getAsd());
					ps.setString(4, SLAModelList.get(i).getAsmItwr());
					ps.setString(5, SLAModelList.get(i).getAsdItwr());
					ps.setString(6, SLAModelList.get(i).getEmpID());
					ps.setString(7, SLAModelList.get(i).getEmpName());
					ps.setString(8, SLAModelList.get(i).getActivity());
					ps.setString(9, SLAModelList.get(i).getWorkItem());
					ps.setString(10, SLAModelList.get(i).getOnOffShore());
					ps.setDouble(11, SLAModelList.get(i).getTotHrs());
					ps.setString(12, SLAModelList.get(i).getShiftDetail());
					ps.setString(13, SLAModelList.get(i).getCategory());
					ps.setString(14, SLAModelList.get(i).getBillType());
					ps.setString(15, SLAModelList.get(i).getDm());
					ps.setString(16, SLAModelList.get(i).getAppArea());
					ps.setString(17, SLAModelList.get(i).getBusinessArea());
					ps.setString(18, SLAModelList.get(i).getTower());
					ps.setString(19, SLAModelList.get(i).getBam());
					ps.setString(20, SLAModelList.get(i).getRemarks());
					ps.setString(21, SLAModelList.get(i).getIsBillable());
					ps.setString(22, SLAModelList.get(i).getWrNo());
					ps.setInt(23, SLAModelList.get(i).getPlannedHrs());
					ps.setString(24, SLAModelList.get(i).getComments());
					ps.setString(25, SLAModelList.get(i).getWrDesc());
					ps.setString(26, SLAModelList.get(i).getCostCenter());
					ps.setString(27, SLAModelList.get(i).getFundType());
					ps.setString(28, SLAModelList.get(i).getVendorClass());
					ps.setString(29, SLAModelList.get(i).getAccountId());
					ps.setString(30, billCycle);
					ps.setString(31,userId);					
				}	
				@Override
				public int getBatchSize() {
					int batchSize = SLAModelList.size();
					logger.info("SLA Batch Update Size is " +batchSize);
					return batchSize;
				}
			});
			logger.info("SLA Batch update: records updated = " + batchUpdateResult.length );
		}catch(DataAccessException dae) {
			logger.error("SLA Batch Update Error ", dae);
			throw new Exception("SLA Batch Update Error ");
		}

		
	}
	
	
	
	
	
	
	@Override
	public ArrayList<SLAData> readSLAData(String billCycle, int towerID, int accountId) {
		
		Object queryParam[];
		String readSlaQuery;
		if(towerID != 0 ) {
			queryParam = new Object [] {towerID, billCycle, accountId };
			readSlaQuery = selectSLAData;
		}else {
			queryParam = new Object [] { billCycle, accountId };
			readSlaQuery = selectAllSLAData;
		}
		
		ArrayList<SLAData> slaDataList = (ArrayList<SLAData>) jdbcTemplate.query(readSlaQuery,
				queryParam, new RowMapper<SLAData>() {
					@Override
					public SLAData mapRow(ResultSet rs, int rownumber) throws SQLException {
						SLAData slaModel = new SLAData();
						slaModel.setSeqID(rs.getInt("seq_id"));
						slaModel.setWeekEndDate(rs.getString("weekend_date"));
						slaModel.setAsm(rs.getString("asm"));
						slaModel.setAsd(rs.getString("asd"));
						slaModel.setAsmItwr(rs.getString("asm_itwr"));
						slaModel.setAsdItwr(rs.getString("asd_itwr"));
						slaModel.setEmpID(rs.getString("emp_id"));
						slaModel.setEmpName(rs.getString("emp_name"));
						slaModel.setActivity(rs.getString("activity"));
						slaModel.setWorkItem(rs.getString("work_item"));
						slaModel.setOnOffShore(rs.getString("on_off_shore"));
						slaModel.setTotHrs(rs.getDouble("total_hours"));
						slaModel.setShiftDetail(rs.getString("shift_detail"));
						slaModel.setCategory(rs.getString("category"));
						slaModel.setBillType(rs.getString("bill_type"));
						slaModel.setDm(rs.getString("dm"));
						slaModel.setAppArea(rs.getString("app_area"));
						slaModel.setBusinessArea(rs.getString("business_area"));
						slaModel.setTower(rs.getString("tower"));
						slaModel.setBam(rs.getString("bam"));
						slaModel.setRemarks(rs.getString("remarks"));
						slaModel.setIsBillable(rs.getString("is_billable"));
						slaModel.setWrNo(rs.getString("wr_no"));
						slaModel.setPlannedHrs(rs.getInt("planned_hours"));
						slaModel.setComments(rs.getString("comments"));
						slaModel.setWrDesc(rs.getString("wr_desc"));
						slaModel.setCostCenter(rs.getString("cost_center"));
						slaModel.setFundType(rs.getString("fund_type"));
						slaModel.setVendorClass(rs.getString("vendor_class"));
						slaModel.setModifiedBy(rs.getString("modified_by"));
						slaModel.setAccountId(rs.getString("account_id"));

						return slaModel;
					}
				});

		return slaDataList;
	}

	@Override
	public void updateSLAData(String billCycle,  ArrayList<SLAData> updateSLADataList) {
		jdbcTemplate.batchUpdate(updateSLAData, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				ps.setString(1, updateSLADataList.get(i).getWeekEndDate());
				ps.setString(2, updateSLADataList.get(i).getAsm());
				ps.setString(3, updateSLADataList.get(i).getAsd());
				ps.setString(4, updateSLADataList.get(i).getAsmItwr());
				ps.setString(5, updateSLADataList.get(i).getAsdItwr());
				ps.setString(6, updateSLADataList.get(i).getEmpID());
				ps.setString(7, updateSLADataList.get(i).getEmpName());
				ps.setString(8, updateSLADataList.get(i).getActivity());
				ps.setString(9, updateSLADataList.get(i).getWorkItem());
				ps.setString(10, updateSLADataList.get(i).getOnOffShore());
				ps.setDouble(11, updateSLADataList.get(i).getTotHrs());
				ps.setString(12, updateSLADataList.get(i).getShiftDetail());
				ps.setString(13, updateSLADataList.get(i).getCategory());
				ps.setString(14, updateSLADataList.get(i).getBillType());
				ps.setString(15, updateSLADataList.get(i).getDm());
				ps.setString(16, updateSLADataList.get(i).getAppArea());
				ps.setString(17, updateSLADataList.get(i).getBusinessArea());
				ps.setString(18, updateSLADataList.get(i).getTower());
				ps.setString(19, updateSLADataList.get(i).getBam());
				ps.setString(20, updateSLADataList.get(i).getRemarks());
				ps.setString(21, updateSLADataList.get(i).getIsBillable());
				ps.setString(22, updateSLADataList.get(i).getWrNo());
				ps.setInt(23, updateSLADataList.get(i).getPlannedHrs());
				ps.setString(24, updateSLADataList.get(i).getComments());
				ps.setString(25, updateSLADataList.get(i).getWrDesc());
				ps.setString(26, updateSLADataList.get(i).getCostCenter());
				ps.setString(27, updateSLADataList.get(i).getFundType());
				ps.setString(28, updateSLADataList.get(i).getVendorClass());
				ps.setString(29, updateSLADataList.get(i).getAccountId());
				ps.setString(30, updateSLADataList.get(i).getChangeLog());				
				ps.setInt(31, updateSLADataList.get(i).getActive());
				ps.setString(32, updateSLADataList.get(i).getModifiedBy());
				ps.setString(33, billCycle);
				ps.setInt(34, updateSLADataList.get(i).getSeqID());

			}

			@Override
			public int getBatchSize() {
				return updateSLADataList.size();
			}
		});
	}

	@Override
	public void deleteSLAData(String billCycle, List<Integer> seqIDList) {
		logger.info("Making SLA Data inactive for billCycle " + billCycle + " and seqIdList " + seqIDList.toString());
		// TODO Auto-generated method stub
		jdbcTemplate.batchUpdate(deleteSLAData, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, billCycle);
				ps.setInt(2, seqIDList.get(i));
			}

			@Override
			public int getBatchSize() {
				return seqIDList.size();
			}
		});
		
	}
	
	@Override
	public void deleteSLAData(String billCycle) {
		logger.info("Deleting SLA for billCycle " + billCycle);
		// TODO Auto-generated method stub
		int result = jdbcTemplate.update(deleteSlaDataForBillCycle, new Object[] {billCycle});
		
	}

	@Override
	public void createNewSLARecord(String billCycle,  ArrayList<SLAData> newSLADataList) {
		logger.info("Creating new SLA Record for billCycle " + billCycle);
		jdbcTemplate.batchUpdate(insertNewSLARecord, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				//int seqID = GetSeqID(billCycle);
				
				//ps.setInt(1, seqID);
				ps.setString(1, newSLADataList.get(i).getWeekEndDate());
				ps.setString(2, newSLADataList.get(i).getAsm());
				ps.setString(3, newSLADataList.get(i).getAsd());
				ps.setString(4, newSLADataList.get(i).getAsmItwr());
				ps.setString(5, newSLADataList.get(i).getAsdItwr());
				ps.setString(6, newSLADataList.get(i).getEmpID());
				ps.setString(7, newSLADataList.get(i).getEmpName());
				ps.setString(8, newSLADataList.get(i).getActivity());
				ps.setString(9, newSLADataList.get(i).getWorkItem());
				ps.setString(10, newSLADataList.get(i).getOnOffShore());
				ps.setDouble(11, newSLADataList.get(i).getTotHrs());
				ps.setString(12, newSLADataList.get(i).getShiftDetail());
				ps.setString(13, newSLADataList.get(i).getCategory());
				ps.setString(14, newSLADataList.get(i).getBillType());
				ps.setString(15, newSLADataList.get(i).getDm());
				ps.setString(16, newSLADataList.get(i).getAppArea());
				ps.setString(17, newSLADataList.get(i).getBusinessArea());
				ps.setString(18, newSLADataList.get(i).getTower());
				ps.setString(19, newSLADataList.get(i).getBam());
				ps.setString(20, newSLADataList.get(i).getRemarks());
				ps.setString(21, newSLADataList.get(i).getIsBillable());
				ps.setString(22, newSLADataList.get(i).getWrNo());
				ps.setInt(23, newSLADataList.get(i).getPlannedHrs());
				ps.setString(24, newSLADataList.get(i).getComments());
				ps.setString(25, newSLADataList.get(i).getWrDesc());
				ps.setString(26, newSLADataList.get(i).getCostCenter());
				ps.setString(27, newSLADataList.get(i).getFundType());
				ps.setString(28, newSLADataList.get(i).getVendorClass());
				ps.setString(29, newSLADataList.get(i).getAccountId());
				ps.setString(30, billCycle);
				ps.setString(31, newSLADataList.get(i).getChangeLog());
				ps.setInt(32, newSLADataList.get(i).getActive());
				ps.setString(33, newSLADataList.get(i).getModifiedBy());

			}

			@Override
			public int getBatchSize() {
				return newSLADataList.size();
			}
			
			int GetSeqID(String billCycle){
				int maxSeqID =  jdbcTemplate.queryForObject(getMaxSeqID, new Object[] { billCycle }, Integer.class);
				return maxSeqID + 1;
			}
		});
	}

	@Override
	public ArrayList<SLAData> readCustomSLAData(String billCycle, int towerID, String weekEndDate, String wrNo,
			String empName, int billable, String remarks, int accountId) {
		logger.info("Reading Customized SLA Data for billCycle " + billCycle + " and towerId " + towerID + " and account Id " + accountId
				+ " weekendDate " + weekEndDate + " wrNo " + wrNo + " empName " + empName + " billable " +billable + " remarks " + remarks);
		StringBuilder sb = new StringBuilder();

		sb.append(
				"SELECT * FROM biller.blr_sla_data sla where sla.active = 1 and sla.bill_cycle ='")
		  .append(billCycle).append("'");
		
		if(towerID != 0) {
			sb.append(" and sla.tower in(select tower_desc from biller.blr_tower tower where tower.tower_id= ")				
			.append(towerID)
			.append(")");
		  
		}
		if (!(weekEndDate.equals("ALL"))) {
			sb.append(" and sla.weekend_date='").append(weekEndDate).append("'");
		}

		if (!(wrNo.equals("ALL"))) {
			sb.append(" and sla.wr_no='").append(wrNo).append("'");
		}

		if (!(empName.equals("ALL"))) {
			sb.append(" and sla.emp_name='").append(empName).append("'");
		}
		if (billable !=0) {
			String isBillable = null;
			switch (billable) {
			case 1:
					isBillable = "Yes";
					break;
			case 2:
					isBillable = "No";
					break;
			default:
					isBillable = null;
			}
					
			sb.append(" and sla.is_billable='").append(isBillable).append("'");
		}
		if (!(remarks.equals("ALL"))) {
			sb.append(" and sla.remarks='").append(remarks).append("'");
		}
		
		sb.append(" and sla.account_id in(select account_desc from biller.blr_accounts ac where ac.account_id= ")				
		.append(accountId)
		.append(")");
		
		ArrayList<SLAData> slaDataList = (ArrayList<SLAData>) jdbcTemplate.query(sb.toString(),
				new Object[] { }, new RowMapper<SLAData>() {
					@Override
					public SLAData mapRow(ResultSet rs, int rownumber) throws SQLException {
						SLAData slaModel = new SLAData();
						slaModel.setSeqID(rs.getInt("seq_id"));
						slaModel.setWeekEndDate(rs.getString("weekend_date"));
						slaModel.setAsm(rs.getString("asm"));
						slaModel.setAsd(rs.getString("asd"));
						slaModel.setAsmItwr(rs.getString("asm_itwr"));
						slaModel.setAsdItwr(rs.getString("asd_itwr"));
						slaModel.setEmpID(rs.getString("emp_id"));
						slaModel.setEmpName(rs.getString("emp_name"));
						slaModel.setActivity(rs.getString("activity"));
						slaModel.setWorkItem(rs.getString("work_item"));
						slaModel.setOnOffShore(rs.getString("on_off_shore"));
						slaModel.setTotHrs(rs.getDouble("total_hours"));
						slaModel.setShiftDetail(rs.getString("shift_detail"));
						slaModel.setCategory(rs.getString("category"));
						slaModel.setBillType(rs.getString("bill_type"));
						slaModel.setDm(rs.getString("dm"));
						slaModel.setAppArea(rs.getString("app_area"));
						slaModel.setBusinessArea(rs.getString("business_area"));
						slaModel.setTower(rs.getString("tower"));
						slaModel.setBam(rs.getString("bam"));
						slaModel.setRemarks(rs.getString("remarks"));
						slaModel.setIsBillable(rs.getString("is_billable"));
						slaModel.setWrNo(rs.getString("wr_no"));
						slaModel.setPlannedHrs(rs.getInt("planned_hours"));
						slaModel.setComments(rs.getString("comments"));
						slaModel.setWrDesc(rs.getString("wr_desc"));
						slaModel.setCostCenter(rs.getString("cost_center"));
						slaModel.setFundType(rs.getString("fund_type"));
						slaModel.setVendorClass(rs.getString("vendor_class"));
						slaModel.setModifiedBy(rs.getString("modified_by"));
						slaModel.setAccountId(rs.getString("account_id"));

						return slaModel;
					}
				});

		return slaDataList;
	}

	public List<String> getEmployeeList(String billCycle, int towerID) {
		logger.info("Getting SLA employee list for billCycle " + billCycle + " and towerId " + towerID);
		Object queryParam[];
		String getSlaEmployeeQuery;
		if(towerID != 0 ) {
			queryParam = new Object [] {towerID, billCycle };
			getSlaEmployeeQuery = selectSLAEmployeeList;
		}else {
			queryParam = new Object [] { billCycle };
			getSlaEmployeeQuery = selectAllSLAEmployeeList;
		}
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};

		List<String> slaEmployeeList = jdbcTemplate.query(getSlaEmployeeQuery, queryParam, rowMap);
		return slaEmployeeList;
	}

	public List<String> getWRList(String billCycle, int towerID) {
		logger.info("Getting SLA WR list for billCycle " + billCycle + " and towerId " + towerID);
		Object queryParam[];
		String getSlaWrQuery;
		if(towerID != 0 ) {
			queryParam = new Object [] {towerID, billCycle };
			getSlaWrQuery = selectSLAWrList;
		}else {
			queryParam = new Object [] { billCycle };
			getSlaWrQuery = selectAllSLAWrList;
		}
		
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};

		List<String> slaWrList = jdbcTemplate.query(getSlaWrQuery, queryParam, rowMap);
		return slaWrList;
	}

	public List<String> getWeekendList(String billCycle, int towerID) {
		logger.info("Getting SLA weekend list for billCycle " + billCycle + " and towerId " + towerID);
		Object queryParam[];
		String getSlaWeekEndQuery;
		if(towerID != 0 ) {
			queryParam = new Object [] {towerID, billCycle };
			getSlaWeekEndQuery = selectSLAWeekendList;
		}else {
			queryParam = new Object [] { billCycle };
			getSlaWeekEndQuery = selectAllSLAWeekendList;
		}
		
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};

		List<String> slaWeekendList = jdbcTemplate.query(getSlaWeekEndQuery, queryParam , rowMap);
		return slaWeekendList;
	}
	
	public List<String> getRemarksList(String billCycle, int towerID) {
		
		Object queryParam[];
		String getSlaRemarksQuery;
		if(towerID != 0 ) {
			queryParam = new Object [] {towerID, billCycle };
			getSlaRemarksQuery = selectSLARemarksList;
		}else {
			queryParam = new Object [] { billCycle };
			getSlaRemarksQuery = selectAllSLARemarksList;
		}
		
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};

		List<String> slaRemarksList = jdbcTemplate.query(getSlaRemarksQuery, queryParam , rowMap);
		return slaRemarksList;
	}
	
	public String getActiveBillCycle() {
		logger.info("Getting Active billCycle...");
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};

		List<String> activeBillCycle = jdbcTemplate.query(getActiveBillCycle, rowMap);
		
		if(activeBillCycle.size() > 0) {
			return activeBillCycle.get(0);
		}
		else {
			logger.warn("No Active billCycle found...");
			return null;
		}
		
	}
	
	public String getModifiedBy(int seqId) {
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};

		List<String> modifiedByStr = jdbcTemplate.query(getModifiedBy, new Object[] {seqId},rowMap);
		
		if(modifiedByStr.size() > 0) {
			return modifiedByStr.get(0);
		}
		else {			
			return null;
		}
	}
}
