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

	@Value("${DELETE_ILC_DATA}")
	private String deleteIlcData;

	@Value("${INSERT_ILC_DATA}")
	private String insertIlcData;

	@Value("${INSERT_ILC_DATA_APPROVAL}")
	private String insertIlcDataApproval;

	@Value("${SELECT_ILC_DATA}")
	private String selectIlcData;

	@Value("${SELECT_ILC_EMPLOYEE_LIST}")
	private String selectILCEmployeeList;
	
	@Value("${SELECT_ILC_WR_LIST}")
	private String selectILCWrList;
	
	@Value("${SELECT_ILC_WEEKEND_LIST}")
	private String selectILCWeekendList;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void createILCData(ArrayList<ILCData> ilcModelList, String billCycle, String userId) {

		try {
			jdbcTemplate.update(deleteIlcData);
		} catch (DataAccessException dae) {
			logger.info("Delete ILCData Exception: " + dae.getMessage());
		}

		jdbcTemplate.batchUpdate(insertIlcData, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, ilcModelList.get(i).getEmpID());
				ps.setString(2, ilcModelList.get(i).getEmpName());
				ps.setString(3, ilcModelList.get(i).getClaimCode());
				ps.setString(4, ilcModelList.get(i).getActivity());
				ps.setString(5, BillerUtil.getDateStr(ilcModelList.get(i).getWeekEndDate()));
				ps.setInt(6, ilcModelList.get(i).getTotHrs());
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
			}

			@Override
			public int getBatchSize() {
				return ilcModelList.size();
			}
		});

		jdbcTemplate.update(insertIlcDataApproval, new Object[] { billCycle, userId});
	}

	@Override
	public ArrayList<ILCData> readILCData(String billCycle, String towerID) {

		ArrayList<ILCData> ilcDataList = (ArrayList<ILCData>) jdbcTemplate.query(selectIlcData, new Object[] {towerID, billCycle}, new RowMapper<ILCData>() {
			@Override
			public ILCData mapRow(ResultSet rs, int rownumber) throws SQLException {
				ILCData ilcModel = new ILCData();
				ilcModel.setEmpID(rs.getString("emp_id"));
				ilcModel.setEmpName(rs.getString("emp_name"));
				ilcModel.setClaimCode(rs.getString("claim_code"));
				ilcModel.setActivity(rs.getString("activity"));
				ilcModel.setWeekEndDate(rs.getString("weekend_date"));
				ilcModel.setTotHrs(Integer.parseInt(rs.getString("total_hours")));
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

				return ilcModel;
			}
		});

		return ilcDataList;
	}

	@Override
	public ArrayList<ILCData> readCustomILCData(String billCycle, int towerID, String weekEndDate, String wrNo,
			String empName) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT * FROM biller.blr_ilc_data ilc where ilc.tower in(select tower_desc from biller.blr_tower tower where tower.tower_id=")
		  .append(towerID)
		  .append(" and ilc.bill_cycle =")
		  .append(billCycle);
		
		if (!(weekEndDate.equals("All"))) {
			 sb.append(" ilc.weekend_date=")
			   .append(weekEndDate);
		}
		
		if (!(wrNo.equals("All"))) {
			 sb.append(" ilc.wr_no=")
			   .append(wrNo);
		}
		
		if (!(empName.equals("All"))) {
			 sb.append(" ilc.emp_name=")
			   .append(empName);
		}
		
		ArrayList<ILCData> ilcDataList = (ArrayList<ILCData>) jdbcTemplate.query(sb.toString(),  new RowMapper<ILCData>() {
			@Override
			public ILCData mapRow(ResultSet rs, int rownumber) throws SQLException {
				ILCData ilcModel = new ILCData();
				ilcModel.setEmpID(rs.getString("emp_id"));
				ilcModel.setEmpName(rs.getString("emp_name"));
				ilcModel.setClaimCode(rs.getString("claim_code"));
				ilcModel.setActivity(rs.getString("activity"));
				ilcModel.setWeekEndDate(rs.getString("weekend_date"));
				ilcModel.setTotHrs(Integer.parseInt(rs.getString("total_hours")));
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

				return ilcModel;
			}
		});

		return ilcDataList;
	}
	
	public List<String> getEmployeeList(String billCycle){
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		
		List<String> ilcEmployeeList =  jdbcTemplate.query(selectILCEmployeeList, rowMap);		
		return ilcEmployeeList;
	}
	
	public List<String> getWRList(String billCycle){
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		
		List<String> ilcWrList =  jdbcTemplate.query(selectILCWrList, rowMap);		
		return ilcWrList;
	}
	
	public List<String> getWeekendList(String billCycle){
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		
		List<String> ilcWeekendList =  jdbcTemplate.query(selectILCWeekendList, rowMap);		
		return ilcWeekendList;
	}
}
