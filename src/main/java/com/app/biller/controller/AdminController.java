package com.ibm.biller.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibm.biller.services.SystemAdminService;
import com.ibm.biller.services.UserAdminService;

@Controller
@RequestMapping("/login")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	UserAdminService userAdminService;
	
	@Autowired
	SystemAdminService systemAdminService;

	@RequestMapping(path = "/create.do", method = RequestMethod.POST)
	public String createUser(HttpServletRequest request) {
		userAdminService.createUser(request);
		return "User";
	}

	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public String updateUser(HttpServletRequest request) {
		userAdminService.updateUser();
		return null;
	}
	
	@RequestMapping(value = "/unlock.do", method = RequestMethod.GET)
	public String unlockData(HttpServletRequest request) {
		String billCycle = (String) request.getAttribute("billCycle");
		systemAdminService.unlockData(billCycle);
		return null;
	}
}
