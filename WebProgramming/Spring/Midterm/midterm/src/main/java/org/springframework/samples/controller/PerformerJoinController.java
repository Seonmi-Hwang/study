package org.springframework.samples.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.model.Address;
import org.springframework.samples.service.PerformerService;
import org.springframework.samples.validator.PerformerJoinStep2Validator;
import org.springframework.samples.validator.PerformerJoinStep1Validator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("performerForm") // performerForm 객체를 session에 저장
public class PerformerJoinController {

	@Autowired
	PerformerService performerService;
	
	@ModelAttribute("performerForm") // performerForm 객체 생성
	public PerformerForm formBacking(HttpServletRequest request) { // request handler methods 보다 먼저 호출
		PerformerForm performerForm = new PerformerForm();
		Address address = new Address("", "서울", "");		// Address 객체 생성 및 초기화
		performerForm.setAddress(address);
		performerForm.setTime("20"); // 공연시간 default 값 20분
		return performerForm;
	}
	
	@RequestMapping("/newJoin/step1") // step1 요청
	public String step1() {
		return "join/creationStep1"; // step1 view로 이동
	}
	
	@RequestMapping("/newJoin/step2") // step1 -> step2 이동
	public String step2(
			@ModelAttribute("performerForm") PerformerForm performerForm,
			BindingResult bindingResult) {		
		System.out.println("command 객체: " + performerForm);
		// session에 저장된 performerForm 객체에 저장된 입력 값 검증 
		new PerformerJoinStep1Validator().validate(performerForm, bindingResult);
		
		// 같은 이메일 아이디가 이미 존재할 경우 다시 step1 form 띄움
		if (performerService.getPerformerInfoByEmail(performerForm.getEmail()) != null) {
			bindingResult.reject("sameEmailExist", new Object[] {}, null);
			return "join/creationStep1";
		}
		
		// 검증 오류 발생 시 step1 view로 이동
		if (bindingResult.hasErrors()) {
			return "join/creationStep1";
		}

		return "join/creationStep2"; // step2 view로 이동
	}

	@PostMapping("/newJoin/step3") // step2 -> step3 이동
	public String step3(
				@ModelAttribute("performerForm") PerformerForm performerForm,
				BindingResult result) {
		
		new PerformerJoinStep2Validator().validate(performerForm, result);
		if (result.hasErrors())
			return "join/creationStep2"; // 검증 오류 발생 시 step2 view로 이동
		return "join/creationStep3"; // step3 view로 이동
	}
	
	@PostMapping("/newJoin/done") // step3 -> done 이동
	public String done(
				@ModelAttribute("performerForm") PerformerForm performerForm,
				SessionStatus sessionStatus, Model model) {
		performerService.joinNewPerformer(performerForm);
		sessionStatus.setComplete(); // performerForm session 종료
		
		Date date = new Date();
		model.addAttribute("currentTime", date); // 참가 등록 시간
		
		return "join/creationDone";	// done view로 이동
	}

}
