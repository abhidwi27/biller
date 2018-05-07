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
	public void uploadWIASMData(ArrayList<WIASMData> wiASMModelList, String billCycle, String userId, String uploadDataType) {

		try {
			jdbcTemplate.update(deleteWIASMData);
		} catch (DataAccessException dae) {
			logger.info("Delete WIASMData Exception: " + dae.getMessage());
		}

		jdbcTemplate.batchUpdate(insertWIASMData, new BatchPreparedStatementSetter() {
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
				return wiASMModelList.size();
			}
		});

		// jdbcTemplate.update(insertIlcDataSign, new Object[] { billCycle,
		// userId,uploadDataType });
	}

	/*@Override
	public ArrayList<ILCData> readILCData(String billCycle, int towerID , int accountId) {
		
		Object queryParam[];
		String readIlcQuery;
		if(towerID != 0 ) {
			queryParam = new Object [] {towerID, billCycle , accountId};
			readIlcQuery = selectIlcData;
		}else {
			queryParam = new Object [] { billCycle, accountId };
			readIlcQuery = selectAllIlcData;
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
						ilcModel.setShiftType(rs.getString("shift_type"));
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

	/*@Override
	public ArrayList<ILCData> readCustomILCData(String billCycle, int towerID, String weekEndDate, String wrNo,
			String empName, int billable, String remarks, int accountId) {

		StringBuilder sb = new StringBuilder();

		sb.append(
				"SELECT * FROM biller.blr_ilc_data ilc where ")
				.append("ilc.bill_cycle ='").append(billCycle).append("'");
		if(towerID != 0) {
			sb.append(" and ilc.tower in(select tower_desc from biller.blr_tower tower where tower.tower_id= ")				
			.append(towerID)
			.append(")");
		  
		}
		
		if (!(weekEndDate.equals("ALL"))) {
			sb.append(" and ilc.weekend_date='").append(weekEndDate).append("'");
		}

		if (!(wrNo.equals("ALL"))) {
			sb.append(" and ilc.wr_no='").append(wrNo).append("'");
		}

		if (!(empName.equals("ALL"))) {
			sb.append(" and ilc.emp_name='").append(empName).append("'");
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
					
			sb.append(" and ilc.is_billable='").append(isBillable).append("'");
		}
		if (!(remarks.equals("ALL"))) {
			sb.append(" and ilc.remarks='").append(remarks).append("'");
		}
		
		sb.append(" and ilc.account_id in(select account_desc from biller.blr_accounts ac where ac.account_id= ")				
			.append(accountId)
			.append(")"); */
		  
		
		
		
			

		/*ArrayList<ILCData> ilcDataList = (ArrayList<ILCData>) jdbcTemplate.query(sb.toString(),
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
						ilcModel.setShiftType(rs.getString("shift_type"));
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
	} */

	/*public List<String> getEmployeeList(String billCycle, int towerID) {
		
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
	}*/

	/*public List<String> getWRList(String billCycle, int towerID) {
		
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
	}*/

	/*public List<String> getWeekendList(String billCycle, int towerID) {
		
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
	}*/
	
	
	/*public List<String> getRemarksList(String billCycle, int towerID) {
		
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
	}*/
}
