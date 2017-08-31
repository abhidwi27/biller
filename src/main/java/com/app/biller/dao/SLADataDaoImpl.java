package com.app.biller.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("slaDataDao")
public class SLADataDaoImpl implements SLADataDao {

	@Override
	public List<?> readSLAData(String billCycle, String towerID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateSLAData(String billCycle, String towerID, ArrayList<?> records) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteSLAData(String billCycle, int[] seqID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createNewSLARecord(String billCycle, String userID, ArrayList<?> records) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> readCustomSLAData(String billCycle, String towerID, String[] weekEndDate, String[] wrNo,
			String[] empID, String[] modifiedBy) {
		// TODO Auto-generated method stub
		return null;
	}

}
