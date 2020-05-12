package org.springframework.samples.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.model.PerformerInfo;
import org.springframework.samples.service.PerformerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.ui.Model;

@Controller
public class LoginController { 

	@Autowired
	private PerformerService performerService;

	@RequestMapping("/performer/login")
	public ModelAndView handleRequest(HttpServletRequest request,
			@ModelAttribute("performer") PerformerForm performer, Model model) throws Exception {
		
			System.out.println("Login 객체 : " + performer);
		
//			new LoginValidator().validate(performer, bindingResult);
			
			PerformerInfo realPerformer = performerService.getPerformerInfoByEmail(performer.getEmail());
			if (!realPerformer.matchPassword(performer.getPassword())) {
				String errorMessage = "아이디(" + performer.getEmail() + ")와 암호가 일치하지 않습니다.";
				return new ModelAndView("performer/login", "message", errorMessage);	// 검증 오류 발생 시 다시 login으로 이동
			} else {
				model.addAttribute("login", realPerformer);
				return new ModelAndView("performer/performerDetail");		// detail
			}
	}
}