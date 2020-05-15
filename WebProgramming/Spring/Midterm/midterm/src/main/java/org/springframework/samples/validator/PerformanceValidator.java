package org.springframework.samples.validator;

import org.springframework.samples.controller.PerformerForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PerformanceValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return PerformerForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PerformerForm regReq = (PerformerForm) target;

		// 필수 항목
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "time", "required");
		
		String time = regReq.getTime();
		if (!time.equals("") && Integer.parseInt(time) > 30) {
			errors.rejectValue("time", "tooLongTime");
		}

	}
}
