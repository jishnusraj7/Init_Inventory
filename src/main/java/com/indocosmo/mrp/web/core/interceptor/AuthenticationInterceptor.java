package com.indocosmo.mrp.web.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.indocosmo.mrp.web.masters.users.model.Users;

//import com.indocosmo.posellaweb.web.login.model.User;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		String rootPath = request.getContextPath();
		if (!uri.endsWith(rootPath + "/") &&!uri.endsWith("login") && !uri.endsWith("logout")) {
			Users userData = (Users) request.getSession().getAttribute(
					"user");
			if (userData == null) {
				response.sendRedirect(rootPath + "/");
				return false;
			}
		}
		return true;
	}
}
