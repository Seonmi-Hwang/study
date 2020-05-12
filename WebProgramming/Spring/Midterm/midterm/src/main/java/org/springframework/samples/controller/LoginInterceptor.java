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
		PerformerForm performerForm = 
			(PerformerForm) WebUtils.getSessionAttribute(request, "userSession");
		if (performerForm == null) {
			PerformerForm performer = new PerformerForm();
			performer.setEmail((String)request.getParameter("email"));
			
			ModelAndView modelAndView = new ModelAndView("performer/login");
			modelAndView.addObject("performer", performer);
			throw new ModelAndViewDefiningException(modelAndView);
		}
		else {
			return true; // PerformerController 실행
		}
	}
}
