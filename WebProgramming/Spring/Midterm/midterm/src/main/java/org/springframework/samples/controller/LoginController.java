package org.springframework.samples.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.validator.LoginCommandValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import org.springframework.validation.BindingResult;

/*
 * Login form 작성 후
 * 검증 및 session 생성을 하는 controller
 * */
@Controller
public class LoginController { 
	
	@Autowired
	private Authenticator authenticator;

	@RequestMapping("/performer/login")
	public ModelAndView handleRequest(HttpServletRequest request,
			@ModelAttribute("loginCommand") LoginCommand loginCommand,
			@RequestParam(value="forwardAction", required=false) String forwardAction,
			BindingResult result) throws Exception {
		
			System.out.println("Login 객체 : " + loginCommand); // console 출력
			
			new LoginCommandValidator().validate(loginCommand, result); // 검증
			
			ModelAndView mav = new ModelAndView();
			
			if (result.hasErrors()) { // 검증에 실패했을 경우
				mav.addObject("loginForwardAction", forwardAction); 
				mav.addObject("loginCommand", loginCommand);
				mav.setViewName("performer/login"); // login form 이동
				return mav;
			}
			
			try {
				authenticator.authenticate(loginCommand); // email과 password가 맞는지 검증
				WebUtils.setSessionAttribute(request, "login", loginCommand);
				if (forwardAction != null) {
					return new ModelAndView("redirect:" + forwardAction);	 // 검증 성공 시 
				} else {
					return new ModelAndView("redirect:/index");
				}
			} catch (AuthenticationException ex) { // 검증에 실패했을 경우
				result.reject("notMatchPassword", new Object[] { loginCommand.getEmail() }, null); // error message
				mav.addObject("loginForwardAction", forwardAction);
				mav.addObject("loginCommand", loginCommand);
				mav.setViewName("performer/login"); // login form 이동
				return mav;
			}

	}
}