package com.app.biller.controller;

import java.util.ArrayList;

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
import com.app.biller.model.User;
import com.app.biller.services.DataValidationService;
import com.app.biller.services.EmailService;

@Controller
@RequestMapping("/manage")
public class ManagerController {

	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

	@Autowired
	DataValidationService dataValidationService;

	@Autowired
	EmailService emailService;

	@RequestMapping(path = "/read.do", method = RequestMethod.GET)
	public ModelAndView readILCData() {
		ArrayList<ILCData> ilcDataList = (ArrayList<ILCData>) dataValidationService.readILCData();
		ModelAndView mv = new ModelAndView("Data");
		mv.addObject("ilcDataList", ilcDataList);
		// mv.addObject("myname", myname);
		return mv;

		// model.addAttribute("ilcDataList", ilcDataList);
		// return "Data";
	}

	@RequestMapping(path = "/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateILCData(@RequestParam("userID") String userId) {
		return dataValidationService.updateILCData(userId);
	}

	@RequestMapping(path = "/signoff.do", method = RequestMethod.GET)
	@ResponseBody
	public String signoffILCData(HttpSession userSession) {
		User loggedUser = (User) userSession.getAttribute("userObj");
		String userId = loggedUser.getUserID();
		String signOff = dataValidationService.signoffILCData(userId);
		if (signOff.equalsIgnoreCase("success")) {
			sendSignOffMail(userId);
		}
		return "approved";
	}

	private void sendSignOffMail(String userId) {
		emailService.sendEmail(userId);
	}

}
