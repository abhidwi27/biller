package com.app.biller.controller;

import static com.app.biller.util.BillerHelper.getUserProfile;

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
import com.app.biller.services.DataApprovalService;
import com.app.biller.services.DataLockService;
import com.app.biller.services.DataValidationService;
import com.app.biller.services.EmailService;
import com.app.biller.services.ReferenceDataService;
import com.app.biller.ui.ApprovalStatus;
import com.app.biller.ui.ItwrReference;
import com.app.biller.ui.ResponseDataEnvelope;
import com.app.biller.ui.ReviewWrapper;
import com.app.biller.ui.TableData;
import com.google.gson.Gson;
import com.app.biller.ui.WIASMReference;

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

	enum DelegationStatus {SET, UNSET};

	@RequestMapping(path = "/read.do", method = RequestMethod.GET)
	public @ResponseBody ResponseDataEnvelope readILCorSLAData(@RequestParam("dataType") int dataType,
			@RequestParam("billCycle") String billCycle, @RequestParam("towerID") int towerID,
			@RequestParam("accountId") int accountId, HttpSession userSession) throws Exception {
		List<?> dataList;
		try {
			User userProfile = getUserProfile(userSession);
			String userID = userProfile.getUserID();
			if (dataType == 0) {
				dataList = (ArrayList<ILCData>) dataValidationService.readILCData(billCycle, towerID, accountId);
			} else {
				dataList = (ArrayList<SLAData>) dataValidationService.readSLAData(billCycle, towerID, accountId);
			}
			logger.info("Getting data for ResonsetDataEnvelope...");
			tableData.setHeader(referenceDataService.getTableHeader(dataType));
			tableData.setBody(dataList);
			responseDataEnvelope.setTableData(tableData);
			responseDataEnvelope.setEmployeeList(referenceDataService.getEmployeeList(billCycle, dataType, towerID));
			responseDataEnvelope.setWrList(referenceDataService.getWRList(billCycle, dataType, towerID));
			responseDataEnvelope.setWeekEndList(referenceDataService.getWeekendList(billCycle, dataType, towerID));
			responseDataEnvelope.setRejectForUserList(dataApprovalService.getRejectForUserList(billCycle));
			responseDataEnvelope.setRemarksList(referenceDataService.getRemarksList(billCycle, dataType, towerID));
			responseDataEnvelope.setDataLockedBy(dataLockService.checkLockForTower(billCycle, towerID));
			responseDataEnvelope.setHasApprovedBillCycle(dataApprovalService.checkPriorApproval(billCycle, userID));
			logger.info("ResonsetDataEnvelope ready...");
		}catch(Exception ex) {
			logger.error("Exception occured while executing read method", ex);
			throw new Exception("Generic Exception", ex);
		}
		return responseDataEnvelope;
	}

	@RequestMapping(path = "/readCustom.do", method = RequestMethod.POST)
	public @ResponseBody List<?> readCustomData(@RequestBody DataFilter dataFilter) throws Exception {
		List<?> dataList;
		try {
			if (dataFilter.getDataType() == 0) {
				logger.info("Reading Customized ILC Data...");
				dataList = (ArrayList<ILCData>) dataValidationService.readCustomILCData(dataFilter.getBillCycle(),
						dataFilter.getTowerID(), dataFilter.getWeekEndDate(), dataFilter.getWrNo(), dataFilter.getEmpName(),
						dataFilter.getBillable(), dataFilter.getRemarks(), dataFilter.getAccountId());
			} else {
				logger.info("Reading Customized SLA Data...");
				dataList = (ArrayList<SLAData>) dataValidationService.readCustomSLAData(dataFilter.getBillCycle(),
						dataFilter.getTowerID(), dataFilter.getWeekEndDate(), dataFilter.getWrNo(), dataFilter.getEmpName(),
						dataFilter.getBillable(), dataFilter.getRemarks(), dataFilter.getAccountId());
			}
		}catch(Exception ex){
			logger.error("Exception occured while executing readCustom method", ex);
			throw new Exception("Generic Exception", ex);
		}
		return dataList;
	}

	@RequestMapping(path = "/update.do", method = RequestMethod.POST)
	public @ResponseBody boolean updateSLAData(@RequestParam("billCycle") String billCycle,
			@RequestParam("towerID") int towerID, @RequestBody SaveRecords saveRecords, HttpSession userSession) throws Exception {
		User userProfile = getUserProfile(userSession);
		String userID = userProfile.getUserID();
		boolean slaUpdateResult = false;
		try {
			logger.info("Updating SLA Data...");
			dataValidationService.updateSLAData(billCycle, userID, saveRecords.getUpdateRecords());
			dataValidationService.createNewSLARecord(billCycle, userID, saveRecords.getNewRecords());
			User lockedBy = dataLockService.checkLockForTower(billCycle, towerID);
			if (lockedBy != null) {
				if (lockedBy.getUserID().equals(userID)) {
					dataLockService.unSetLock(userID, billCycle, towerID);
				}
			}
			slaUpdateResult = true;
		} catch (Exception ex) {
			logger.error("Exception occured while updating SLA data.");
			throw new Exception("Generic Exception", ex);
		}
		return slaUpdateResult;
	}

	@RequestMapping(path = "/lock.do", method = RequestMethod.GET)
	public @ResponseBody String lockSLAData(@RequestParam("billCycle") String billCycle,
			@RequestParam("towerID") int towerID, HttpSession userSession) throws Exception {
		User userProfile = getUserProfile(userSession);
		String userID = userProfile.getUserID();
		Gson gson = new Gson();
		HashMap<String, Object> lockResponseMap = new HashMap<String, Object>();
		try {
			User lockedBy = dataLockService.checkLockForTower(billCycle, towerID);
			Tower lockedForTower = dataLockService.checkLockByUser(userID, billCycle);
			lockResponseMap.put("lockedBy", lockedBy);
			if (lockedForTower != null) {
				lockResponseMap.put("lockedForTower", lockedForTower.getTowerName());
			} else {
				lockResponseMap.put("lockedForTower", "");
			}
			if (lockedBy == null && lockedForTower == null) {
				dataLockService.setLock(billCycle, userID, towerID);
				lockResponseMap.put("msg", "success");
			} else {
				lockResponseMap.put("msg", "failed");
			}
		}catch (Exception ex) {
			logger.error("Exception occured while executing lock method.");
			throw new Exception("Generic Exception", ex);
		}
		return gson.toJson(lockResponseMap);
	}
	
	@RequestMapping(path = "/unlock.do", method = RequestMethod.GET)
	public @ResponseBody String unlockSLAData(@RequestParam("billCycle") String billCycle,
			@RequestParam("towerID") int towerID, HttpSession userSession) throws Exception {
		User userProfile = getUserProfile(userSession);
		String userID = userProfile.getUserID();
		Gson gson = new Gson();
		HashMap<String, Object> unlockResponseMap = new HashMap<String, Object>();
		try {
			User lockedBy = dataLockService.checkLockForTower(billCycle, towerID);			
			unlockResponseMap.put("lockedBy", lockedBy);
			
			if (lockedBy != null ) {
				dataLockService.unSetLock(userID,billCycle, towerID);
				unlockResponseMap.put("msg", "success");
			} else {
				unlockResponseMap.put("msg", "failed");
			}
		}catch (Exception ex) {
			logger.error("Exception occured while executing lock method.");
			throw new Exception("Generic Exception", ex);
		}
		return gson.toJson(unlockResponseMap);
	}

	@RequestMapping(path = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody boolean deleteSLAData(@RequestParam("billCycle") String billCycle,
			@RequestBody List<Integer> seqIDList, HttpSession userSession) throws Exception {
		
		boolean slaDeleteResult = false;
		try {
			dataValidationService.deleteSLAData(billCycle, seqIDList);
			slaDeleteResult = true;
		}catch (Exception ex) {
			logger.error("Exception occured while executing deleting SLA Data.");
			throw new Exception("Generic Exception", ex);
		}
		return slaDeleteResult;
	}

	@RequestMapping(path = "/approve.do", method = RequestMethod.GET)
	@ResponseBody
	public ReviewWrapper approveSLAData(@RequestParam("billCycle") String billCycle,
			@RequestParam("approveFor") String approveFor, HttpServletRequest request) throws Exception {
		boolean approval = false;
		String approveBy = "";
		String roleDesc = "";
		int roleID = -1;
		HttpSession session = request.getSession(false);
		ReviewWrapper reviewWrapper = new ReviewWrapper();
		try {
			if (session != null) {
				User userProfile = getUserProfile(session);
				approveBy = userProfile.getUserID();
				roleDesc = userProfile.getRoleDesc();
				roleID = userProfile.getRoleID();
				approval = dataApprovalService.setUserApproval(billCycle, approveBy, approveFor, roleID, roleDesc);
				Tower LockForTower = dataLockService.checkLockByUser(approveFor, billCycle);
				if (approval && LockForTower != null) {
					dataLockService.unSetLock(approveFor, billCycle, LockForTower.getTowerID());
				}
			}
			String activeBillCycle = referenceDataService.getActiveBillCycle();
			reviewWrapper.setApprovalStatus(dataApprovalService.getApprovalStatus(activeBillCycle));
			if (approval && approveFor != null) {
				// emailService.sendEmail(emailService.getEmailID(approveFor));
				String billMonth = referenceDataService.getMonthForBillCycle(billCycle);
				String billYear = billCycle.substring(2, 6);
				emailService.sendApprovalEmail(approveFor, approveBy, billMonth, billYear);
				reviewWrapper.setReviewFlag(1);
			} else {
				reviewWrapper.setReviewFlag(0);
			}
		}catch (Exception ex) {
			logger.error("Exception occured while executing approve method.", ex);
			throw new Exception("Generic Exception", ex);
		}
		return reviewWrapper;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/reject.do", method = RequestMethod.POST)
	@ResponseBody
	public ReviewWrapper rejectSLAData(@RequestParam("billCycle") String billCycle,
			@RequestParam("rejectedFor") String rejectedFor, @RequestBody String rejectComments,
			HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		ReviewWrapper reviewWrapper = new ReviewWrapper();
		Map<String, String> map = new HashMap<String, String>();
		map = (Map<String, String>) gson.fromJson(rejectComments, map.getClass());
		String userID = "";
		HttpSession session = request.getSession(false);
		try {
			String activeBillCycle = referenceDataService.getActiveBillCycle();
			if (session != null) {
				User userProfile = getUserProfile(session);
				userID = userProfile.getUserID();
				dataApprovalService.rejectUserApproval(billCycle, userID, rejectedFor);
				String billMonth = referenceDataService.getMonthForBillCycle(billCycle);
				String billYear = billCycle.substring(2, 6);
				emailService.sendRejectionEmail(rejectedFor, userID, map.get("rejectComments"), billMonth, billYear);
				reviewWrapper.setReviewFlag(1);
			} else {
				reviewWrapper.setReviewFlag(0);
			}		
			reviewWrapper.setApprovalStatus(dataApprovalService.getApprovalStatus(activeBillCycle));
		}catch (Exception ex) {
			logger.error("Exception occured while executing reject method.", ex);
			throw new Exception("Generic Exception", ex);
		}
		return reviewWrapper;
	}

	@RequestMapping(path = "/getApprovalStatus.do", method = RequestMethod.GET)
	public @ResponseBody ApprovalStatus getApprovalStatus() throws Exception {
		ApprovalStatus approvalStatus;
		try {
			String activeBillCycle = referenceDataService.getActiveBillCycle();
			approvalStatus =  dataApprovalService.getApprovalStatus(activeBillCycle);
		}catch (Exception ex) {
			logger.error("Exception occured while executing getApprovalStatus.", ex);
			throw new Exception("Generic Exception", ex);
		}
		return approvalStatus;
	}

	@RequestMapping(path = "/delegate.do", method = RequestMethod.POST)
	public @ResponseBody int manageDelegation(String delegatedTo, String delegateStatus, HttpServletRequest request) throws Exception {
		int delegateResult = 0;
		HttpSession session = request.getSession(false);
		try {
			if (session != null && getUserProfile(session) != null) {
				String delegatedBy = getUserProfile(session).getUserID();
				if(Integer.parseInt(delegateStatus) == 0){
					delegatedTo = "";
					delegateResult = dataApprovalService.unsetDelegation(delegatedBy);
					emailService.sendDelegationEmail(delegatedBy, delegatedTo, DelegationStatus.UNSET.toString());
				} else {
					delegateResult = dataApprovalService.setDelegation(delegatedBy, delegatedTo);
					emailService.sendDelegationEmail(delegatedBy, delegatedTo, DelegationStatus.SET.toString());
				}
			}
		}catch (Exception ex) {
			logger.error("Exception occured while executing delegate method.", ex);
			throw new Exception("Generic Exception", ex);
		}
		return delegateResult;
	}
	
	@RequestMapping(path = "/itwrRef.do", method = RequestMethod.GET)
	public @ResponseBody List<ItwrReference> getItwrReferenceData(@RequestParam("wrNo") String wrNo, HttpSession userSession) throws Exception {	
		try {
			return referenceDataService.getItwrReferenceData(wrNo);
		}catch (Exception ex) {
			logger.error("Exception occured while executing itwrRef method.", ex);
			throw new Exception("Generic Exception", ex);
		}
	}
	
	@RequestMapping(path = "/wiasmRef.do", method = RequestMethod.GET)
	public @ResponseBody List<WIASMReference> getwiasmReferenceData(@RequestParam("wrkitem") String wrkItem, HttpSession userSession) throws Exception {
		try {
			return referenceDataService.getwiasmReferenceData(wrkItem);
		}catch (Exception ex) {
			logger.error("Exception occured while executing wiasmRef method.", ex);
			throw new Exception("Generic Exception", ex);
		}
	}
	
	@RequestMapping(path = "/getBulkUpdateData.do", method = RequestMethod.GET)
	public @ResponseBody List<String> getBulkUpdateData(@RequestParam("dataType") int dataType,
			@RequestParam("billCycle") String billCycle, @RequestParam("headerId") int headerId, HttpSession userSession) throws Exception {	
		try {
			if(dataType != 0) {
				return referenceDataService.getBulkUpdateData(billCycle, headerId);
			}else {
				return null;
			}
		}catch (Exception ex) {
			logger.error("Exception occured while executing getBulkUpdateData method", ex);
			throw new Exception("Generic Exception", ex);
		}
		
	}
}
