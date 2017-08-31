package com.app.biller.services;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface DataLoadService {

	String uploadFiles(MultipartHttpServletRequest request);

	String loadILCData(String billCycle, String userId);

	String loadSLAData(String billCycle, String userId);
}
