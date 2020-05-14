package org.springframework.samples.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.model.PerformerInfo;
import org.springframework.samples.service.PerformerService;
import org.springframework.samples.validator.LoginCommandValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class LoginController { 
	
	@Autowired
	private Authenticator authenticator;

	@RequestMapping("/performer/login")
	public ModelAndView handleRequest(HttpServletRequest request,
			@ModelAttribute("loginCommand") LoginCommand loginCommand,
			BindingResult result) throws Exception {
		
			System.out.println("Login 객체 : " + loginCommand);
			
			new LoginCommandValidator().validate(loginCommand, result);
			
			if (result.hasErrors()) {
				return new ModelAndView("performer/login", "loginCommand", loginCommand);
			}
			
			try {
				authenticator.authenticate(loginCommand);
				WebUtils.setSessionAttribute(request, "login", loginCommand);
				return new ModelAndView("redirect:/index");	 // 검증 성공
			} catch (AuthenticationException ex) { // 검증에 실패했을 경우
				result.reject("notMatchPassword", new Object[] { loginCommand
						.getEmail() }, null);
				return new ModelAndView("performer/login", "loginCommand", loginCommand);
			}

	}
}