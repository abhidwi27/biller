package com.app.biller.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.biller.services.SystemAdminService;
import com.app.biller.services.UserAdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	UserAdminService userAdminService;

	@Autowired
	SystemAdminService systemAdminService;

	/*
	 * @RequestMapping(path = "/create.do", method = RequestMethod.POST) public
	 * String createUser(HttpServletRequest request) {
	 * userAdminService.createUser(request); return "User"; }
	 */

	@RequestMapping(value = "/reset.do", method = RequestMethod.GET)
	public String resetPassword(HttpServletRequest request) {
		String userId = (String) request.getAttribute("userId");
		String password = (String) request.getAttribute("password");
		userAdminService.resetPassword(userId, password);
		return "login";
	}

	@RequestMapping(value = "/unlock.do", method = RequestMethod.GET)
	public String unlockData(HttpServletRequest request) {
		String billCycle = (String) request.getAttribute("billCycle");
		systemAdminService.unLockData(billCycle);
		return "login";
	}
}
