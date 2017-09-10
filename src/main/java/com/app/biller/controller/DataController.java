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

import com.app.biller.domain.ILCData;
import com.app.biller.domain.SLAData;
import com.app.biller.domain.User;
import com.app.biller.services.DataApprovalService;
import com.app.biller.services.DataValidationService;
import com.app.biller.services.EmailService;
import com.app.biller.services.ReferenceDataService;
import com.app.biller.ui.DataTableModel;
import com.google.gson.Gson;

@Controller
@RequestMapping("/data")
public class DataController {

	private static final Logger logger = LoggerFactory.getLogger(DataController.class);

	@Autowired
	DataValidationService dataValidationService;

	@Autowired
	DataApprovalService dataApprovalService;

	@Autowired
	ReferenceDataService referenceDataService;

	@Autowired
	EmailService emailService;

	@Autowired
	DataTableModel dataTable;

	@RequestMapping(path = "/read.do", method = RequestMethod.GET)
	@ResponseBody
	public String readILCorSLAData(@RequestParam("dataType") int dataType, @RequestParam("billCycle") String billCycle,
			@RequestParam("towerID") String towerID) {
		List<?> dataList;
		if (dataType == 0) {
			dataList = (ArrayList<ILCData>) dataValidationService.readILCData(billCycle, towerID);
		} else {
			dataList = (ArrayList<SLAData>) dataValidationService.readSLAData(billCycle, towerID);
		}
		return getJsonDataTable(dataType, dataList);
	}

	@RequestMapping(path = "/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateSLAData(@RequestParam("billCycle") String billCycle, @RequestParam("towerID") String towerID,
			@RequestParam("records") ArrayList<?> records, HttpSession userSession) {

		User userProfile = getUserProfile(userSession);
		String userId = userProfile.getUserID();
		return dataValidationService.updateSLAData(userId, billCycle, towerID, records);
	}

	@RequestMapping(path = "/approve.do", method = RequestMethod.GET)
	@ResponseBody
	public String approveSLAData(@RequestParam("billCycle") String billCycle, HttpServletRequest request) {
		boolean approved = false;
		String userId = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			User userProfile = getUserProfile(session);
			userId = userProfile.getUserID();
			int roleId = userProfile.getRoleID();
			approved = dataApprovalService.setUserApproval(billCycle, userId, roleId);
		}
		if (approved && userId != null) {
			emailService.sendEmail(userId);
			return "approved";
		}
		return "rejected";
	}

	private String getJsonDataTable(int dataType, List<?> dataList) {
		dataTable.setHeader(referenceDataService.getTableHeader(dataType));
		dataTable.setBody(dataList);
		Gson gson = new Gson();
		String jsonDataTable = gson.toJson(dataTable);
		return jsonDataTable;
	}
}
