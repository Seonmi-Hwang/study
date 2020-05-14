package org.springframework.samples.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.model.Address;
import org.springframework.samples.service.PerformerService;
import org.springframework.samples.validator.PerformanceValidator;
import org.springframework.samples.validator.PerformerJoinValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("joinForm")
public class PerformerJoinController {

	@Autowired
	PerformerService performerService;
	
	@ModelAttribute("joinForm") 			// request handler methods 보다 먼저 호출됨
	public PerformerForm formBacking(HttpServletRequest request) {
		PerformerForm memRegReq = new PerformerForm();
		Address address = new Address("", "서울", "");		// Address 객체 생성 및 초기화
		memRegReq.setAddress(address);
		memRegReq.setTime("20");
		return memRegReq;
	}
	
	@RequestMapping("/newJoin/step1")
	public String step1() {
		return "join/creationStep1";
	}
	
	@RequestMapping("/newJoin/step2") // step1 -> step2
	public String step2(
			@ModelAttribute("joinForm") PerformerForm memRegReq,
			BindingResult bindingResult) {		
		System.out.println("command 객체: " + memRegReq);
		
		new PerformerJoinValidator().validate(memRegReq, bindingResult);
		
		if (performerService.getPerformerInfoByEmail(memRegReq.getEmail()) != null) {
			bindingResult.reject("sameEmailExist", new Object[] {}, null);
			return "join/creationStep1";
		}
		
		if (bindingResult.hasErrors()) {
			return "join/creationStep1";
		}

		return "join/creationStep2";
	}
	
	@GetMapping("/newJoin/step2")		// step3 -> step2 이동
	public String step2FromStep3() {
		return "join/creationStep2";		// step2 view로 이동
	}

	@PostMapping("/newJoin/step3")		// step2 -> step3 이동
	public String step3(
				@ModelAttribute("joinForm") PerformerForm memRegReq,
				BindingResult result) {
		
		new PerformanceValidator().validate(memRegReq, result);
		if (result.hasErrors())
			return "join/creationStep2";	// 검증 오류 발생 시 step2 view로 이동
		return "join/creationStep3";		// step3 view로 이동
	}
	
	@PostMapping("/newJoin/done")		// step3 -> done 이동
	public String done(
				@ModelAttribute("joinForm") PerformerForm performerForm,
				SessionStatus sessionStatus, Model model) {
		performerService.joinNewPerformer(performerForm);
		sessionStatus.setComplete();	// joinForm session 종료
		
		Date date = new Date();
		model.addAttribute("currentTime", date);
		
		return "join/creationDone";		// done view로 이동
	}

}
