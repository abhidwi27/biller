package com.app.biller.controller;

import static com.app.biller.util.BillerHelper.getUserProfile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.app.biller.domain.SaveRecords;
import com.app.biller.domain.Tower;
import com.app.biller.domain.User;
import com.app.biller.domain.UserApproval;
import com.app.biller.services.DataApprovalService;
import com.app.biller.services.DataLockService;
import com.app.biller.services.DataValidationService;
import com.app.biller.services.EmailService;
import com.app.biller.services.ReferenceDataService;
import com.app.biller.ui.ApprovalStatus;
import com.app.biller.ui.ResponseDataEnvelope;
import com.app.biller.ui.ReviewWrapper;
import com.app.biller.ui.TableData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


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
			@RequestParam("billCycle") String billCycle, @RequestParam("towerID") int towerID, HttpSession userSession) {
		List<?> dataList;
		User userProfile = getUserProfile(userSession);
		String userID = userProfile.getUserID();
		
		if (dataType == 0) {
			dataList = (ArrayList<ILCData>) dataValidationService.readILCData(billCycle, towerID);
		} else {
			dataList = (ArrayList<SLAData>) dataValidationService.readSLAData(billCycle, towerID);
		}
		tableData.setHeader(referenceDataService.getTableHeader(dataType));
		tableData.setBody(dataList);
		responseDataEnvelope.setTableData(tableData);
		responseDataEnvelope.setEmployeeList(referenceDataService.getEmployeeList(billCycle, dataType, towerID));
		responseDataEnvelope.setWrList(referenceDataService.getWRList(billCycle, dataType, towerID));
		responseDataEnvelope.setWeekEndList(referenceDataService.getWeekendList(billCycle, dataType, towerID));
		responseDataEnvelope.setRejectForUserList(dataApprovalService.getRejectForUserList(billCycle));
		responseDataEnvelope.setDataLockedBy(dataLockService.checkLockForTower(billCycle, towerID));
		responseDataEnvelope.setHasApprovedBillCycle(dataApprovalService.checkPriorApproval(billCycle, userID));
		
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
	public @ResponseBody boolean updateSLAData(@RequestParam("billCycle") String billCycle, @RequestParam("towerID") int towerID,
			@RequestBody SaveRecords saveRecords, HttpSession userSession) {
		User userProfile = getUserProfile(userSession);
		String userID = userProfile.getUserID();
		
		try {
			dataValidationService.updateSLAData(billCycle, userID, saveRecords.getUpdateRecords());
			dataValidationService.createNewSLARecord(billCycle, userID, saveRecords.getNewRecords());
			User lockedBy = dataLockService.checkLockForTower(billCycle, towerID);
			if(lockedBy!= null) {				
					if(lockedBy.getUserID().equals(userID)) {
							dataLockService.unSetLock(userID, billCycle, towerID);
					}
			}
			return true;
		} catch(Exception ex)
		{
			return false;
		}
		
	}

	@SuppressWarnings("unused")
	@RequestMapping(path = "/lock.do", method = RequestMethod.GET)
	public @ResponseBody String lockSLAData(@RequestParam("billCycle") String billCycle,
			@RequestParam("towerID") int towerID, HttpSession userSession) {
		User userProfile = getUserProfile(userSession);
		String userID = userProfile.getUserID();
		
		Gson gson = new Gson();
		HashMap<String, Object> lockResponseMap = new HashMap<String, Object>();
		User lockedBy = dataLockService.checkLockForTower(billCycle, towerID);
		Tower lockedForTower = dataLockService.checkLockByUser(userID, billCycle);
		
		
		lockResponseMap.put("lockedBy", lockedBy);
		if(lockedForTower != null) {
			lockResponseMap.put("lockedForTower", lockedForTower.getTowerName());
		}else {
			lockResponseMap.put("lockedForTower", "");
		}
		
		if (lockedBy == null && lockedForTower == null) {
			dataLockService.setLock(billCycle, userID, towerID);
			lockResponseMap.put("msg", "success");
		} else {
			lockResponseMap.put("msg", "failed");
		}
		
		return gson.toJson(lockResponseMap);
	}
	
	@RequestMapping(path = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody boolean deleteSLAData(@RequestParam("billCycle") String billCycle,
			@RequestBody List<Integer> seqIDList, HttpSession userSession) {
		User userProfile = getUserProfile(userSession);
		String userID = userProfile.getUserID();
		dataValidationService.deleteSLAData(billCycle, seqIDList);
		return true;
	}

	@RequestMapping(path = "/approve.do", method = RequestMethod.GET)
	@ResponseBody
	public ReviewWrapper approveSLAData(@RequestParam("billCycle") String billCycle, @RequestParam("approveFor") String approveFor, HttpServletRequest request) {
		boolean approval = false;
		String approveBy = null;
		String roleDesc = null;
		HttpSession session = request.getSession(false);
		ReviewWrapper reviewWrapper = new ReviewWrapper();
		if (session != null) {
			User userProfile = getUserProfile(session);
			approveBy = userProfile.getUserID();
			roleDesc = userProfile.getRoleDesc();
			int roleID = userProfile.getRoleID();
			approval = dataApprovalService.setUserApproval(billCycle, approveBy, approveFor, roleID, roleDesc);
			Tower LockForTower = dataLockService.checkLockByUser(approveFor, billCycle);
			if (approval && LockForTower != null) {
				dataLockService.unSetLock(approveFor, billCycle, LockForTower.getTowerID());
			}
			
		}
		
		String activeBillCycle = referenceDataService.getActiveBillCycle();		
		reviewWrapper.setApprovalStatus( dataApprovalService.getApprovalStatus(activeBillCycle));
		if (approval && approveFor != null) {
			//emailService.sendEmail(emailService.getEmailID(approveFor));
			String billMonth = referenceDataService.getMonthForBillCycle(billCycle);
			String billYear = billCycle.substring(2, 6);
			emailService.sendApprovalEmail(approveFor, approveBy, billMonth, billYear);
			reviewWrapper.setReviewFlag(1);
		}else {
			reviewWrapper.setReviewFlag(0);
		}
		return reviewWrapper;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/reject.do", method = RequestMethod.POST)
	@ResponseBody
	public ReviewWrapper rejectSLAData(@RequestParam("billCycle") String billCycle, @RequestParam("rejectedFor") String rejectedFor, @RequestBody String rejectComments, HttpServletRequest request) {
		
		Gson gson = new Gson(); 
		ReviewWrapper reviewWrapper = new ReviewWrapper();
		Map<String,String> map = new HashMap<String,String>();
		map = (Map<String,String>) gson.fromJson(rejectComments, map.getClass());
		String userID = null;;
		HttpSession session = request.getSession(false);
		String activeBillCycle = referenceDataService.getActiveBillCycle();		
		
		if (session != null) {
			User userProfile = getUserProfile(session);
			userID = userProfile.getUserID();
			dataApprovalService.rejectUserApproval(billCycle, userID, rejectedFor);
			String billMonth = referenceDataService.getMonthForBillCycle(billCycle);
			String billYear = billCycle.substring(2, 6);
			emailService.sendRejectionEmail(rejectedFor, userID, map.get("rejectComments"), billMonth, billYear);
			reviewWrapper.setReviewFlag(1);
		}else {
			reviewWrapper.setReviewFlag(0);
		}
		reviewWrapper.setApprovalStatus( dataApprovalService.getApprovalStatus(activeBillCycle));
		return reviewWrapper;
	}
	
	@RequestMapping(path = "/getApprovalStatus.do", method = RequestMethod.GET)	
	public @ResponseBody ApprovalStatus getApprovalStatus() {
			String activeBillCycle = referenceDataService.getActiveBillCycle();
			return dataApprovalService.getApprovalStatus(activeBillCycle);			
	}
	
	@RequestMapping(path = "/delegate.do", method = RequestMethod.POST)	
	public @ResponseBody int delegateTo(String delegateTo, String delegateStatus, HttpServletRequest request) {
		
		int delegateResult = 0;		
		HttpSession session = request.getSession(false);
		if (session != null) {
			User userProfile = getUserProfile(session);			
		    String delegateBy = userProfile.getUserID();
			delegateResult = dataApprovalService.updateDelegateUser(delegateBy, delegateTo, Integer.parseInt(delegateStatus));
		}		
		return delegateResult;
		
		
	}
}
