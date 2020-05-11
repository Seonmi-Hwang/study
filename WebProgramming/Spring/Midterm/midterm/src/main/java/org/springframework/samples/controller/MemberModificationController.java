package org.springframework.samples.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.model.PerformerInfo;
import org.springframework.samples.service.PerformerService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member/modify")
public class MemberModificationController {

	private static final String MEMBER_MODIFICATION_FORM = "member/modificationForm";
	private static final String MEMBER_NOT_FOUND_VIEW = "member/memberNotFound";
	
	@Autowired
	private PerformerService performerService;

	@RequestMapping(method = RequestMethod.GET)
	public String form(@ModelAttribute("modReq") PerformerModRequest modReq,
			@RequestParam("pid") String performerId) {
		PerformerInfo pi = performerService.getPerformerInfo(performerId);
		if (pi == null)
			return MEMBER_NOT_FOUND_VIEW;

		modReq.setId(pi.getId());
		modReq.setName(pi.getName());
		modReq.setAddress(pi.getAddress());
		modReq.setEmail(pi.getEmail());
		modReq.setPhoneNumber(pi.getPhoneNumber());

		return MEMBER_MODIFICATION_FORM;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String modify(@ModelAttribute("modReq") PerformerModRequest modReq, Errors errors) {
		if (errors.hasErrors()) {
			return MEMBER_MODIFICATION_FORM;
		}
		try {
			performerService.modifyMemberInfo(modReq);
			return "member/modified";
		} catch (NotMatchPasswordException ex) {
			errors.rejectValue("currentPassword", "invalidPassword");
			return MEMBER_MODIFICATION_FORM;
		} catch (PerformerNotFoundException ex) {
			return MEMBER_NOT_FOUND_VIEW;
		}
	}

	public void setMemberService(PerformerService memberService) {
		this.performerService = memberService;
	}

}
