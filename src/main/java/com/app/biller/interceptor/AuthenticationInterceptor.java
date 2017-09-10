package com.app.biller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.app.biller.domain.User;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		if (!uri.endsWith("login.do") && !uri.endsWith("logout.do")) {
			User userProfile = (User) request.getSession().getAttribute("userProfile");
			if (userProfile == null) {
				response.sendRedirect(request.getContextPath()+"/login.do");
				return false;
			}
		}
		return true;
	}
}
