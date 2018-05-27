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

import com.app.biller.domain.ILCData;
import com.app.biller.util.BillerUtil;

@Repository("ilcDataDao")
public class ILCDataDaoImpl implements ILCDataDao {

	private static final Logger logger = LoggerFactory.getLogger(ILCDataDaoImpl.class);
	
	@Autowired
	ReferenceDataDao referenceDataDao;

	@Value("${DELETE_ILC_DATA}")
	private String deleteIlcData;

	@Value("${INSERT_ILC_DATA}")
	private String insertIlcData;

	@Value("${INSERT_ILC_DATA_APPROVAL}")
	private String insertIlcDataApproval;

	@Value("${SELECT_ILC_DATA}")
	private String selectIlcData;
	
	@Value("${SELECT_ALL_ILC_DATA}")
	private String selectAllIlcData;

	@Value("${SELECT_ILC_EMPLOYEE_LIST}")
	private String selectILCEmployeeList;
	
	@Value("${SELECT_ALL_ILC_EMPLOYEE_LIST}")
	private String selectAllILCEmployeeList;

	@Value("${SELECT_ILC_WR_LIST}")
	private String selectILCWrList;
	
	@Value("${SELECT_ALL_ILC_WR_LIST}")
	private String selectAllILCWrList;

	@Value("${SELECT_ILC_WEEKEND_LIST}")
	private String selectILCWeekendList;
	
	@Value("${SELECT_ALL_ILC_WEEKEND_LIST}")
	private String selectAllILCWeekendList;
	
	@Value("${SELECT_ILC_REMARKS_LIST}")
	private String selectILCRemarksList;
	
	@Value("${SELECT_ALL_ILC_REMARKS_LIST}")
	private String selectAllILCRemarksList;
	
	@Value("${SELECT_OTHERS_TOWER_ILC_DATA}")
	private String selectOtherTowersILCData;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void createILCData(ArrayList<ILCData> ilcModelList, String billCycle, String userId, String uploadDataType) throws DataAccessException {

		try {
			logger.info("Deleting ILC Data...");			
			int result = jdbcTemplate.update(deleteIlcData, new Object[]  {billCycle});
			if(result != 0) {
				logger.info("ILC Data deleted successfully...");
			}else {
				logger.warn("Couldn't delete ILC Data, may be ILC Table is empty or there is no data to delete for bill cycle " +billCycle);
			}
		} catch (DataAccessException dae) {
			logger.info("Delete ILCData Exception: " ,dae);
			throw dae;
			
		}
		
		if(ilcModelList == null) {
			logger.error("ilcModelList is null, ILC Data can not be inserted");
			throw new NullPointerException("null ilcModelList");
		}
		try {
			int[] batchUpdateResult = jdbcTemplate.batchUpdate(insertIlcData, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, ilcModelList.get(i).getEmpID());
					ps.setString(2, ilcModelList.get(i).getEmpName());
					ps.setString(3, ilcModelList.get(i).getClaimCode());
					ps.setString(4, ilcModelList.get(i).getActivity());
					ps.setString(5, BillerUtil.getDateStr(ilcModelList.get(i).getWeekEndDate()));
					ps.setDouble(6, ilcModelList.get(i).getTotHrs());
					ps.setString(7, ilcModelList.get(i).getShiftType());
					ps.setString(8, ilcModelList.get(i).getUsInd());
					ps.setString(9, ilcModelList.get(i).getOnOffShore());
					ps.setString(10, ilcModelList.get(i).getBillingType());
					ps.setString(11, ilcModelList.get(i).getCategory());
					ps.setString(12, ilcModelList.get(i).getBam());
					ps.setString(13, ilcModelList.get(i).getAppArea());
					ps.setString(14, ilcModelList.get(i).getBusinessArea());
					ps.setString(15, ilcModelList.get(i).getMonth());
					ps.setString(16, ilcModelList.get(i).getQuarter());
					ps.setString(17, ilcModelList.get(i).getDm());
					ps.setString(18, ilcModelList.get(i).getAsm());
					ps.setString(19, ilcModelList.get(i).getAsd());
					ps.setString(20, ilcModelList.get(i).getWrNo());
					ps.setString(21, ilcModelList.get(i).getIsTicket());
					ps.setString(22, ilcModelList.get(i).getStaffType());
					ps.setString(23, ilcModelList.get(i).getIsCTC());
					ps.setString(24, ilcModelList.get(i).getIsRTC());
					ps.setInt(25, ilcModelList.get(i).getPlannedHrs());
					ps.setString(26, ilcModelList.get(i).getIsBillable());
					ps.setString(27, ilcModelList.get(i).getRemarks());
					ps.setString(28, ilcModelList.get(i).getCtcOrRtc());
					ps.setString(29, ilcModelList.get(i).getWorkType());
					ps.setString(30, ilcModelList.get(i).getWrDesc());
					ps.setString(31, ilcModelList.get(i).getCostCenter());
					ps.setString(32, ilcModelList.get(i).getCategory2());
					ps.setString(33, ilcModelList.get(i).getOnOffLanded());
					ps.setString(34, ilcModelList.get(i).getTower());
					ps.setString(35, ilcModelList.get(i).getAsmItwr());
					ps.setString(36, ilcModelList.get(i).getAsdItwr());
					ps.setInt(37, ilcModelList.get(i).getItwrActual());
					ps.setString(38, ilcModelList.get(i).getGroupType());
					ps.setString(39, ilcModelList.get(i).getVendorClass());
					ps.setString(40, ilcModelList.get(i).getWrIncDef());
					ps.setString(41, billCycle);
					ps.setString(42, ilcModelList.get(i).getAccountId());
				}
	
				@Override
				public int getBatchSize() {
					int batchSize = ilcModelList.size();
					logger.info("ILC Batch Update Size is " +batchSize);
					return batchSize;
				}
			});
			logger.info("ILC Batch update: records updated = " + batchUpdateResult.length );
		}catch(DataAccessException dae) {
			logger.error("ILC Batch Update Error", dae);
			throw dae;
		}

		// jdbcTemplate.update(insertIlcDataSign, new Object[] { billCycle,
		// userId,uploadDataType });
	}

	@Override
	public ArrayList<ILCData> readILCData(String billCycle, int towerID , int accountId) {
		logger.info("Reading ILC Data for billCycle " + billCycle + " and towerId " + towerID + " and account Id " + accountId);
		Object queryParam[];
		String readIlcQuery;
		
		if(towerID == 0 ) {
			queryParam = new Object [] { billCycle, accountId };
			readIlcQuery = selectAllIlcData;
		}else if(towerID == 7 ) {
			queryParam = new Object [] {billCycle , accountId};
			readIlcQuery = selectOtherTowersILCData;
		}else  {
			queryParam = new Object [] {towerID, billCycle , accountId};
			readIlcQuery = selectIlcData;
		}
		
		ArrayList<ILCData> ilcDataList = (ArrayList<ILCData>) jdbcTemplate.query(readIlcQuery,
				queryParam, new RowMapper<ILCData>() {
					@Override
					public ILCData mapRow(ResultSet rs, int rownumber) throws SQLException {
						ILCData ilcModel = new ILCData();
						ilcModel.setEmpID(rs.getString("emp_id"));
						ilcModel.setEmpName(rs.getString("emp_name"));
						ilcModel.setClaimCode(rs.getString("claim_code"));
						ilcModel.setActivity(rs.getString("activity"));
						ilcModel.setWeekEndDate(rs.getString("weekend_date"));
						//ilcModel.setTotHrs(Integer.parseInt(rs.getString("total_hours")));
						ilcModel.setTotHrs(Double.parseDouble(rs.getString("total_hours")));
						ilcModel.setShiftType(rs.getString("shift_detail"));
						ilcModel.setUsInd(rs.getString("us_ind"));
						ilcModel.setOnOffShore(rs.getString("on_off_shore"));
						ilcModel.setBillingType(rs.getString("billing_type"));
						ilcModel.setCategory(rs.getString("category"));
						ilcModel.setBam(rs.getString("bam"));
						ilcModel.setAppArea(rs.getString("app_area"));
						ilcModel.setBusinessArea(rs.getString("business_area"));
						ilcModel.setMonth(rs.getString("month"));
						ilcModel.setQuarter(rs.getString("quarter"));
						ilcModel.setDm(rs.getString("dm"));
						ilcModel.setAsm(rs.getString("asm"));
						ilcModel.setAsd(rs.getString("asd"));
						ilcModel.setWrNo(rs.getString("wr_no"));
						ilcModel.setIsTicket(rs.getString("is_ticket"));
						ilcModel.setStaffType(rs.getString("staff_type"));
						ilcModel.setIsCTC(rs.getString("is_ctc"));
						ilcModel.setIsRTC(rs.getString("is_rtc"));
						ilcModel.setPlannedHrs(Integer.parseInt(rs.getString("planned_hours")));
						ilcModel.setIsBillable(rs.getString("is_billable"));
						ilcModel.setRemarks(rs.getString("remarks"));
						ilcModel.setCtcOrRtc(rs.getString("ctc_or_rtc"));
						ilcModel.setWorkType(rs.getString("work_type"));
						ilcModel.setWrDesc(rs.getString("wr_desc"));
						ilcModel.setCostCenter(rs.getString("cost_center"));
						ilcModel.setCategory2(rs.getString("category2"));
						ilcModel.setOnOffLanded(rs.getString("on_off_landed"));
						ilcModel.setTower(rs.getString("tower"));
						ilcModel.setLastModified(rs.getString("last_modified"));
						ilcModel.setAsmItwr(rs.getString("asm_itwr"));
						ilcModel.setAsdItwr(rs.getString("asd_itwr"));
						ilcModel.setItwrActual(Integer.parseInt(rs.getString("itwr_actual")));
						ilcModel.setGroupType(rs.getString("group_type"));
						ilcModel.setVendorClass(rs.getString("vendor_class"));
						ilcModel.setWrIncDef(rs.getString("wr_inc_def"));
						ilcModel.setAccountId(rs.getString("account_id"));
						return ilcModel;
					}
				});

		return ilcDataList;
	}

	@Override
	public ArrayList<ILCData> readCustomILCData(String billCycle, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3, String [] val3, int key4, String [] val4, int key5, String [] val5) {
		
		StringBuilder sb = new StringBuilder();
		ArrayList<ILCData> ilcDataList;
		String fieldName1 = null;
		String fieldName2 = null;
		String fieldName3 = null;
		String fieldName4 = null;
		String fieldName5 = null;
		
		
		if(key1 != 0) {
			fieldName1 = referenceDataDao.getColumnNameById(key1);
		}
		if(key2 != 0) {
			fieldName2 = referenceDataDao.getColumnNameById(key2);
		}
		if(key3 !=0) {
			fieldName3 = referenceDataDao.getColumnNameById(key3);
		}
		if(key4 != 0) {
			fieldName4 = referenceDataDao.getColumnNameById(key4);
		}
		if(key5 != 0) {
			fieldName5 = referenceDataDao.getColumnNameById(key5);
		}
		StringBuilder val1Str = new StringBuilder();
		StringBuilder val2Str = new StringBuilder();
		StringBuilder val3Str = new StringBuilder();
		StringBuilder val4Str = new StringBuilder();
		StringBuilder val5Str = new StringBuilder();
		
		if(val1.length != 0) {
			for(int i=0; i<val1.length-1; i++) {
				val1Str = val1Str.append("'").append(val1[i]).append("'").append(",");
			}
			val1Str = val1Str.append("'").append(val1[val1.length-1]).append("'");
		}
		
		if(val2.length != 0) {
			for(int i=0; i<val2.length-1; i++) {
				val2Str = val2Str.append("'").append(val2[i]).append("'").append(",");
			}
			val2Str = val2Str.append("'").append(val2[val2.length-1]).append("'");
		}
		
		if(val3.length != 0) {
			for(int i=0; i<val3.length-1; i++) {
				val3Str = val3Str.append("'").append(val3[i]).append("'").append(",");
			}
			val3Str = val3Str.append("'").append(val3[val3.length-1]).append("'");
		}
		
		if(val4.length != 0) {
			for(int i=0; i<val4.length-1; i++) {
				val4Str = val4Str.append("'").append(val4[i]).append("'").append(",");
			}
			val4Str = val4Str.append("'").append(val4[val4.length-1]).append("'");
		}
		
		if(val5.length != 0) {
			for(int i=0; i<val5.length-1; i++) {
				val5Str = val5Str.append("'").append(val5[i]).append("'").append(",");
			}
			val5Str = val5Str.append("'").append(val5[val5.length-1]).append("'");
		}
		
		
		sb.append(
				"SELECT * FROM biller.blr_ilc_data ilc where ilc.bill_cycle ='")
		  .append(billCycle).append("'");
		
		sb.append(" and ilc.account_id in(select account_desc from biller.blr_accounts ac where ac.account_id= ")				
		.append(accountId)
		.append(")");
		
		if(val1.length!=0 && fieldName1 != null) {
			sb.append(" AND ilc.")
			  .append(fieldName1)
			  .append(" in ( ")
			  .append(val1Str.toString())
			  .append(")");
			}
			
			if(val2.length !=0 && fieldName2 != null) {
			  sb.append(" AND ilc.")
			  .append(fieldName2)
			  .append(" in ( ")
			  .append(val2Str.toString())
			  .append(")");
			}
			
			if(val3.length !=0 && fieldName3 != null) {
			 sb.append(" AND ilc.")
			  .append(fieldName3)
			  .append(" in ( ")
			  .append(val3Str.toString())
			  .append(")");
			}
			
			if(val4.length !=0 && fieldName4 != null) {
			  sb.append(" AND ilc.")
			  .append(fieldName4)
			  .append(" in ( ")
			  .append(val4Str.toString())
			  .append(")");
			}
			
			if(val5.length !=0 && fieldName5 != null) {
			  sb.append(" AND ilc.")
			  .append(fieldName5)
			  .append(" in ( ")
			  .append(val5Str.toString())
			  .append(")");
			}
		
		if(towerID != 0 && towerID !=7) {
			sb.append(" and ilc.tower in(select tower_desc from biller.blr_tower tower where tower.tower_id= ")				
			.append(towerID)
			.append(")");
		  
		}		
		if(towerID == 7) {
			  sb.append(" AND  ilc.tower not in(select tower_desc from biller.blr_tower where tower_id in(1,2,3,4,5,6)) ");
		}
		try {  
		ilcDataList = (ArrayList<ILCData>) jdbcTemplate.query(sb.toString(),
				new RowMapper<ILCData>() {
					@Override
					public ILCData mapRow(ResultSet rs, int rownumber) throws SQLException {
						ILCData ilcModel = new ILCData();
						ilcModel.setEmpID(rs.getString("emp_id"));
						ilcModel.setEmpName(rs.getString("emp_name"));
						ilcModel.setClaimCode(rs.getString("claim_code"));
						ilcModel.setActivity(rs.getString("activity"));
						ilcModel.setWeekEndDate(rs.getString("weekend_date"));
						//ilcModel.setTotHrs(Integer.parseInt(rs.getString("total_hours")));
						ilcModel.setTotHrs(Double.parseDouble(rs.getString("total_hours")));
						ilcModel.setShiftType(rs.getString("shift_detail"));
						ilcModel.setUsInd(rs.getString("us_ind"));
						ilcModel.setOnOffShore(rs.getString("on_off_shore"));
						ilcModel.setBillingType(rs.getString("billing_type"));
						ilcModel.setCategory(rs.getString("category"));
						ilcModel.setBam(rs.getString("bam"));
						ilcModel.setAppArea(rs.getString("app_area"));
						ilcModel.setBusinessArea(rs.getString("business_area"));
						ilcModel.setMonth(rs.getString("month"));
						ilcModel.setQuarter(rs.getString("quarter"));
						ilcModel.setDm(rs.getString("dm"));
						ilcModel.setAsm(rs.getString("asm"));
						ilcModel.setAsd(rs.getString("asd"));
						ilcModel.setWrNo(rs.getString("wr_no"));
						ilcModel.setIsTicket(rs.getString("is_ticket"));
						ilcModel.setStaffType(rs.getString("staff_type"));
						ilcModel.setIsCTC(rs.getString("is_ctc"));
						ilcModel.setIsRTC(rs.getString("is_rtc"));
						ilcModel.setPlannedHrs(Integer.parseInt(rs.getString("planned_hours")));
						ilcModel.setIsBillable(rs.getString("is_billable"));
						ilcModel.setRemarks(rs.getString("remarks"));
						ilcModel.setCtcOrRtc(rs.getString("ctc_or_rtc"));
						ilcModel.setWorkType(rs.getString("work_type"));
						ilcModel.setWrDesc(rs.getString("wr_desc"));
						ilcModel.setCostCenter(rs.getString("cost_center"));
						ilcModel.setCategory2(rs.getString("category2"));
						ilcModel.setOnOffLanded(rs.getString("on_off_landed"));
						ilcModel.setTower(rs.getString("tower"));
						ilcModel.setLastModified(rs.getString("last_modified"));
						ilcModel.setAsmItwr(rs.getString("asm_itwr"));
						ilcModel.setAsdItwr(rs.getString("asd_itwr"));
						ilcModel.setItwrActual(Integer.parseInt(rs.getString("itwr_actual")));
						ilcModel.setGroupType(rs.getString("group_type"));
						ilcModel.setVendorClass(rs.getString("vendor_class"));
						ilcModel.setWrIncDef(rs.getString("wr_inc_def"));
						ilcModel.setAccountId(rs.getString("account_id"));
						
						return ilcModel;
					}
				});
		}catch(DataAccessException dae) {
			logger.error("Error in read custom ILC Data", dae);
			ilcDataList = null;
		}
		return ilcDataList;
	}

	public List<String> getEmployeeList(String billCycle, int towerID) {
		logger.info("Getting ILC employee list for billCycle " + billCycle + " and towerId " + towerID);
		Object queryParam[];
		String getIlcEmployeeQuery;
		if(towerID != 0 ) {
			queryParam = new Object [] {towerID, billCycle };
			getIlcEmployeeQuery = selectILCEmployeeList;
		}else {
			queryParam = new Object [] { billCycle };
			getIlcEmployeeQuery = selectAllILCEmployeeList;
		}
		
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};

		List<String> ilcEmployeeList = jdbcTemplate.query(getIlcEmployeeQuery, queryParam, rowMap);
		return ilcEmployeeList;
	}

	public List<String> getWRList(String billCycle, int towerID) {
		
		Object queryParam[];
		String getIlcWrQuery;
		if(towerID != 0 ) {
			queryParam = new Object [] {towerID, billCycle };
			getIlcWrQuery = selectILCWrList;
		}else {
			queryParam = new Object [] { billCycle };
			getIlcWrQuery = selectAllILCWrList;
		}
		
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};

		List<String> ilcWrList = jdbcTemplate.query(getIlcWrQuery, queryParam, rowMap);
		return ilcWrList;
	}

	public List<String> getWeekendList(String billCycle, int towerID) {
		logger.info("Getting ILC Wekeend list for billCycle " + billCycle + " and towerId " + towerID);
		Object queryParam[];
		String getIlcWeekendQuery;
		if(towerID != 0 ) {
			queryParam = new Object [] {towerID, billCycle };
			getIlcWeekendQuery = selectILCWeekendList;
		}else {
			queryParam = new Object [] { billCycle };
			getIlcWeekendQuery = selectAllILCWeekendList;
		}
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};

		List<String> ilcWeekendList = jdbcTemplate.query(getIlcWeekendQuery, queryParam, rowMap);
		return ilcWeekendList;
	}
	
	
	public List<String> getRemarksList(String billCycle, int towerID) {
		logger.info("Getting ILC Remakrs list for billCycle " + billCycle + " and towerId " + towerID);
		Object queryParam[];
		String getIlcRemarkQuery;
		if(towerID != 0 ) {
			queryParam = new Object [] {towerID, billCycle };
			getIlcRemarkQuery = selectILCRemarksList;
		}else {
			queryParam = new Object [] { billCycle };
			getIlcRemarkQuery = selectAllILCRemarksList;
		}
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};

		List<String> ilcRemarksList = jdbcTemplate.query(getIlcRemarkQuery, queryParam, rowMap);
		return ilcRemarksList;
	}
	
public List<String> getLevel1Values(String billCycle, int towerID, int accountId, int key1){
		
		StringBuilder sb = new StringBuilder();
		String fieldName = referenceDataDao.getColumnNameById(key1);
		
		sb.append("SELECT DISTINCT ilc.")
		  .append(fieldName)
		  .append(" FROM biller.blr_ilc_data ilc WHERE ilc.bill_cycle='")
		  .append(billCycle)
		  .append("'");
		
		sb.append(" AND ilc.account_id in(")
		  .append("SELECT ac.account_desc FROM biller.blr_accounts ac WHERE ac.account_id=")
		  .append(accountId)
		  .append(")");
		
		if(towerID !=0 && towerID != 7) {
		  sb.append(" AND ilc.tower in(")
		  .append("SELECT tower_desc FROM biller.blr_tower WHERE tower_id = ")
		  .append(towerID)
		  .append(")");
		}
		if(towerID == 7) {
			  sb.append(" AND  ilc.tower not in(select tower_desc from biller.blr_tower where tower_id in(1,2,3,4,5,6)) ");
		}
		sb.append(" ORDER BY 1 ASC");
		  
		  
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		
		List<String> level1ValueList = jdbcTemplate.query(sb.toString(), new Object[] {}, rowMap);
		return level1ValueList;
	}
	
	public List<String> getLevel2Values(String billCycle, int towerID, int accountId, int key1, String [] val1, int key2){
		StringBuilder sb = new StringBuilder();
		String fieldName1 = referenceDataDao.getColumnNameById(key1);
		String fieldName2 = referenceDataDao.getColumnNameById(key2);
		StringBuilder val1Str = new StringBuilder();
		
		if(val1.length != 0) {
			for(int i=0; i<val1.length-1; i++) {
				val1Str = val1Str.append("'").append(val1[i]).append("'").append(",");
			}
			val1Str = val1Str.append("'").append(val1[val1.length-1]).append("'");
		}
		
		sb.append("SELECT DISTINCT ilc.")
		  .append(fieldName2)
		  .append(" FROM biller.blr_ilc_data ilc WHERE ilc.bill_cycle='")
		  .append(billCycle)
		  .append("'")
		  .append(" AND ilc.")
		  .append(fieldName1)
		  .append(" in ( ")
		  .append(val1Str.toString())
		  .append(")");		
		
		sb.append(" AND ilc.account_id in(")
		  .append("SELECT ac.account_desc FROM biller.blr_accounts ac WHERE ac.account_id=")
		  .append(accountId)
		  .append(")");
		
		if(towerID !=0 && towerID != 7) {
		  sb.append(" AND ilc.tower in(")
		  .append("SELECT tower_desc FROM biller.blr_tower WHERE tower_id = ")
		  .append(towerID)
		  .append(")");
		}
		
		if(towerID == 7) {
			  sb.append(" AND  ilc.tower not in(select tower_desc from biller.blr_tower where tower_id in(1,2,3,4,5,6)) ");
		}
		
		sb.append(" ORDER BY 1 ASC");		  
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		
		List<String> level2ValueList = jdbcTemplate.query(sb.toString(), new Object[] {}, rowMap);
		return level2ValueList;
	}
	
	public List<String> getLevel3Values(String billCycle, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3){
		StringBuilder sb = new StringBuilder();
		String fieldName1 = referenceDataDao.getColumnNameById(key1);
		String fieldName2 = referenceDataDao.getColumnNameById(key2);
		String fieldName3 = referenceDataDao.getColumnNameById(key3);
		StringBuilder val1Str = new StringBuilder();
		StringBuilder val2Str = new StringBuilder();
		
		if(val1.length != 0) {
			for(int i=0; i<val1.length-1; i++) {
				val1Str = val1Str.append("'").append(val1[i]).append("'").append(",");
			}
			val1Str = val1Str.append("'").append(val1[val1.length-1]).append("'");
		}
		
		if(val2.length != 0) {
			for(int i=0; i<val2.length-1; i++) {
				val2Str = val2Str.append("'").append(val2[i]).append("'").append(",");
			}
			val2Str = val2Str.append("'").append(val2[val2.length-1]).append("'");
		}
		
		sb.append("SELECT DISTINCT ilc.")
		  .append(fieldName3)
		  .append(" FROM biller.blr_ilc_data ilc WHERE ilc.bill_cycle='")
		  .append(billCycle)
		  .append("'")
		  .append(" AND ilc.")
		  .append(fieldName1)
		  .append(" in ( ")
		  .append(val1Str.toString())
		  .append(")")
		  .append(" AND ilc.")
		  .append(fieldName2)
		  .append(" in ( ")
		  .append(val2Str.toString())
		  .append(")");
		
		sb.append(" AND ilc.account_id in(")
		  .append("SELECT ac.account_desc FROM biller.blr_accounts ac WHERE ac.account_id=")
		  .append(accountId)
		  .append(")");
		
		if(towerID !=0 && towerID!=7) {
		  sb.append(" AND ilc.tower in(")
		  .append("SELECT tower_desc FROM biller.blr_tower WHERE tower_id = ")
		  .append(towerID)
		  .append(")");
		}
		
		if(towerID == 7) {
			  sb.append(" AND  ilc.tower not in(select tower_desc from biller.blr_tower where tower_id in(1,2,3,4,5,6)) ");
		}
		
		sb.append(" ORDER BY 1 ASC");		  
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		
		List<String> level3ValueList = jdbcTemplate.query(sb.toString(), new Object[] {}, rowMap);
		return level3ValueList;
	}
	
	public List<String> getLevel4Values(String billCycle, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3, String [] val3, int key4){
		StringBuilder sb = new StringBuilder();
		String fieldName1 = referenceDataDao.getColumnNameById(key1);
		String fieldName2 = referenceDataDao.getColumnNameById(key2);
		String fieldName3 = referenceDataDao.getColumnNameById(key3);
		String fieldName4 = referenceDataDao.getColumnNameById(key4);
		StringBuilder val1Str = new StringBuilder();
		StringBuilder val2Str = new StringBuilder();
		StringBuilder val3Str = new StringBuilder();
		
		if(val1.length != 0) {
			for(int i=0; i<val1.length-1; i++) {
				val1Str = val1Str.append("'").append(val1[i]).append("'").append(",");
			}
			val1Str = val1Str.append("'").append(val1[val1.length-1]).append("'");
		}
		
		if(val2.length != 0) {
			for(int i=0; i<val2.length-1; i++) {
				val2Str = val2Str.append("'").append(val2[i]).append("'").append(",");
			}
			val2Str = val2Str.append("'").append(val2[val2.length-1]).append("'");
		}
		
		if(val3.length != 0) {
			for(int i=0; i<val3.length-1; i++) {
				val3Str = val3Str.append("'").append(val3[i]).append("'").append(",");
			}
			val3Str = val3Str.append("'").append(val3[val3.length-1]).append("'");
		}
		
		sb.append("SELECT DISTINCT ilc.")
		  .append(fieldName4)
		  .append(" FROM biller.blr_ilc_data ilc WHERE ilc.bill_cycle='")
		  .append(billCycle)
		  .append("'")
		  .append(" AND ilc.")
		  .append(fieldName1)
		  .append(" in ( ")
		  .append(val1Str.toString())
		  .append(")")
		  .append(" AND ilc.")
		  .append(fieldName2)
		  .append(" in ( ")
		  .append(val2Str.toString())
		  .append(")")
		  .append(" AND ilc.")
		  .append(fieldName3)
		  .append(" in ( ")
		  .append(val3Str.toString())
		  .append(")");
		
		sb.append(" AND ilc.account_id in(")
		  .append("SELECT ac.account_desc FROM biller.blr_accounts ac WHERE ac.account_id=")
		  .append(accountId)
		  .append(")");
		
		if(towerID !=0 && towerID != 7) {
		  sb.append(" AND ilc.tower in(")
		  .append("SELECT tower_desc FROM biller.blr_tower WHERE tower_id = ")
		  .append(towerID)
		  .append(")");
		}
		if(towerID == 7) {
			  sb.append(" AND  ilc.tower not in(select tower_desc from biller.blr_tower where tower_id in(1,2,3,4,5,6)) ");
		}
		sb.append(" ORDER BY 1 ASC");		  
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		
		List<String> level4ValueList = jdbcTemplate.query(sb.toString(), new Object[] {}, rowMap);
		return level4ValueList;
	}
	
	public List<String> getLevel5Values(String billCycle, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3, String [] val3, int key4, String [] val4, int key5){
		StringBuilder sb = new StringBuilder();
		String fieldName1 = referenceDataDao.getColumnNameById(key1);
		String fieldName2 = referenceDataDao.getColumnNameById(key2);
		String fieldName3 = referenceDataDao.getColumnNameById(key3);
		String fieldName4 = referenceDataDao.getColumnNameById(key4);
		String fieldName5 = referenceDataDao.getColumnNameById(key5);
		StringBuilder val1Str = new StringBuilder();
		StringBuilder val2Str = new StringBuilder();
		StringBuilder val3Str = new StringBuilder();
		StringBuilder val4Str = new StringBuilder();
		
		if(val1.length != 0) {
			for(int i=0; i<val1.length-1; i++) {
				val1Str = val1Str.append("'").append(val1[i]).append("'").append(",");
			}
			val1Str = val1Str.append("'").append(val1[val1.length-1]).append("'");
		}
		
		if(val2.length != 0) {
			for(int i=0; i<val2.length-1; i++) {
				val2Str = val2Str.append("'").append(val2[i]).append("'").append(",");
			}
			val2Str = val2Str.append("'").append(val2[val2.length-1]).append("'");
		}
		
		if(val3.length != 0) {
			for(int i=0; i<val3.length-1; i++) {
				val3Str = val3Str.append("'").append(val3[i]).append("'").append(",");
			}
			val3Str = val3Str.append("'").append(val3[val3.length-1]).append("'");
		}
		
		if(val4.length != 0) {
			for(int i=0; i<val4.length-1; i++) {
				val4Str = val4Str.append("'").append(val4[i]).append("'").append(",");
			}
			val4Str = val4Str.append("'").append(val4[val4.length-1]).append("'");
		}
		
		sb.append("SELECT DISTINCT ilc.")
		  .append(fieldName5)
		  .append(" FROM biller.blr_ilc_data ilc WHERE ilc.bill_cycle='")
		  .append(billCycle)
		  .append("'")
		  .append(" AND ilc.")
		  .append(fieldName1)
		  .append(" in ( ")
		  .append(val1Str.toString())
		  .append(")")
		  .append(" AND ilc.")
		  .append(fieldName2)
		  .append(" in ( ")
		  .append(val2Str.toString())
		  .append(")")
		  .append(" AND ilc.")
		  .append(fieldName3)
		  .append(" in ( ")
		  .append(val3Str.toString())
		  .append(")")
		  .append(" AND ilc.")
		  .append(fieldName4)
		  .append(" in ( ")
		  .append(val4Str.toString())
		  .append(")");
		
		sb.append(" AND ilc.account_id in(")
		  .append("SELECT ac.account_desc FROM biller.blr_accounts ac WHERE ac.account_id=")
		  .append(accountId)
		  .append(")");
		
		if(towerID !=0 && towerID != 7) {
		  sb.append(" AND ilc.tower in(")
		  .append("SELECT tower_desc FROM biller.blr_tower WHERE tower_id = ")
		  .append(towerID)
		  .append(")");
		}
		if(towerID == 7) {
			  sb.append(" AND  ilc.tower not in(select tower_desc from biller.blr_tower where tower_id in(1,2,3,4,5,6)) ");
		}
		sb.append(" ORDER BY 1 ASC");		  
		
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		
		List<String> level5ValueList = jdbcTemplate.query(sb.toString(), new Object[] {}, rowMap);
		return level5ValueList;
	}
}
