package com.app.biller.services;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FileUploadService {

	String uploadFiles(MultipartHttpServletRequest request);

	String uploadILCData(String billCycle, String userId);

	String uploadSLAData(String billCycle, String userId);
}
