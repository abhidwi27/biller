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

import com.app.biller.domain.SLAData;

@Repository("slaDataDao")
public class SLADataDaoImpl implements SLADataDao {

	@Value("${SELECT_SLA_EMPLOYEE_LIST}")
	private String selectSLAEmployeeList;

	@Value("${SELECT_SLA_WR_LIST}")
	private String selectSLAWrList;

	@Value("${SELECT_SLA_WEEKEND_LIST}")
	private String selectSLAWeekendList;

	@Value("${SELECT_SLA_DATA}")
	private String selectSLAData;

	@Value("${INSERT_SLA_DATA}")
	private String insertSLAData;

	@Value("${UPDATE_SLA_DATA}")
	private String updateSLAData;
	
	@Value("${DELETE_SLA_DATA}")
	private String deleteSLAData;
	
	@Value("${GET_MAX_SEQID}")
	private String getMaxSeqID;
	
	@Value("${GET_ACTIVE_BILLCYCLE}")
	private String getActiveBillCycle;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public ArrayList<SLAData> readSLAData(String billCycle, int towerID) {
		ArrayList<SLAData> slaDataList = (ArrayList<SLAData>) jdbcTemplate.query(selectSLAData,
				new Object[] { towerID, billCycle }, new RowMapper<SLAData>() {
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
				ps.setInt(11, updateSLADataList.get(i).getTotHrs());
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
				ps.setString(29, updateSLADataList.get(i).getChangeLog());				
				ps.setInt(30, updateSLADataList.get(i).getActive());
				ps.setString(31, updateSLADataList.get(i).getModifiedBy());
				ps.setString(32, billCycle);
				ps.setInt(33, updateSLADataList.get(i).getSeqID());

			}

			@Override
			public int getBatchSize() {
				return updateSLADataList.size();
			}
		});
	}

	@Override
	public void deleteSLAData(String billCycle, List<Integer> seqIDList) {
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
	public void createNewSLARecord(String billCycle,  ArrayList<SLAData> newSLADataList) {
		
		jdbcTemplate.batchUpdate(insertSLAData, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				int seqID = GetSeqID(billCycle);
				
				ps.setInt(1, seqID);
				ps.setString(2, newSLADataList.get(i).getWeekEndDate());
				ps.setString(3, newSLADataList.get(i).getAsm());
				ps.setString(4, newSLADataList.get(i).getAsd());
				ps.setString(5, newSLADataList.get(i).getAsmItwr());
				ps.setString(6, newSLADataList.get(i).getAsdItwr());
				ps.setString(7, newSLADataList.get(i).getEmpID());
				ps.setString(8, newSLADataList.get(i).getEmpName());
				ps.setString(9, newSLADataList.get(i).getActivity());
				ps.setString(10, newSLADataList.get(i).getWorkItem());
				ps.setString(11, newSLADataList.get(i).getOnOffShore());
				ps.setInt(12, newSLADataList.get(i).getTotHrs());
				ps.setString(13, newSLADataList.get(i).getShiftDetail());
				ps.setString(14, newSLADataList.get(i).getCategory());
				ps.setString(15, newSLADataList.get(i).getBillType());
				ps.setString(16, newSLADataList.get(i).getDm());
				ps.setString(17, newSLADataList.get(i).getAppArea());
				ps.setString(18, newSLADataList.get(i).getBusinessArea());
				ps.setString(19, newSLADataList.get(i).getTower());
				ps.setString(20, newSLADataList.get(i).getBam());
				ps.setString(21, newSLADataList.get(i).getRemarks());
				ps.setString(22, newSLADataList.get(i).getIsBillable());
				ps.setString(23, newSLADataList.get(i).getWrNo());
				ps.setInt(24, newSLADataList.get(i).getPlannedHrs());
				ps.setString(25, newSLADataList.get(i).getComments());
				ps.setString(26, newSLADataList.get(i).getWrDesc());
				ps.setString(27, newSLADataList.get(i).getCostCenter());
				ps.setString(28, newSLADataList.get(i).getFundType());
				ps.setString(29, newSLADataList.get(i).getVendorClass());
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
			String empName) {

		StringBuilder sb = new StringBuilder();

		sb.append(
				"SELECT * FROM biller.blr_sla_data sla where sla.tower in(select tower_desc from biller.blr_tower tower where sla.active = 1 AND tower.tower_id=")
				.append(towerID).append(" )and sla.bill_cycle ='").append(billCycle).append("'");

		if (!(weekEndDate.equals("ALL"))) {
			sb.append(" and sla.weekend_date='").append(weekEndDate).append("'");
		}

		if (!(wrNo.equals("ALL"))) {
			sb.append(" and sla.wr_no='").append(wrNo).append("'");
		}

		if (!(empName.equals("ALL"))) {
			sb.append(" and sla.emp_name='").append(empName).append("'");
		}

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

	public List<String> getEmployeeList(String billCycle, int towerID) {
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};

		List<String> slaEmployeeList = jdbcTemplate.query(selectSLAEmployeeList, new Object[] { billCycle, towerID }, rowMap);
		return slaEmployeeList;
	}

	public List<String> getWRList(String billCycle, int towerID) {
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};

		List<String> slaWrList = jdbcTemplate.query(selectSLAWrList, new Object[] { billCycle, towerID }, rowMap);
		return slaWrList;
	}

	public List<String> getWeekendList(String billCycle, int towerID) {
		RowMapper<String> rowMap = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};

		List<String> slaWeekendList = jdbcTemplate.query(selectSLAWeekendList, new Object[] { billCycle, towerID }, rowMap);
		return slaWeekendList;
	}
	
	public String getActiveBillCycle() {
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
			return null;
		}
		
	}
}
