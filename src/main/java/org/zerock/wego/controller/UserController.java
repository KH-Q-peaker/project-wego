package org.zerock.wego.controller;

import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@RequestMapping("user")
@RestController
public class UserController 
	implements InitializingBean{

	private final UserService userService;
	
	@Override
	public void afterPropertiesSet() throws ServiceException {
		log.trace("afterPropertiesSet() invoked");
		try {
			Objects.requireNonNull(this.userService);
		}
		catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // afterPropertiesSet

	@GetMapping(
			path = "/json/{targetUserId}",
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			})
	public UserVO getTargetUserInfo(
			@PathVariable("targetUserId")Long targetUserId,
			Model model)
					throws ServiceException {
		log.trace("getTargetUserInfo({}) invoked.", targetUserId);

		UserVO targetUserVO = this.userService.getTargetUserInfo(targetUserId);
		
		return targetUserVO;
	} // getTargetUserInfo

} // end class
