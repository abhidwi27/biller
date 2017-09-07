package com.app.biller.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.biller.dao.TableHeaderDaoImpl;

@Service
public class TableHeaderServiceImpl implements TableHeaderService {
	
	@Autowired
	TableHeaderDaoImpl tableHeaderDao;
	
	public List<String> getTableHeader(int dataType){
		
		if(dataType == 0) {
			return tableHeaderDao.getILCTableHeader();
			
		}else {
			return tableHeaderDao.getSLATableHeader();
		}
		
		
	}

}
