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
public class OAuthService
implements InitializingBean {

	private final KakaoService kakaoService;
	private final UserService userService;


	@Override
	public void afterPropertiesSet() throws Exception {
		log.trace("afterPropertiesSet() invoked");
		try {
			Objects.requireNonNull(this.kakaoService);
			Objects.requireNonNull(this.userService);
		} 
		catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // afterPropertiesSet


	public UserDTO kakaoLogin(String authorizationCode) throws JsonProcessingException {
		log.trace("kakaoLogin({}) invoked.", authorizationCode);

		KakaoUserInfoDTO kakaoUser = this.getKakaoUserInfoDTOByAuthorizationCode(authorizationCode);

		log.info("kakaoUser: {}", kakaoUser);

		UserDTO userDTO = this.userService.parseUserDTO(kakaoUser);

		return userDTO;
	} // kakaoLogin


	private KakaoUserInfoDTO getKakaoUserInfoDTOByAuthorizationCode(String authorizationCode) {

		KakaoOAuthTokenDTO kakaoOAuthTokenDTO = kakaoService.getAccessToken(authorizationCode);
		KakaoUserInfoDTO kakaoUserInfoDTO = kakaoService.getUserInfo(kakaoOAuthTokenDTO);

		return kakaoUserInfoDTO;

	} // getKakaoUserInfoDTOByAuthorizationCode


}// end class
