package com.ibm.biller.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;

import com.ibm.biller.model.ILCData;

public interface DataValidationService {
	public ArrayList<ILCData> readILCData();
	
	public String updateILCData(String userId);
	
	public String signoffILCData(String userId);
}