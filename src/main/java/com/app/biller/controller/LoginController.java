package com.app.biller.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.app.biller.model.User;
import com.app.biller.services.LoginService;
import com.app.biller.view.LoginBean;

@Controller
@SessionAttributes("userObj")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	private static final String LOGIN_VIEW = "Login";

	@Autowired
	private User user;

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String showLogin(Model model) {
		model.addAttribute("msg", "Please Enter Login Credentials");
		return LOGIN_VIEW;
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String authenticateUser(Model model, @ModelAttribute("loginBean") LoginBean loginBean) {
		String viewName = LOGIN_VIEW;
		if (loginBean != null && loginBean.getUserId() != null & loginBean.getPassword() != null) {
			user = loginService.validateCredentials(loginBean.getUserId(), loginBean.getPassword());
			if (user != null) {
				logger.info("Logged in User: " + user.getName());
				model.addAttribute("userObj", user);
				viewName = loginService.getUserHome(user.getRoleID());
				if(viewName.equalsIgnoreCase("Data")){
					logger.info("User View Name: " + viewName);
					return "redirect:/manage/read.do";
				}
				return viewName;
			} else {
				model.addAttribute("error", "User Authentication Failed");
			}

		} else {
			model.addAttribute("error", "Please Enter Login Credentials");
		}
		return viewName;
	}

}
