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

//import com.app.biller.domain.ILCData;
import com.app.biller.domain.SLAData;

@Repository("slaDataDao")
public class SLADataDaoImpl implements SLADataDao {

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
	
	@Value("${GET_ACTIVE_BILLCYCLE}")
	private String getActiveBillCycle;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	@Override
	public void createSLAData(ArrayList<SLAData> SLAModelList, String billCycle, String userId, String uploadDataType) {

		/*try {
			jdbcTemplate.update(deleteIlcData);
		} catch (DataAccessException dae) {
			logger.info("Delete ILCData Exception: " + dae.getMessage());
		}*/

		jdbcTemplate.batchUpdate(insertSLAData, new BatchPreparedStatementSetter() {
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
				
				
				/*ps.setString(1, slaModelList.get(i).getEmpID());
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
				ps.setString(41, billCycle);*/
			}

			@Override
			public int getBatchSize() {
				return SLAModelList.size();
			}
		});

		// jdbcTemplate.update(insertIlcDataSign, new Object[] { billCycle,
		// userId,uploadDataType });
//		System.out.println("created..SLA...");
	}
	
	
	
	
	
	
	@Override
	public ArrayList<SLAData> readSLAData(String billCycle, int towerID) {
		
		Object queryParam[];
		String readSlaQuery;
		if(towerID != 0 ) {
			queryParam = new Object [] {towerID, billCycle };
			readSlaQuery = selectSLAData;
		}else {
			queryParam = new Object [] { billCycle };
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
				ps.setDouble(12, newSLADataList.get(i).getTotHrs());
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
				ps.setString(30, newSLADataList.get(i).getAccountId());
				ps.setString(31, billCycle);
				ps.setString(32, newSLADataList.get(i).getChangeLog());
				ps.setInt(33, newSLADataList.get(i).getActive());
				ps.setString(34, newSLADataList.get(i).getModifiedBy());

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
			String empName, int billable, String remarks) {

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

						return slaModel;
					}
				});

		return slaDataList;
	}

	public List<String> getEmployeeList(String billCycle, int towerID) {
		
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
