package com.app.biller.services;

import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FileUploadService {

	String uploadFiles(MultipartHttpServletRequest request) throws Exception;

	String uploadILCData(String billCycle, String userId, String uploadDataType, String reportWeekend) throws DataAccessException;

	String uploadSLAData(String billCycle, String userId,String uploadDataType,String reportWeekend, boolean override) throws Exception;
	
	}
