package com.app.biller.services;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FileUploadService {

	String uploadFiles(MultipartHttpServletRequest request) throws Exception;

	String uploadILCData(String billCycle, String userId, String uploadDataType, String reportWeekend) throws Exception;

	String uploadSLAData(String billCycle, String userId,String uploadDataType,String reportWeekend) throws Exception;
	
	}
