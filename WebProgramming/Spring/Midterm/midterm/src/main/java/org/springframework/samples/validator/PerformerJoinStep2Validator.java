package org.springframework.samples.validator;

import org.springframework.samples.controller.PerformerForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PerformerJoinStep2Validator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return PerformerForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PerformerForm regReq = (PerformerForm) target;

		// 필수 입력 항목
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "time", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "day", "required");
		
		String time = regReq.getTime();
		try {
			if (!time.equals("") && Integer.parseInt(time) > 30) {
				errors.rejectValue("time", "tooLongTime"); // 공연 시간 30분 제한 검증
			}
		} catch (NumberFormatException ex) { // error 처리
			errors.rejectValue("time", "isNotNumber"); // 공연 시간을 정수로 입력했는지 검증
		}

	}
}
