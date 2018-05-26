package com.app.biller.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.app.biller.controller.AdminController;
import com.app.biller.domain.Account;
import com.app.biller.domain.Header;
import com.app.biller.domain.Month;
import com.app.biller.domain.Tower;
import com.app.biller.ui.ItwrReference;
import com.app.biller.ui.WIASMReference;

@Repository("referenceDataDao")
public class ReferenceDataDaoImpl implements ReferenceDataDao {

	private static final Logger logger = LoggerFactory.getLogger(ReferenceDataDaoImpl.class);
	
	@Value("${SELECT_ILC_TABLE_HEADER}")
	String selectIlcTableHeader;

	@Value("${SELECT_SLA_TABLE_HEADER}")
	String selectSlaTableHeader;

	@Value("${SELECT_TOWER_LIST}")
	String selectTowerList;

	@Value("${SELECT_MONTH_LIST}")
	String selectMonthList;

	@Value("${SELECT_YEAR_LIST}")
	String selectYearList;
	
	@Value("${GET_MONTH_FOR_BILL_CYCLE}")
	String getMonthForBillCycle;
	
	@Value("${GET_ACCOUNT_LIST}")
	String getAccountList;
	
	@Value("${GET_ITWR_REFERENCE_DATA}")
	String getItwrReferenceData;
	
	@Value("${GET_COLUMN_NAME_BY_INDEX}")
	String getColumnNameByIndex;
	
	@Value("${GET_HEADER_FOR_BULK_UPDATE}")
	String getHeaderForBulkUpdate;
	

	@Value("${GET_WIASM_REFERENCE_DATA}")
	String getwiasmReferenceData;
	

	@Value("${GET_HEADER_FOR_CUSTOMISE}")
	String getHeaderForCustomiseUpdate;


	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<String> getILCTableHeader() {
		RowMapper<String> rowMap = getRowMapper();
		List<String> header = jdbcTemplate.query(selectIlcTableHeader, rowMap);
		return header;
	}

	@Override
	public List<String> getSLATableHeader() {
		RowMapper<String> rowMap = getRowMapper();
		List<String> header = jdbcTemplate.query(selectSlaTableHeader, rowMap);
		return header;
	}

	public List<Tower> getTowerList() {
		RowMapper<Tower> towerMap = new RowMapper<Tower>() {
			@Override
			public Tower mapRow(ResultSet rs, int rowNum) throws SQLException {
				Tower tower = new Tower();
				tower.setTowerID(rs.getInt("tower_id"));
				tower.setTowerName(rs.getString("tower_desc"));
				return tower;
			}
		};

		List<Tower> towerList = (List<Tower>) jdbcTemplate.query(selectTowerList, towerMap);
		return towerList;
	}

	public ArrayList<Month> getMonthList() {
		RowMapper<Month> monthMap = new RowMapper<Month>() {
			@Override
			public Month mapRow(ResultSet rs, int rowNum) throws SQLException {
				Month month = new Month();
				month.setMonthID(rs.getString("month_id"));
				month.setMonthName(rs.getString("month_desc"));
				return month;
			}
		};

		ArrayList<Month> monthList = (ArrayList<Month>) jdbcTemplate.query(selectMonthList, monthMap);
		return monthList;
	}

	public List<String> getYearList() {
		RowMapper<String> rowMap = getRowMapper();
		List<String> year = jdbcTemplate.query(selectYearList, rowMap);
		return year;
	}

	private RowMapper<String> getRowMapper() {
		RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		return rowMapper;
	}
	
	public String getMonthForBillCycle(String billCycle) {
		String monthID = billCycle.substring(0, 2);		
		String month = jdbcTemplate.queryForObject(getMonthForBillCycle, new Object[] {monthID}, String.class );
		return month;
	}
	
	public List<Account> getAccountList() {
		
		List<Account> accountList = new ArrayList<Account>();
		RowMapper<Account> rowMapper = new RowMapper<Account>() {
			@Override
			public Account mapRow(ResultSet rs, int rownumber) throws SQLException {
				Account account = new Account();
				
				account.setAccountId(rs.getInt("account_id"));
				account.setAccountDesc(rs.getString("account_desc"));
				return account;
			}
		};
		accountList =  jdbcTemplate.query(getAccountList, rowMapper);
		return accountList;
	}
	
	public List<ItwrReference> getItwrReferenceData(String wrNo){
		List<ItwrReference> itwrReferenceList = new ArrayList<ItwrReference>();
		RowMapper<ItwrReference> rowMapper = new RowMapper<ItwrReference>() {
			@Override
			public ItwrReference mapRow(ResultSet rs, int rownumber) throws SQLException {
				ItwrReference itwrRef = new ItwrReference();
				
				itwrRef.setReqNo(rs.getString("req_no"));
				itwrRef.setReqDesc(rs.getString("req_title"));
				itwrRef.setFundingType(rs.getString("fund_type"));
				itwrRef.setCostCenter(rs.getString("cost_center"));
				itwrRef.setVendor(rs.getString("vendor_class"));
				itwrRef.setAsm(rs.getString("asm"));
				itwrRef.setAsd(rs.getString("asd"));
				
				return itwrRef;
			}
		};
		itwrReferenceList =  jdbcTemplate.query(getItwrReferenceData, new Object[] {wrNo},rowMapper);
		return itwrReferenceList;
	}
	
	
	public List<WIASMReference> getwiasmReferenceData(String wrkItem){
		List<WIASMReference> wiasmReferenceList = new ArrayList<WIASMReference>();
		RowMapper<WIASMReference> rowMapper = new RowMapper<WIASMReference>() {
			@Override
			public WIASMReference mapRow(ResultSet rs, int rownumber) throws SQLException {
				WIASMReference wiasmRef = new WIASMReference();
				
				wiasmRef.setCategory(rs.getString("category"));
				wiasmRef.setOn_off_shore(rs.getString("on_off_shore"));
				wiasmRef.setBilling_type(rs.getString("bill_type"));
				wiasmRef.setApplication(rs.getString("application"));
				wiasmRef.setBuss_area(rs.getString("bus_area"));
				wiasmRef.setBam(rs.getString("bam"));
				wiasmRef.setDm(rs.getString("dm"));
				
				return wiasmRef;
			}
		};
		wiasmReferenceList =  jdbcTemplate.query(getwiasmReferenceData, new Object[] {wrkItem},rowMapper);
		return wiasmReferenceList;
	}
	
	public String getColumnNameById(int headerId) {				
		String column = jdbcTemplate.queryForObject(getColumnNameByIndex, new Object[] {headerId}, String.class );
		return column;
	}
	
	public List<Header> getHeaderForBulkUpdate(){
		
		RowMapper<Header> rowMapper = new RowMapper<Header>() {
			@Override
			public Header mapRow(ResultSet rs, int rownumber) throws SQLException {
				Header header= new Header();				
				header.setHeaderId(rs.getInt("header_id"));
				header.setHeaderDesc(rs.getString("header_Desc"));
				return header;
			}			
		};
		List<Header> bulkUpdateHeaderList =  jdbcTemplate.query(getHeaderForBulkUpdate,rowMapper);
		return bulkUpdateHeaderList;
	}
	
	public List<String> getBulkUpdateData(String billCycle, String column){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT DISTINCT sla.")
		  .append(column).append(" FROM biller.blr_sla_data sla where sla.bill_cycle ='")
		  .append(billCycle)
		  .append("' order by 1");
		
		RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getString(1);
			}
		};
		
		List<String> bulkUpdateDataList =  jdbcTemplate.query(sb.toString(),rowMapper);
		return bulkUpdateDataList;
	}
	
	
	public List<Header> getHeaderForCustomise(){
		
		RowMapper<Header> rowMapper = new RowMapper<Header>() {
			@Override
			public Header mapRow(ResultSet rs, int rownumber) throws SQLException {
				Header header= new Header();				
				header.setHeaderId(rs.getInt("header_id"));
				header.setHeaderDesc(rs.getString("header_Desc"));
				return header;
			}			
		};
		List<Header> customiseUpdateHeaderList =  jdbcTemplate.query(getHeaderForCustomiseUpdate,rowMapper);
		return customiseUpdateHeaderList;
	}
	
	
}
