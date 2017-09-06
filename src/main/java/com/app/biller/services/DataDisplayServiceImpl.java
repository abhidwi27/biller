package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.app.biller.dao.ILCDataDaoImpl;
import com.app.biller.dao.MonthDaoImpl;
import com.app.biller.dao.SLADataDaoImpl;
import com.app.biller.dao.TowerDaoImpl;
import com.app.biller.dao.YearDaoImpl;
import com.app.biller.model.Month;
import com.app.biller.model.Tower;

public class DataDisplayServiceImpl {

	@Autowired
	MonthDaoImpl monthDao;
	
	@Autowired
	YearDaoImpl yearDao;
	
	@Autowired
	ILCDataDaoImpl ilcDataDao;
	
	@Autowired
	SLADataDaoImpl slaDataDao;
	
	@Autowired
	TowerDaoImpl towerDao;
	
	
	public List<String> getEmployeeList(String billCycle, int dataType){
		
		if(dataType == 0) {
			return ilcDataDao.getEmployeeList(billCycle);
		}else {
			return slaDataDao.getEmployeeList(billCycle);
		}
	}	
	
	public List<String> getWRList(String billCycle, int dataType){
		if(dataType == 0) {
			return ilcDataDao.getWRList(billCycle);
		}else {
			return slaDataDao.getWRList(billCycle);
		}
		
	}

	public List<Tower> getTowerList(String billCycle){
		return towerDao.getTowerList();
	}
	
	public List<String> getWeekendList(String billCycle, int dataType){
		if(dataType == 0) {
			return ilcDataDao.getWeekendList(billCycle);
		}else {
			return slaDataDao.getWeekendList(billCycle);
		}
	}
	
	public ArrayList<Month> getMonth(){
		return monthDao.getMonthList();
		
	}
	
	public List<String> getYear(){
		return yearDao.getYearList();
		
	}
	
	
}
