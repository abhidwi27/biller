package com.app.biller.controller;

import static com.app.biller.util.BillerHelper.getUserProfile;

import java.util.ArrayList;
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

import com.app.biller.model.ILCData;
import com.app.biller.model.User;
import com.app.biller.services.DataLoadService;
import com.app.biller.services.DataValidationService;

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

	@RequestMapping(value = "/upload.do", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFiles(MultipartHttpServletRequest request, @RequestParam("billCycle") String billCycle, HttpSession userSession) {
		billCycle = "082017";
		String status = dataLoadService.uploadFiles(request);
		if (status.equalsIgnoreCase("Success")) {
			User userProfile = getUserProfile(userSession);
			String userId = userProfile.getUserID();
			return dataLoadService.loadILCData(billCycle, userId);
		} else {
			logger.info("status = " + status);
		}
		return "File Upload Failed";
	}

	@RequestMapping(path = "/read.do", method = RequestMethod.GET)
	@ResponseBody
	public String readILCData(@RequestParam("billCycle") String billCycle, @RequestParam("towerID") String towerID,
			Model model) {
		List<ILCData> ilcDataList = dataValidationService.readILCData(billCycle, towerID);
		model.addAttribute("ilcDataList", ilcDataList);
		return null;
	}

	@RequestMapping(path = "/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateSLAData(@RequestParam("billCycle") String billCycle, @RequestParam("towerID") String towerID, @RequestParam("records") ArrayList records, HttpSession userSession) {
		User userProfile = getUserProfile(userSession);
		String userId = userProfile.getUserID();
		return dataValidationService.updateSLAData(billCycle, towerID, userId, records);
	}
}
