package com.app.biller.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import com.app.biller.services.EmailService;
import com.app.biller.services.FileDownloadService;
import com.app.biller.services.FileUploadService;
import com.app.biller.services.ReferenceDataService;
import com.app.biller.util.BillerHelper;

import org.apache.commons.lang3.StringUtils;

@Controller
@RequestMapping("/file")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	FileDownloadService fileDownloadService;

	@Autowired
	ReferenceDataService referenceDataService;

	@Autowired
	EmailService emailService;
	
	@Autowired
	BillerHelper billerHelper;
	

	@RequestMapping(value = "/upload.do", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFiles(MultipartHttpServletRequest request, @RequestParam("billCycle") String billCycle,
			@RequestParam("dataType") String uploadDataType, @RequestParam("reportWeekend") String reportWeekend, @RequestParam("override") boolean override,
			HttpSession userSession) throws Exception {
		String uploadStatus = "File Upload Failed";
		logger.info("File uploading process started for: " + " billCycle " + billCycle + " datatype " + uploadDataType + " reportWeekend " + reportWeekend);
		//String status = "Success";
		String billMonth = referenceDataService.getMonthForBillCycle(billCycle);
		String billYear = billCycle.substring(2, 6);
		try {
			String status = fileUploadService.uploadFiles(request);
			if (status.equalsIgnoreCase("Success")) {
				User userProfile = billerHelper.getUserProfile(userSession);
				String userId = userProfile.getUserID();
				if (uploadDataType.equals("0")) {
					uploadStatus = fileUploadService.uploadILCData(billCycle, userId, uploadDataType, reportWeekend);
				} else {
					uploadStatus = fileUploadService.uploadSLAData(billCycle, userId, uploadDataType, reportWeekend, override);
				}
				String dataType = uploadDataType.equals("0") ? "ILC" : "SLA";
				if(!uploadStatus.equals("false")) {
					emailService.sendFileUploadEmail(dataType, billMonth, billYear, reportWeekend);
				}
				return uploadStatus;
			} else {
				logger.error("File Upload Failed with Status: " + status);
			}
		}catch (Exception ex) {
			logger.error("Exception occured while executing uploadFiles method", ex);
			throw new Exception("Generic Exception", ex);
		}
		return uploadStatus;
	}

	@RequestMapping(value = "/download.do", method = RequestMethod.GET)
	@ResponseBody
	public void downloadFiles(@RequestParam("month") String month, @RequestParam("year") String year,
			@RequestParam("weekEnd") String weekEnd, @RequestParam("filename") String fileName, HttpSession userSession,
			HttpServletResponse response) throws Exception {
		// String filePath = "C:\\billerdata\\downloads\\" + month + "-" + year + "\\" + weekEnd;
		String filePath = StringUtils.join("C:\\billerdata\\downloads\\", month, "-", year, "\\", weekEnd);
		String fullFilePath = filePath + "\\" + fileName;
		try {
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
			File xlfile = new File(fullFilePath);
			response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
			FileInputStream fileInputStream = new FileInputStream(xlfile);
			byte[] buffer = new byte[fileInputStream.available()];
			fileInputStream.read(buffer);
			bufferedOutputStream.write(buffer, 0, buffer.length);
			fileInputStream.close();
			bufferedOutputStream.flush();
		}  catch (Exception ex) {
			logger.error("Exception occured while executing downloadFiles method", ex);
			throw new Exception("Generic Exception", ex);
		}
	}

	@RequestMapping(value = "/filename.do", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getFileName(@RequestParam("month") String month, @RequestParam("year") String year,
			@RequestParam("weekEnd") String weekEnd, @RequestParam("fileType") int fileType, HttpSession userSession) throws Exception {
		List<String> fileNameList = new ArrayList<String>();
		try {
		// String filePath = "C:\\billerdata\\downloads\\" + month + "-" + year + "\\" + weekEnd;
			String filePath = StringUtils.join("C:\\billerdata\\downloads\\", month, "-", year, "\\", weekEnd);
			File[] files = new File(filePath).listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					String startsWith = (fileType == 0) ? "ILC" : "SLA";
					return name.endsWith(".xlsx") && name.startsWith(startsWith);
				}
			});
			if (files != null && files.length != 0) {
				for (File file : files) {
					fileNameList.add(file.getName());
				}
			}
		}catch (Exception ex) {
			logger.error("Exception occured while executing fileName method", ex);
			throw new Exception("Generic Exception", ex);
		}
		return fileNameList;
	}
}
