package org.zerock.wego.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.service.common.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@RequestMapping("user")
@RestController
public class UserController {
	
//	private final UserService userService;
//
//	
//	@GetMapping(path = "/json/{targetUserId}")
//	public UserVO getUserInfoById(@PathVariable("targetUserId")Integer targetUserId)
//					throws ServiceException {
//		log.trace("getTargetUserInfo({}) invoked.", targetUserId);
//
//		UserVO targetUserVO = this.userService.getById(targetUserId);
//		
//		return targetUserVO;
//	} // getTargetUserInfo
	// 굳이 필요가 없는 것 같아 삭제예정

} // end class
