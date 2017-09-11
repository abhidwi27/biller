package com.app.biller.dao;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.domain.Month;
import com.app.biller.domain.Tower;

public interface ReferenceDataDao {

	List<String> getILCTableHeader();

	List<String> getSLATableHeader();

	List<Tower> getTowerList();

	ArrayList<Month> getMonthList();

	List<String> getYearList();
}
