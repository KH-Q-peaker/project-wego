package org.zerock.wego.service.oauth;

import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.common.UserDTO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.domain.oauth.kakao.KakaoOAuthTokenDTO;
import org.zerock.wego.domain.oauth.kakao.KakaoUserInfoDTO;
import org.zerock.wego.domain.oauth.naver.NaverOAuthTokenDTO;
import org.zerock.wego.domain.oauth.naver.NaverUserInfoDTO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.oauth.KakaoOAuth;
import org.zerock.wego.oauth.NaverOAuth;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service
public class OAuthService {

	private final LoginService loginService;
	private final KakaoOAuth kakaoOAuth;
	private final NaverOAuth naverOAuth;


	public UserVO kakaoLogin(String authorizationCode) throws JsonProcessingException {
		log.trace("kakaoLogin(authorizationCode) invoked.");

		KakaoOAuthTokenDTO kakaoOAuthTokenDTO = this.getKakaoAccessToken(authorizationCode);
		KakaoUserInfoDTO kakaoUserInfoDTO = this.getKakaoUserInfo(kakaoOAuthTokenDTO);
		
		String targetSocialId = kakaoUserInfoDTO.getKakao_account().getEmail();
		
		boolean isAlreadySignUp = loginService.isSignUp(targetSocialId);
		
		if(!isAlreadySignUp) {
			UserDTO userDTO = UserDTO.createByKakao(kakaoUserInfoDTO);
			
			this.loginService.signUp(userDTO);
		} // if
		
		return this.loginService.socialLogin(targetSocialId);
		
	} // kakaoLogin

	
	public KakaoOAuthTokenDTO getKakaoAccessToken(String authorizationCode) throws JsonProcessingException {
		log.trace("getKakaoAccessToken(authorizationCode) invoked.");

			ResponseEntity<String> responseEntity = this.kakaoOAuth.requestAccessToken(authorizationCode);

			HttpStatus responseStateCode = responseEntity.getStatusCode();

			log.info("responseCode = {}", responseStateCode);

			if(responseStateCode == HttpStatus.OK) {

				KakaoOAuthTokenDTO kakaoOAuthTokenDTO = this.kakaoOAuth.parseOAuthTokenDTO(responseEntity.getBody());

				return kakaoOAuthTokenDTO;
			}
			else {
				throw new ServiceException("kakao getAccessToken");
			} // if-else

	}// KakaoOAuthTokenDTO


	public KakaoUserInfoDTO getKakaoUserInfo(KakaoOAuthTokenDTO OAuthToken) throws ServiceException{
		log.trace("getKakaoUserInfo(OAuthToken) invoked.");

		try {
			ResponseEntity<String> responseEntity = this.kakaoOAuth.requestUserInfo(OAuthToken);

			HttpStatus responseStateCode = responseEntity.getStatusCode();

			log.info("responseCode = {}", responseStateCode);

			if(responseStateCode == HttpStatus.OK) {

				KakaoUserInfoDTO kakaoUserInfoDTO = this.kakaoOAuth.parseUserInfoDTO(responseEntity.getBody());

				return kakaoUserInfoDTO;
			}
			else {
				throw new ServiceException("kakao getAccessToken");
			} // if-else
		}
		catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch

	}// KakaoUserInfoDTO
	
	
	public UserVO naverLogin(String authorizationCode, String state) throws JsonProcessingException {
		log.trace("naverLogin(authorizationCode) invoked.");

		NaverOAuthTokenDTO naverOAuthTokenDTO = this.getNaverAccessToken(authorizationCode, state);
		NaverUserInfoDTO naverUserInfoDTO = this.getNaverUserInfo(naverOAuthTokenDTO);
		
		String targetSocialId = naverUserInfoDTO.getResponse().getEmail();
		
		boolean isAlreadySignUp = loginService.isSignUp(targetSocialId);
		
		if(!isAlreadySignUp) {
			UserDTO userDTO = UserDTO.createByNaver(naverUserInfoDTO);
			
			this.loginService.signUp(userDTO);
		} // if
		
		return this.loginService.socialLogin(targetSocialId);
		
	} // naverLogin

	
	public NaverOAuthTokenDTO getNaverAccessToken(String authorizationCode, String state) throws JsonProcessingException {
		log.trace("getNaverAccessToken(authorizationCode) invoked.");

			ResponseEntity<String> responseEntity = this.naverOAuth.requestAccessToken(authorizationCode, state);

			HttpStatus responseStateCode = responseEntity.getStatusCode();

			log.info("responseCode = {}", responseStateCode);

			if(responseStateCode == HttpStatus.OK) {

				NaverOAuthTokenDTO naverOAuthTokenDTO = this.naverOAuth.parseOAuthTokenDTO(responseEntity.getBody());

				return naverOAuthTokenDTO;
			}
			else {
				throw new ServiceException("naver getAccessToken");
			} // if-else

	}// getNaverAccessToken


	public NaverUserInfoDTO getNaverUserInfo(NaverOAuthTokenDTO OAuthToken) throws ServiceException{
		log.trace("getNaverUserInfo(OAuthToken) invoked.");

		try {
			ResponseEntity<String> responseEntity = this.naverOAuth.requestUserInfo(OAuthToken);

			HttpStatus responseStateCode = responseEntity.getStatusCode();

			log.info("responseCode = {}", responseStateCode);

			if(responseStateCode == HttpStatus.OK) {

				NaverUserInfoDTO naverUserInfoDTO = this.naverOAuth.parseUserInfoDTO(responseEntity.getBody());

				return naverUserInfoDTO;
			}
			else {
				throw new ServiceException("naver getAccessToken");
			} // if-else
		}
		catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch

	}// getNaverUserInfo

}// end class
