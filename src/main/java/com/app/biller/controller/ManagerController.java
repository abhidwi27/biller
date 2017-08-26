package com.app.biller.controller;

import java.util.ArrayList;

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
import com.app.biller.services.DataValidationService;

@Controller
@RequestMapping("/manage")
public class ManagerController {

	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

	@Autowired
	DataValidationService dataValidationService;

	@RequestMapping(path = "/read.do", method = RequestMethod.GET)
	public ModelAndView readILCData() {
		logger.info("in read ilc data...");
		ArrayList<ILCData> ilcDataList = dataValidationService.readILCData();
		logger.info("ilc data list size is in controller: " + ilcDataList.size());
		//String myname = "Abhishek";
		
		ModelAndView mv = new ModelAndView("Data");
		mv.addObject("ilcDataList", ilcDataList);
		//mv.addObject("myname", myname);
		logger.info("viewname issss: "+mv.getViewName());
		return mv;

		// model.addAttribute("ilcDataList", ilcDataList);
		// return "Data";
	}

	@RequestMapping(path = "/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateILCData(@RequestParam("userID") String userId) {
		return dataValidationService.updateILCData(userId);
	}

	@RequestMapping(path = "/signoff.do", method = RequestMethod.POST)
	@ResponseBody
	public String signoffILCData(@RequestParam("userID") String userId) {
		return dataValidationService.signoffILCData(userId);
	}

}
