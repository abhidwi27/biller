package com.app.biller.controller;

import static com.app.biller.util.BillerHelper.getUserProfile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.app.biller.services.FileDownloadService;
import com.app.biller.services.FileUploadService;
import com.app.biller.services.ReferenceDataService;

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

	@RequestMapping(value = "/upload.do", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFiles(MultipartHttpServletRequest request, @RequestParam("billCycle") String billCycle,
			@RequestParam("dataType") String uploadDataType, @RequestParam("reportWeekend") String reportWeekend,
			HttpSession userSession) {
		String status = "";
		status = fileUploadService.uploadFiles(request);
		if (status.equalsIgnoreCase("Success")) {
			User userProfile = getUserProfile(userSession);
			String userId = userProfile.getUserID();
			if (uploadDataType.equals("0")) {
				return fileUploadService.uploadILCData(billCycle, userId, uploadDataType, reportWeekend);
			} else {
				return fileUploadService.uploadSLAData(billCycle, userId, uploadDataType, reportWeekend);
			}
		} else {
			logger.error("File Upload failed with status: " + status);
		}
		return "File Upload Failed";
	}

	@RequestMapping(value = "/download.do", method = RequestMethod.GET)
	@ResponseBody
	public void downloadFiles(@RequestParam("month") String month, @RequestParam("year") String year, @RequestParam("weekEnd") String weekEnd,
			@RequestParam("filename") String fileName, HttpSession userSession, HttpServletResponse response) {
		
		String filePath = "C:\\billerdata\\downloads\\" + month + "-" + year + "\\" + weekEnd;
		
		String fullFilePath = filePath + "\\" + fileName;
		
		try {
		
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
			File xlfile = new File(fullFilePath);
			response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
			BufferedOutputStream bfos = new BufferedOutputStream(response.getOutputStream());
			FileInputStream fis = new FileInputStream(xlfile);
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			bfos.write(buffer, 0, buffer.length);
			fis.close();
			bfos.flush();
		} catch (IOException e) {
			System.out.println("Error :- " + e.getMessage());
			
		}
		
	}
	
	@RequestMapping(value = "/filename.do", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getFileName( @RequestParam("month") String month,
			@RequestParam("year") String year, @RequestParam("weekEnd") String weekEnd, @RequestParam("fileType") int fileType,
			HttpSession userSession) {
		
		List<String> fileNameList  = new ArrayList<String>();
		
		String filePath = "C:\\billerdata\\downloads\\" + month + "-" + year + "\\" + weekEnd;
		
		File[] files = new File(filePath).listFiles(new FilenameFilter() { 
			
			
			
			@Override 
			public boolean accept(File dir, String name) { 
				String startsWith;
				if(fileType == 0) {
					startsWith = "ILC";
				}else {
					startsWith = "SLA";
				}
				
				return name.endsWith(".xlsx") && name.startsWith(startsWith); 
			} 
		});
		if(files != null && files.length != 0) {
			for(File file : files) {
				fileNameList.add(file.getName());
			}
		}
		
		return fileNameList;
	}
}
