package com.ibm.biller.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ibm.biller.model.ILCData;
import com.ibm.biller.model.User;
import com.ibm.biller.services.DataLoadService;
import com.ibm.biller.services.DataValidationService;

@Controller
@RequestMapping("/pmo")
public class PmoController {

	private static final Logger logger = LoggerFactory.getLogger(PmoController.class);

	@Autowired
	DataLoadService dataLoadService;
	
	@Autowired
	DataValidationService dataValidationService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showHome() {
		return "Home";
	}

	@RequestMapping(value="/upload.do", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFiles(MultipartHttpServletRequest request, HttpSession userSession) {
		//String billCycle = (String) request.getAttribute("billCycle");
		//String dataType = (String) request.getAttribute("billCycleType");
		//ToDo: Fetch billCycle and dataType from UI. Setting here until then.
		String billCycle = "082017";
		String dataType = "0";
		String status = dataLoadService.uploadFiles(request);		
		if(status.equalsIgnoreCase("Success")){
			User loggedUser = (User) userSession.getAttribute("userObj");
			return dataLoadService.loadILCData(billCycle, dataType, loggedUser.getUserID());
		} else {
			logger.info("status = "+status);
		}
		return "File Upload Failed";
	}
	
	@RequestMapping(path = "/read.do", method = RequestMethod.GET)
	@ResponseBody
	public String readILCData(Model model) {
		List<ILCData> ilcDataList = dataValidationService.readILCData();
		model.addAttribute("ilcDataList", ilcDataList);
		
		return null;
	}

	@RequestMapping(path = "/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateILCData(@RequestParam("userID") String userId) {
		return dataValidationService.updateILCData(userId);
	}
}
