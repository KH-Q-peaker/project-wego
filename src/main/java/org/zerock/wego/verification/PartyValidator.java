package org.zerock.wego.verification;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.zerock.wego.domain.party.PartyDTO;


@Component
public class PartyValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PartyDTO.class.equals(clazz);
	} // supports

	@Override
	public void validate(Object target, Errors errors) {
		PartyDTO dto = (PartyDTO) target;

		ValidationUtils.rejectIfEmpty(errors, "sanInfoId", "sanInfoId값이 유효하지 않습니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title값이 유효하지 않습니다.");
		ValidationUtils.rejectIfEmpty(errors, "partyDt", "partyDt값이 유효하지 않습니다.");
		ValidationUtils.rejectIfEmpty(errors, "partyMax", "partyMax값이 유효하지 않습니다.");

		if (errors.getFieldError("sanInfoId") == null) {
			if (dto.getSanInfoId() < 0 || dto.getSanInfoId() > 100) { 
				errors.rejectValue("sanInfoId", "존재하지 않는 산ID 입니다.");
			} // if
		} // if

		if (errors.getFieldError("title") == null) { 
			if(dto.getTitle().length() < 2 || dto.getTitle().length() > 20) {
				errors.rejectValue("title", "입력 가능한 글자 수는 2~20자 입니다.");
			} // if
		} // if

		if (errors.getFieldError("partyDt") == null) {
			
	        Long datetime = System.currentTimeMillis();
	        Timestamp now = new Timestamp(datetime);
			
			if(dto.getPartyDt().before(now)) {
				errors.rejectValue("partyDt", "현재보다 이전날짜 및 시간으로 지정할 수 없습니다.");
			} // if
		} // if

		if (errors.getFieldError("partyMax") == null) { 
			if(dto.getPartyMax() < 2 || dto.getPartyMax() > 45) {
				errors.rejectValue("partyMax", "모집 가능 인원은 2 ~ 45명입니다.");
			} // if
		} // if

	} // validate

} // end class