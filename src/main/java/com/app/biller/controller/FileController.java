package com.app.biller.controller;

import static com.app.biller.util.BillerHelper.getUserProfile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.activation.FileTypeMap;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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
			@RequestParam("dataType") String uploadDataType,@RequestParam("reportWeekend") String reportWeekend,HttpSession userSession) {
	//public String uploadFiles(MultipartHttpServletRequest request, @RequestParam("billCycle") String billCycle,
		//	HttpSession userSession) {
		//billCycle = "112017";
		String status = fileUploadService.uploadFiles(request);
		if (status.equalsIgnoreCase("Success")) {
			User userProfile = getUserProfile(userSession);
			String userId = userProfile.getUserID();
			if (uploadDataType.equals("0")) {
				return fileUploadService.uploadILCData(billCycle, userId, uploadDataType, reportWeekend);
			}else {
				//String returnValue = fileUploadService.uploadSLAData(billCycle, userId, uploadDataType, reportWeekend);
				String returnValue = fileUploadService.uploadSLAData(billCycle, userId, uploadDataType, reportWeekend);
				return returnValue;
			}
		} else {
			logger.info("status = " + status);
		}
		return "File Upload Failed";
	}

	@RequestMapping(value = "/download.do", method = RequestMethod.GET)
	@ResponseBody
	public void downloadFiles(@RequestParam("billCycle") String billCycle,
			@RequestParam("weekEnd") String weekEnd,
			@RequestParam("fileType") int fileType,HttpSession userSession, HttpServletResponse response) {
		
		String fileName;
		String filePath = "C:\\billerdata\\downloads";
		String month = referenceDataService.getMonthForBillCycle(billCycle);
		
		filePath = filePath + "\\" + month + "-" + billCycle.substring(2, 6);
		if(fileType == 0) {
			fileName = "FFIC_ILC_";
		}else {
			fileName = "FFIC_SLA_";	
		}
		
		fileName = fileName + weekEnd + ".xlsx";
		String fullFilePath = filePath + "\\" + fileName;
		
		Path file = Paths.get(filePath, fileName);
		
			try {   
		    //Files.copy(file, response.getOutputStream());
		    //response.setContentType("application/application/vnd.ms-excel");
		    // add response header
		    //response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
		    //response.addHeader("Content-Disposition", "inline; filename=" + fileName);
		    //response.getOutputStream().flush();
		    //return new FileSystemResource(new File(fullFilePath)); 
		    
		    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");

            File xlfile = new File(fullFilePath);
                        
            response.setHeader("Content-disposition", "attachment; filename=\""+ fileName + "\"");
             BufferedOutputStream bfos = new BufferedOutputStream(response.getOutputStream());
              FileInputStream fis = new FileInputStream(xlfile);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);

                bfos.write(buffer, 0, buffer.length);
                fis.close();
                bfos.flush();
                
			}   
		   catch (IOException e) {
		    System.out.println("Error :- " + e.getMessage());
		    //return null;
		   }
	

	//return "Click Link to Download File.";
	}
}
