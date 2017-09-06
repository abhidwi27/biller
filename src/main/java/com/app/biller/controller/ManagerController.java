package com.app.biller.controller;

import static com.app.biller.util.BillerHelper.getUserProfile;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.biller.model.ILCData;
import com.app.biller.model.SLAData;
import com.app.biller.model.TableData;
import com.app.biller.model.User;
import com.app.biller.services.DataApprovalService;
import com.app.biller.services.DataValidationService;
import com.app.biller.services.EmailService;
import com.app.biller.services.TableHeaderService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/manage")
public class ManagerController {

	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

	@Autowired
	DataValidationService dataValidationService;

	@Autowired
	DataApprovalService dataApprovalService;
	
	@Autowired
	TableHeaderService tableHeaderService;

	@Autowired
	EmailService emailService;
	
	@Autowired
	TableData tableData;

	@RequestMapping(path = "/read.do", method = RequestMethod.GET)
	public @ResponseBody String readData(
			@RequestParam("dataType") int dataType,
			@RequestParam("billCycle") String billCycle,
			@RequestParam("towerID") String towerID) {
		
		List<?> dataList;
		
		if(dataType == 0) {
			dataList = (ArrayList<ILCData>) dataValidationService.readILCData(billCycle, towerID);
		}else {
			 dataList = (ArrayList<SLAData>) dataValidationService.readSLAData(billCycle, towerID);
		}
		
		tableData.setHeader(tableHeaderService.getTableHeader(dataType));
		tableData.setTableBody(dataList);
		
		Gson gson = new Gson();
		String tableDataStr = gson.toJson(tableData);
		
		return tableDataStr;

		
	}

	@RequestMapping(path = "/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateSLAData(@RequestParam("billCycle") String billCycle, @RequestParam("towerID") String towerID,
			@RequestParam("records") ArrayList<?> records, HttpSession userSession) {

		User userProfile = getUserProfile(userSession);
		String userId = userProfile.getUserID();
		return dataValidationService.updateSLAData(userId, billCycle, towerID, records);
	}

	@RequestMapping(path = "/signoff.do", method = RequestMethod.GET)
	@ResponseBody
	public String approveSLAData(@RequestParam("billCycle") String billCycle, HttpServletRequest request) {
		boolean signOff = false;
		String userId = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			User userProfile = getUserProfile(session);
			userId = userProfile.getUserID();
			int roleId = userProfile.getRoleID();
			signOff = dataApprovalService.setUserApproval(billCycle, userId, roleId);
		}
		if (signOff && userId != null) {
			emailService.sendEmail(userId);
			return "approved";
		}
		return "rejected";
	}
}
