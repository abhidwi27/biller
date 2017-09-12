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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.biller.domain.DataFilter;
import com.app.biller.domain.ILCData;
import com.app.biller.domain.SLAData;
import com.app.biller.domain.User;
import com.app.biller.services.DataApprovalService;
import com.app.biller.services.DataLockService;
import com.app.biller.services.DataValidationService;
import com.app.biller.services.EmailService;
import com.app.biller.services.ReferenceDataService;
import com.app.biller.ui.ResponseDataEnvelope;
import com.app.biller.ui.TableData;
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
	DataLockService dataLockService;

	@Autowired
	ReferenceDataService referenceDataService;

	@Autowired
	EmailService emailService;

	@Autowired
	TableData tableData;

	@Autowired
	ResponseDataEnvelope responseDataEnvelope;

	@RequestMapping(path = "/read.do", method = RequestMethod.GET)
	public @ResponseBody ResponseDataEnvelope readILCorSLAData(@RequestParam("dataType") int dataType,
			@RequestParam("billCycle") String billCycle, @RequestParam("towerID") int towerID) {
		List<?> dataList;
		if (dataType == 0) {
			dataList = (ArrayList<ILCData>) dataValidationService.readILCData(billCycle, towerID);
		} else {
			dataList = (ArrayList<SLAData>) dataValidationService.readSLAData(billCycle, towerID);
		}
		tableData.setHeader(referenceDataService.getTableHeader(dataType));
		tableData.setBody(dataList);
		responseDataEnvelope.setTableData(tableData);
		responseDataEnvelope.setEmployeeList(referenceDataService.getEmployeeList(billCycle, dataType));
		responseDataEnvelope.setWrList(referenceDataService.getWRList(billCycle, dataType));
		responseDataEnvelope.setWeekEndList(referenceDataService.getWeekendList(billCycle, dataType));

		return responseDataEnvelope;
	}

	@RequestMapping(path = "/readCustom.do", method = RequestMethod.POST)
	public @ResponseBody List<?> readCustomData(@RequestBody DataFilter dataFilter) {
		List<?> dataList;
		if (dataFilter.getDataType() == 0) {
			dataList = (ArrayList<ILCData>) dataValidationService.readCustomILCData(dataFilter.getBillCycle(),
					dataFilter.getTowerID(), dataFilter.getWeekEndDate(), dataFilter.getWrNo(),
					dataFilter.getEmpName());
		} else {
			dataList = (ArrayList<SLAData>) dataValidationService.readCustomSLAData(dataFilter.getBillCycle(),
					dataFilter.getTowerID(), dataFilter.getWeekEndDate(), dataFilter.getWrNo(),
					dataFilter.getEmpName());
		}

		return dataList;
	}

	@RequestMapping(path = "/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateSLAData(@RequestParam("billCycle") String billCycle, @RequestParam("towerID") int towerID,
			@RequestParam("records") ArrayList<?> records, HttpSession userSession) {
		User userProfile = getUserProfile(userSession);
		String userId = userProfile.getUserID();
		return dataValidationService.updateSLAData(billCycle, towerID, userId, records);
	}

	@RequestMapping(path = "/lock.do", method = RequestMethod.GET)
	public @ResponseBody String lockSLAData(@RequestParam("billCycle") String billCycle,
			@RequestParam("towerID") int towerID, HttpSession userSession) {
		User userProfile = getUserProfile(userSession);
		String userID = userProfile.getUserID();
		String lockedBy = dataLockService.checkLock(billCycle, towerID);
		if (lockedBy.equals("")) {
			dataLockService.setLock(billCycle, userID, towerID);
			return "success";
		} else {
			return lockedBy;
		}
	}

	@RequestMapping(path = "/approve.do", method = RequestMethod.GET)
	@ResponseBody
	public String approveSLAData(@RequestParam("billCycle") String billCycle, HttpServletRequest request) {
		boolean approval = false;
		String userID = null;
		String roleDesc = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			User userProfile = getUserProfile(session);
			userID = userProfile.getUserID();
			roleDesc = userProfile.getRoleDesc();
			int roleID = userProfile.getRoleID();
			approval = dataApprovalService.setUserApproval(billCycle, userID, roleID, roleDesc);
		}
		if (approval && userID != null) {
			emailService.sendEmail(userID);
			return "approved";
		}
		return "rejected";
	}
}
