package org.zerock.wego.service;

import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.UserDTO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.domain.oauth.kakao.KakaoOAuthTokenDTO;
import org.zerock.wego.domain.oauth.kakao.KakaoUserInfoDTO;
import org.zerock.wego.exception.ServiceException;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;




@Log4j2
@RequiredArgsConstructor

@Service
public class OAuthService {

	private final KakaoService kakaoService;
	private final LoginService loginService;


	public UserVO kakaoLogin(String authorizationCode) throws JsonProcessingException {
		log.trace("kakaoLogin({}) invoked.", authorizationCode);

		KakaoOAuthTokenDTO kakaoOAuthTokenDTO = kakaoService.getAccessToken(authorizationCode);
		KakaoUserInfoDTO kakaoUserInfoDTO = kakaoService.getUserInfo(kakaoOAuthTokenDTO);
		
		String targetSocialId = kakaoUserInfoDTO.getKakao_account().getEmail();
		
		boolean isAlreadySignUp = loginService.isSignUp(targetSocialId);
		
		if(!isAlreadySignUp) {
			UserDTO userDTO = UserDTO.createByKakao(kakaoUserInfoDTO);
			
			loginService.signUp(userDTO);
		} // if
		
		return loginService.socialLogin(targetSocialId);
		
	} // kakaoLogin


}// end class
