package com.app.biller.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.app.biller.model.Month;
import com.app.biller.model.Tower;
import com.app.biller.model.User;
import com.app.biller.services.DataDisplayService;
import com.app.biller.services.DataDisplayServiceImpl;
import com.app.biller.services.LoginService;
import com.app.biller.view.LoginBean;
import com.google.gson.Gson;

@Controller
@SessionAttributes("userProfile")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	private static final String LOGIN_VIEW = "Login";

	@Autowired
	private User user;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private DataDisplayService dataDisplayService;

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String showLogin(Model model) {
		model.addAttribute("msg", "Please Enter Login Credentials");
		return LOGIN_VIEW;
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(Model model, @ModelAttribute("loginBean") LoginBean loginBean) {
		String viewName = LOGIN_VIEW;
		if (loginBean != null && loginBean.getUserId() != null & loginBean.getPassword() != null) {
			user = loginService.validateCredentials(loginBean.getUserId(), loginBean.getPassword());
			if (user != null) {
				Gson gson = new Gson();
				ArrayList<Month> monthList = dataDisplayService.getMonth();
				List<String> yearList = dataDisplayService.getYear();
				List<Tower> towerList = dataDisplayService.getTowerList();
				String strUserProfile = gson.toJson(user);				
				model.addAttribute("userProfile", user);
				model.addAttribute("strUserProfile", strUserProfile);
				model.addAttribute("monthList", monthList);
				model.addAttribute("yearList", yearList);
				model.addAttribute("towerList", towerList);
				viewName = "Home";
				
				return viewName;
			} else {
				model.addAttribute("error", "User Authentication Failed");
			}

		} else {
			model.addAttribute("error", "Please Enter Login Credentials");
		}
		return viewName;
	}

	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, SessionStatus sessionStatus) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute("userProfile");
			session.invalidate();
		}
		for (Cookie cookie : request.getCookies()) {
			cookie.setMaxAge(0);
		}

		sessionStatus.setComplete();

		return LOGIN_VIEW;
	}
}
