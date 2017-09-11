package com.app.biller.controller;

import static com.app.biller.util.BillerHelper.getUserProfile;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.app.biller.domain.User;
import com.app.biller.services.FileDownloadService;
import com.app.biller.services.FileUploadService;

@Controller
@RequestMapping("/file")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	FileDownloadService fileDownloadService;

	@RequestMapping(value = "/upload.do", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFiles(MultipartHttpServletRequest request, @RequestParam("billCycle") String billCycle,
			HttpSession userSession) {
		billCycle = "082017";
		String status = fileUploadService.uploadFiles(request);
		if (status.equalsIgnoreCase("Success")) {
			User userProfile = getUserProfile(userSession);
			String userId = userProfile.getUserID();
			return fileUploadService.uploadILCData(billCycle, userId);
		} else {
			logger.info("status = " + status);
		}
		return "File Upload Failed";
	}

	@RequestMapping(value = "/download.do", method = RequestMethod.GET)
	@ResponseBody
	public String downloadFiles(@RequestParam("billCycle") String billCycle,
			@RequestParam("weekEnding") String weekEnding, HttpSession userSession) {

		return "Click Link to Download File.";
	}
}
