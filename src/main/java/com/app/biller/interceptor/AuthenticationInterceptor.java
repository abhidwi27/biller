package com.app.biller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.app.biller.domain.User;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	private static final long MAX_INACTIVE_SESSION_TIME = 1800 * 1000; // 30 mins

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		if (!uri.endsWith("app.do") && !uri.endsWith("logout.do")) {
			User userProfile = (User) request.getSession().getAttribute("userProfile");
			if (userProfile == null) {
				logger.warn("User not Logged in.");
				response.sendRedirect(request.getContextPath() + "/app.do");
				return false;
			}
		}

		if (System.currentTimeMillis() - request.getSession().getLastAccessedTime() > MAX_INACTIVE_SESSION_TIME) {
			HttpSession session = request.getSession();
			session.removeAttribute("userProfile");
			session.invalidate();
			logger.warn("Inactive session. Logged out.");
			response.sendRedirect(request.getContextPath() + "/logout.do");
			return false;
		}

		return true;
	}
}
