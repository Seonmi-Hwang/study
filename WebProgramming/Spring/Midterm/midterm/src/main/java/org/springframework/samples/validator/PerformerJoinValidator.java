package org.springframework.samples.validator;

import org.springframework.samples.controller.PerformerForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PerformerJoinValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PerformerForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PerformerForm regReq = (PerformerForm) target;
		if (regReq.getEmail() == null || regReq.getEmail().trim().isEmpty()) {
			errors.rejectValue("email", "required");
		}

		// 필수 항목
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "required");
		
		String emailRegax = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";
		if (!regReq.getEmail().equals("") && !regReq.getEmail().matches(emailRegax)) {
			errors.rejectValue("email", "typeMismatch");
		}
		
		if (regReq.hasPassword()) {
			if (regReq.getPassword().length() < 5) {// 암호 6글자 이상
				errors.rejectValue("password", "shortPassword");
			}
			if (!regReq.isSamePasswordConfirmPassword()) {
				errors.rejectValue("confirmPassword", "notSame");
			}
		}
		
		String zipcode = regReq.getAddress().getZipcode();
		if (!zipcode.equals("") && !zipcode.matches("^\\d{5}$")) {
			errors.pushNestedPath("address");
			try {
				errors.rejectValue("zipcode", "typeMismatch");
			} finally {
				errors.popNestedPath();
			}
		}
		
		String phoneNumber = regReq.getPhoneNumber();
		if (!phoneNumber.equals("") && !phoneNumber.matches("^[0][1]\\d{1}-\\d{3,4}-\\d{4}$")) {
			errors.rejectValue("phoneNumber", "typeMismatch");
		}
	}

}
