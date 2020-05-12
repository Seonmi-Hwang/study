package org.springframework.samples.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.controller.PerformerForm;
import org.springframework.samples.model.PerformerInfo;
import org.springframework.samples.service.PerformerService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class LoginValidator implements Validator {
	
	@Autowired
	private PerformerService performerService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return PerformerForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PerformerForm regReq = (PerformerForm) target;
		
		System.out.println("LoginValidation : " + regReq);
		System.out.println("getEmail : " + regReq.getEmail());
		
		PerformerInfo performer = performerService.getPerformerInfoByEmail(regReq.getEmail());
		if (!performer.matchPassword(regReq.getPassword())) {
			errors.rejectValue("errors", "notMatchPassword");
		}
	}
}
