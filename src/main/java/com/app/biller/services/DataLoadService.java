package com.ibm.biller.services;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface DataLoadService {
	public String uploadFiles(MultipartHttpServletRequest request);
	
	public String loadILCData(String billCycle, String billCycleType, String userId);
}
