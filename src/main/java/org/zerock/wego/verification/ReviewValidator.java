package org.zerock.wego.verification;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.zerock.wego.domain.review.ReviewDTO;


@Component
public class ReviewValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ReviewDTO.class.equals(clazz);
	} // supports

	@Override
	public void validate(Object target, Errors errors) {
		ReviewDTO dto = (ReviewDTO) target;

		ValidationUtils.rejectIfEmpty(errors, "sanInfoId", "sanInfoId값이 비어있습니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title값이 비어있습니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contents", "contents값이 비어있습니다.");

		if (errors.getFieldError("sanInfoId") == null) {
			if (dto.getSanInfoId() < 0 || dto.getSanInfoId() > 100) { 
				errors.rejectValue("sanInfoId", "존재하지 않는 산ID입니다.");
			} // if
		} // if

		if (errors.getFieldError("title") == null) { 
			if(dto.getTitle().length() < 2 || dto.getTitle().length() > 20) {
				errors.rejectValue("title", "입력 가능한 글자 수는 2~20자입니다.");
			} // if
		} // if

		if (errors.getFieldError("contents") == null) {
			if(dto.getContents().length() < 2 || dto.getContents().length() > 1500) {
				errors.rejectValue("contents", "입력 가능한 글자 수는 2~1500자입니다.");
			} // if
		} // if

	} // validate

} // end class