package org.zerock.wego.service;

import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.UserDTO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.UserMapper;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import oracle.jdbc.driver.PlsqlIbtBindInfo;




@Log4j2
@RequiredArgsConstructor

@Service
public class LoginService {

	private final UserMapper userMapper;


	// 회원가입 가입
	public void signUp(UserDTO userDTO) {
		log.trace("signUp({}) invoked.", userDTO);
		
		boolean isAlreadySignUp = isSignUp(userDTO.getSocialId());

		if (isAlreadySignUp) {
			log.error("{} is Already SignUp.", userDTO.getSocialId());
			
			throw new RuntimeException("이미 가입된 회원입니다.");
		} // if

		userMapper.insertUser(userDTO);
	} // signUp
	

	// 회원가입 확인
	public boolean isSignUp(String socialId) {

		return userMapper.selectBySocialId(socialId) != null;
	} // isSignUp


	// 소셜 아이디로 로그인
	public UserVO socialLogin(String socialId) {
		log.trace("loginBySocialId({}) invoked.", socialId);

		return userMapper.selectBySocialId(socialId);

	}// loginBySocialId

}// end class
