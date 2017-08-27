package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;

import com.app.biller.model.ILCData;

public interface DataValidationService {
	public List<ILCData> readILCData();
	
	public String updateILCData(String userId);
	
	public String signoffILCData(String userId);
}