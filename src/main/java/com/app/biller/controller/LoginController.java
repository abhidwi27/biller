package com.app.biller.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.app.biller.domain.User;
import com.app.biller.services.DataApprovalService;
import com.app.biller.services.LoginService;
import com.app.biller.services.ReferenceDataService;
import com.app.biller.ui.LoginModel;
import com.google.gson.Gson;

@Controller
@SessionAttributes("userProfile")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	private static final String LOGIN_VIEW = "login";

	@Value("${TEST_LABEL}")
	private String testLabel;

	@Autowired
	private User user;

	@Autowired
	private LoginService loginService;

	@Autowired
	private ReferenceDataService referenceDataService;

	@Autowired
	private DataApprovalService dataApprovalService;

	@RequestMapping(value = "/app.do", method = RequestMethod.GET)
	public String showLogin(Model model) {
		// model.addAttribute("msg", "Please Enter Login Credentials");
//		System.out.println("===============================:::: "+testLabel);
		return LOGIN_VIEW;
	}

	@RequestMapping(value = "/app.do", method = RequestMethod.POST)
	public String processLogin(Model model, @ModelAttribute("loginModel") LoginModel loginModel,
			HttpServletRequest request) {
		String viewName = LOGIN_VIEW;
		try {
			if (loginModel != null && !loginModel.getUserId().isEmpty() & !loginModel.getPassword().isEmpty()) {
				//logger.info("UserId: " + loginModel.getUserId() + " | " + "Password: " + loginModel.getPassword());
				user = loginService.validateCredentials(loginModel.getUserId(), loginModel.getPassword());				
				if (user != null) {
					logger.info("User logged in with ID " + loginModel.getUserId());
					Gson gson = new Gson();
					String strUserProfile = gson.toJson(user);
					model.addAttribute("userProfile", user);
					model.addAttribute("strUserProfile", strUserProfile);
					model.addAttribute("monthList", referenceDataService.getMonth());
					model.addAttribute("yearList", referenceDataService.getYear());
					model.addAttribute("towerList", referenceDataService.getTowerList());
					model.addAttribute("accountList", referenceDataService.getAccountList());
					model.addAttribute("bulkUpdateHeaderList", referenceDataService.getHeaderForBulkUpdate());
					model.addAttribute("customizeList", referenceDataService.getHeaderForCustomise());
					model.addAttribute("delegateStatus",referenceDataService.getDelegateStatus(user.getUserID()));
					if (user.getRoleID() == 2 || user.getRoleID() == 3) {
						model.addAttribute("delegateUserList", referenceDataService.getDelegateUserList(user.getUserID()));
						model.addAttribute("delegateByUserList",
								dataApprovalService.getDelegateByUserList(user.getUserID()));
					}
					// viewName = "Home";
					viewName = loginService.getUserHome(user.getRoleID());
					return viewName;
				} else {
					model.addAttribute("error", "Wrong Username / Password. Authentication Failed");
					logger.info("Wrong Username / Password. Authentication Failed");
				}
			} else {
				model.addAttribute("error", "Empty Username / Password. Please enter valid input.");
				logger.info("Empty Username / Password. Please enter valid input.");
			}
		}catch(Exception ex) {
			logger.error("Exception in login controller: ", ex);
			return "error";
		}
		return viewName;
	}

	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String processLogout(HttpServletRequest request, SessionStatus sessionStatus) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute("userProfile");
			session.invalidate();
		}
		for (Cookie cookie : request.getCookies()) {
			cookie.setMaxAge(0);
		}
		sessionStatus.setComplete();
		return "redirect:" + "/app.do	";
	}
	
	@RequestMapping(value = "/keepAlive.do", method = RequestMethod.GET)
	public @ResponseBody void keepSessionAlive(HttpServletRequest request, SessionStatus sessionStatus) {
		// This method has been added to stop server session from timing out.
	}
}
