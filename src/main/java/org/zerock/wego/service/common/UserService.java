package org.zerock.wego.service.common;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.exception.NotFoundUserException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;




@Log4j2
@RequiredArgsConstructor

@Service
public class UserService {

	private final UserMapper userMapper;


	public UserVO getById(Integer userId) {
		log.trace("getById({}) invoked.", userId);

		boolean isNotExistUser = !isExistById(userId);

		if(isNotExistUser) {
			throw new NotFoundUserException(userId + "없는 유저 입니다.");
		} // if

		return userMapper.selectByUserId(userId);
	}// getById

	public boolean isExistById(Integer userId) {
		log.trace("isExistById({}) invoked.", userId);

		return userMapper.selectByUserId(userId) != null;
	}// isExistById


}// end class
