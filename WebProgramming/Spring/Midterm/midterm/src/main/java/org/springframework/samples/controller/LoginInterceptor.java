package org.springframework.samples.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

/*
 *  /performer/detail 이나 /performer/delete url이 들어올 경우
 *  session이 있는지 확인하고 없으면 login form을 띄워주고
 *  있으면 원래의 controller로 보내주는 interceptor
 * */

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
			throws Exception {
		LoginCommand loginCommand = 
			(LoginCommand) WebUtils.getSessionAttribute(request, "login"); // get login session
		String email = (String)request.getParameter("email"); // 클릭한 email
		if (loginCommand == null || !loginCommand.getEmail().equals(email)) { // session이 없거나 session에 있는 email이 아닐 경우
			loginCommand = new LoginCommand(); 
			loginCommand.setEmail(email);
			
			ModelAndView modelAndView = new ModelAndView("performer/login"); // login form 
			modelAndView.addObject("loginCommand", loginCommand);
			throw new ModelAndViewDefiningException(modelAndView);
		}
		else {
			return true; // PerformerController 실행
		}
	}
}
