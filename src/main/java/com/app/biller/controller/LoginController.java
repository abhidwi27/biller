package com.app.biller.controller;

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

import com.app.biller.model.User;
import com.app.biller.services.LoginService;
import com.app.biller.view.LoginBean;

@Controller
@SessionAttributes("userProfile")
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
	public String login(Model model, @ModelAttribute("loginBean") LoginBean loginBean) {
		String viewName = LOGIN_VIEW;
		if (loginBean != null && loginBean.getUserId() != null & loginBean.getPassword() != null) {
			user = loginService.validateCredentials(loginBean.getUserId(), loginBean.getPassword());
			if (user != null) {
				// logger.info("Logged in User: " + user.getName());
				model.addAttribute("userProfile", user);
				viewName = loginService.getUserHome(user.getRoleID());
				if (viewName.equalsIgnoreCase("Data")) {
					// logger.info("User View Name: " + viewName);
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

	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logoutDo(HttpServletRequest request, SessionStatus sessionStatus) {
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
