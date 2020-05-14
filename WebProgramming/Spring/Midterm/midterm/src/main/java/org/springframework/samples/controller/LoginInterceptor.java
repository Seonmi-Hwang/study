package org.springframework.samples.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.service.PerformerService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
			throws Exception {
		LoginCommand loginCommand = 
			(LoginCommand) WebUtils.getSessionAttribute(request, "login");
		String email = (String)request.getParameter("email");
		if (loginCommand == null || !loginCommand.getEmail().equals(email)) {
			loginCommand = new LoginCommand();
			loginCommand.setEmail(email);
			
			ModelAndView modelAndView = new ModelAndView("performer/login");
			modelAndView.addObject("loginCommand", loginCommand);
			throw new ModelAndViewDefiningException(modelAndView);
		}
		else {
			return true; // PerformerController 실행
		}
	}
}
