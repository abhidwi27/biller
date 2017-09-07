package com.app.biller.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.app.biller.model.ILCData;
import com.app.biller.model.SLAData;
import com.app.biller.util.BillerUtil;

@Repository("slaDataDao")
public class SLADataDaoImpl implements SLADataDao {
	
	@Value("${GET_SLA_EMPLOYEE_LIST}")
	private String getSLAEmployee;
	
	@Value("${GET_SLA_WR_LIST}")
	private String getSLAWr;
	
	@Value("${GET_SLA_WEEKEND_LIST}")
	private String getSLAWeekend;
	
	@Value("${READ_SLA_DATA}")
	private String readSLAData;
	
	@Value("${READ_CUSTOM_SLA_DATA}")
	private String readCustomSLAData;
	
	@Value("${INSERT_SLA_DATA}")
	private String insertSLAData;
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public ArrayList<SLAData> readSLAData(String billCycle, String towerID) {
		ArrayList<SLAData> slaDataList = (ArrayList<SLAData>) jdbcTemplate.query(readSLAData, new Object[] {towerID, billCycle}, new RowMapper<SLAData>() {
			@Override
			public SLAData mapRow(ResultSet rs, int rownumber) throws SQLException {
				SLAData slaModel = new SLAData();
				slaModel.setSeqID(rs.getInt("seq_id "));
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
				slaModel.setTotHrs(rs.getInt("total_hours"));
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


				return slaModel;
			}
		});

		return slaDataList;
	}

	@Override
	public void updateSLAData(String billCycle, String towerID, ArrayList<SLAData> records) {
		jdbcTemplate.batchUpdate(insertSLAData, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1,records.get(i).getSeqID());
				ps.setString(2,records.get(i).getWeekEndDate());
				ps.setString(3,records.get(i).getAsm());
				ps.setString(4,records.get(i).getAsd());
				ps.setString(5,records.get(i).getAsmItwr());
				ps.setString(6,records.get(i).getAsdItwr());
				ps.setString(7,records.get(i).getEmpID());
				ps.setString(8,records.get(i).getEmpName());
				ps.setString(9,records.get(i).getActivity());
				ps.setString(10,records.get(i).getWorkItem());
				ps.setString(11,records.get(i).getOnOffShore());
				ps.setInt(12,records.get(i).getTotHrs());
				ps.setString(13,records.get(i).getShiftDetail());
				ps.setString(14,records.get(i).getCategory());
				ps.setString(15,records.get(i).getBillType());
				ps.setString(16,records.get(i).getDm());
				ps.setString(17,records.get(i).getAppArea());
				ps.setString(18,records.get(i).getBusinessArea());
				ps.setString(19,records.get(i).getTower());
				ps.setString(20,records.get(i).getBam());
				ps.setString(21,records.get(i).getRemarks());
				ps.setString(22,records.get(i).getIsBillable());
				ps.setString(23,records.get(i).getWrNo());
				ps.setInt(24,records.get(i).getPlannedHrs());
				ps.setString(25,records.get(i).getComments());
				ps.setString(26,records.get(i).getWrDesc());
				ps.setString(27,records.get(i).getCostCenter());
				ps.setString(28,records.get(i).getFundType());
				ps.setString(29,records.get(i).getVendorClass());
				ps.setString(30,records.get(i).getChangeLog());
				ps.setString(31,records.get(i).getActive());
				ps.setString(32,records.get(i).getModifiedBy());

			}

			@Override
			public int getBatchSize() {
				return records.size();
			}
		});
	}

	@Override
	public String deleteSLAData(String billCycle, int[] seqID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createNewSLARecord(String billCycle, String userID, ArrayList<SLAData> records) {
		jdbcTemplate.batchUpdate(insertSLAData, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				ps.setString(1,records.get(i).getWeekEndDate());
				ps.setString(2,records.get(i).getAsm());
				ps.setString(3,records.get(i).getAsd());
				ps.setString(4,records.get(i).getAsmItwr());
				ps.setString(5,records.get(i).getAsdItwr());
				ps.setString(6,records.get(i).getEmpID());
				ps.setString(7,records.get(i).getEmpName());
				ps.setString(8,records.get(i).getActivity());
				ps.setString(9,records.get(i).getWorkItem());
				ps.setString(10,records.get(i).getOnOffShore());
				ps.setInt(11,records.get(i).getTotHrs());
				ps.setString(12,records.get(i).getShiftDetail());
				ps.setString(13,records.get(i).getCategory());
				ps.setString(14,records.get(i).getBillType());
				ps.setString(15,records.get(i).getDm());
				ps.setString(16,records.get(i).getAppArea());
				ps.setString(17,records.get(i).getBusinessArea());
				ps.setString(18,records.get(i).getTower());
				ps.setString(19,records.get(i).getBam());
				ps.setString(20,records.get(i).getRemarks());
				ps.setString(21,records.get(i).getIsBillable());
				ps.setString(22,records.get(i).getWrNo());
				ps.setInt(23,records.get(i).getPlannedHrs());
				ps.setString(24,records.get(i).getComments());
				ps.setString(25,records.get(i).getWrDesc());
				ps.setString(26,records.get(i).getCostCenter());
				ps.setString(27,records.get(i).getFundType());
				ps.setString(28,records.get(i).getVendorClass());
				ps.setString(29,records.get(i).getChangeLog());
				ps.setString(30,records.get(i).getActive());
				ps.setString(31,records.get(i).getModifiedBy());

			}

			@Override
			public int getBatchSize() {
				return records.size();
			}
		});
	}

	@Override
	public ArrayList<SLAData> readCustomSLAData(String billCycle, String towerID, String weekEndDate, String wrNo,
			String empName) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT * FROM biller.blr_sla_data sla where ilc.tower in(select tower_desc from biller.blr_tower tower where tower.tower_id=")
		  .append(towerID)
		  .append(" and sla.bill_cycle =")
		  .append(billCycle);
		
		if (!(weekEndDate.equals("All"))) {
			 sb.append(" sla.weekend_date=")
			   .append(weekEndDate);
		}
		
		if (!(wrNo.equals("All"))) {
			 sb.append(" sla.wr_no=")
			   .append(wrNo);
		}
		
		if (!(empName.equals("All"))) {
			 sb.append(" ilc.emp_name=")
			   .append(empName);
		}
		
		ArrayList<SLAData> slaDataList = (ArrayList<SLAData>) jdbcTemplate.query(readSLAData, new Object[] {towerID, billCycle}, new RowMapper<SLAData>() {
			@Override
			public SLAData mapRow(ResultSet rs, int rownumber) throws SQLException {
				SLAData slaModel = new SLAData();
				slaModel.setSeqID(rs.getInt("seq_id "));
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
				slaModel.setTotHrs(rs.getInt("total_hours"));
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


				return slaModel;
			}
		});

		return slaDataList;
	}
	
	public List<String> getEmployeeList(String billCycle){
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		
		List<String> ilcEmployeeList =  jdbcTemplate.query(getSLAEmployee, rowMap);		
		return ilcEmployeeList;
	}
	
	public List<String> getWRList(String billCycle){
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		
		List<String> ilcWrList =  jdbcTemplate.query(getSLAWr, rowMap);		
		return ilcWrList;
	}
	
	public List<String> getWeekendList(String billCycle){
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		
		List<String> ilcWeekendList =  jdbcTemplate.query(getSLAWeekend, rowMap);		
		return ilcWeekendList;
	}

}
