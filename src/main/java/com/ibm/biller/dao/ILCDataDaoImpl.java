package com.ibm.biller.dao;

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

import com.ibm.biller.model.ILCData;
import com.ibm.biller.util.BillerUtil;

@Repository("ilcDataDao")
public class ILCDataDaoImpl implements ILCDataDao {

	private static final Logger logger = LoggerFactory.getLogger(ILCDataDaoImpl.class);

	@Value("${PMO_DELETE_ILC_DATA}")
	private String deleteIlcData;

	@Value("${PMO_INSERT_ILC_DATA}")
	private String insertIlcData;

	@Value("${PMO_INSERT_ILC_DATA_SIGN}")
	private String insertIlcDataSign;

	@Value("${MANAGE_READ_ILC_DATA}")
	private String readIlcData;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public ArrayList<ILCData> readILCData() {
		
		logger.info("read ilc data query is: "+readIlcData);

		ArrayList<ILCData> ilcDataList = (ArrayList<ILCData>) jdbcTemplate.query(readIlcData, new RowMapper<ILCData>() {
			@Override
			public ILCData mapRow(ResultSet rs, int rownumber) throws SQLException {
				ILCData ilcModel = new ILCData();
				ilcModel.setSeqID(Integer.parseInt(rs.getString("SeqID")));
				ilcModel.setEmpID(rs.getString("EmpID"));
				ilcModel.setEmpName(rs.getString("EmpName"));
				ilcModel.setClaimCode(rs.getString("ClaimCode"));
				ilcModel.setActivity(rs.getString("Activity"));
				ilcModel.setWeekEndDate(rs.getString("WeekEndDate"));
				ilcModel.setTotHrs(Integer.parseInt(rs.getString("TotHrs")));
				ilcModel.setShiftType(rs.getString("ShiftType"));
				ilcModel.setUsInd(rs.getString("UsInd"));
				ilcModel.setOnOffShore(rs.getString("OnOffshore"));
				ilcModel.setBillingType(rs.getString("BillingType"));
				ilcModel.setCategory(rs.getString("Category"));
				ilcModel.setBam(rs.getString("Bam"));
				ilcModel.setAppArea(rs.getString("AppArea"));
				ilcModel.setBusinessArea(rs.getString("BusinessArea"));
				ilcModel.setMonth(rs.getString("Month"));
				ilcModel.setQuarter(rs.getString("Quarter"));
				ilcModel.setDm(rs.getString("Dm"));
				ilcModel.setAsm(rs.getString("Asm"));
				ilcModel.setAsd(rs.getString("Asd"));
				ilcModel.setWrNo(rs.getString("WrNo"));
				ilcModel.setIsTicket(rs.getString("IsTicket"));
				ilcModel.setStaffType(rs.getString("StaffType"));
				ilcModel.setIsCTC(rs.getString("IsCTC"));
				ilcModel.setIsRTC(rs.getString("IsRTC"));
				ilcModel.setPlannedHrs(Integer.parseInt(rs.getString("PlannedHrs")));
				ilcModel.setIsBillable(rs.getString("IsBillable"));
				ilcModel.setRemarks(rs.getString("Remarks"));
				ilcModel.setCtcOrRtc(rs.getString("CtcOrRtc"));
				ilcModel.setWorkType(rs.getString("WorkType"));
				ilcModel.setWrDesc(rs.getString("WrDesc"));
				ilcModel.setCostCenter(rs.getString("CostCenter"));
				ilcModel.setCategory2(rs.getString("Category2"));
				ilcModel.setOnOffLanded(rs.getString("OnOffLanded"));
				ilcModel.setTower(rs.getString("Tower"));
				ilcModel.setAsmItwr(rs.getString("AsmItwr"));
				ilcModel.setAsdItwr(rs.getString("AsdItwr"));
				ilcModel.setItwrActual(Integer.parseInt(rs.getString("ItwrActual")));
				ilcModel.setGroupType(rs.getString("GroupType"));
				ilcModel.setVendorClass(rs.getString("VendorClass"));
				ilcModel.setWrIncDef(rs.getString("WrIncDef"));
				ilcModel.setBillCycle(rs.getString("billCycle"));

				return ilcModel;
			}
		});
		
		logger.info("ilc data list size is: "+ilcDataList.size());
		return ilcDataList;
	}

	@Override
	public void insertILCData(ArrayList<ILCData> ilcModelList, String billCycle, String dataType, String userId) {

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

		jdbcTemplate.update(insertIlcDataSign, new Object[] { billCycle, userId, dataType });
	}
}
