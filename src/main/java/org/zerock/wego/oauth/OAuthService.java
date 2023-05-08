package org.zerock.wego.oauth;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.zerock.wego.domain.common.UserDTO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.domain.oauth.google.GoogleOAuthTokenDTO;
import org.zerock.wego.domain.oauth.google.GoogleUserInfoDTO;
import org.zerock.wego.domain.oauth.kakao.KakaoOAuthTokenDTO;
import org.zerock.wego.domain.oauth.kakao.KakaoUserInfoDTO;
import org.zerock.wego.domain.oauth.naver.NaverOAuthTokenDTO;
import org.zerock.wego.domain.oauth.naver.NaverUserInfoDTO;
import org.zerock.wego.exception.LoginException;
import org.zerock.wego.service.oauth.LoginService;

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
	private final GoogleOAuth googleOAuth;


	public UserVO googleLogin(String authorizationCode, String state) throws JsonProcessingException {
		log.trace("googleLogin(authorizationCode, {}) invoked.", state);

		GoogleOAuthTokenDTO googleOAuthTokenDTO = this.getGoogleAccessToken(authorizationCode);
		GoogleUserInfoDTO googleUserInfoDTO = this.getGoogleUserInfo(googleOAuthTokenDTO);

		String targetSocialId = googleUserInfoDTO.getEmail();

		boolean isAlreadySignUp = loginService.isSignUp(targetSocialId);

		if(!isAlreadySignUp) {
			UserDTO userDTO = UserDTO.createByGoogle(googleUserInfoDTO);

			this.loginService.signUp(userDTO);
		} // if

		return this.loginService.socialLogin(targetSocialId);

	} // googleLogin


	public GoogleOAuthTokenDTO getGoogleAccessToken(String authorizationCode) throws JsonProcessingException {
		log.trace("getGoogleAccessToken(authorizationCode) invoked.");

		ResponseEntity<String> responseEntity = this.googleOAuth.requestAccessToken(authorizationCode);

		HttpStatus responseStateCode = responseEntity.getStatusCode();

		if(responseStateCode == HttpStatus.OK) {

			GoogleOAuthTokenDTO naverOAuthTokenDTO = this.googleOAuth.parseOAuthTokenDTO(responseEntity.getBody());

			return naverOAuthTokenDTO;
		} else {

			throw new LoginException("Google login error");
		} // if-else

	}// getGoogleAccessToken


	public GoogleUserInfoDTO getGoogleUserInfo(GoogleOAuthTokenDTO OAuthToken) throws JsonProcessingException{
		log.trace("getGoogleUserInfo(OAuthToken) invoked.");

		ResponseEntity<String> responseEntity = this.googleOAuth.requestUserInfo(OAuthToken);

		HttpStatus responseStateCode = responseEntity.getStatusCode();

		if(responseStateCode == HttpStatus.OK) {

			GoogleUserInfoDTO googleUserInfoDTO = this.googleOAuth.parseUserInfoDTO(responseEntity.getBody());

			return googleUserInfoDTO;
		} else {

			throw new LoginException("Google login error");
		} // if-else

	}// getGoogleUserInfo


	public UserVO kakaoLogin(String authorizationCode) throws JsonProcessingException {
		log.trace("kakaoLogin(authorizationCode) invoked.");

		KakaoOAuthTokenDTO kakaoOAuthTokenDTO = this.getKakaoAccessToken(authorizationCode);
		KakaoUserInfoDTO kakaoUserInfoDTO = this.getKakaoUserInfo(kakaoOAuthTokenDTO);

		ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession session = servletRequestAttribute.getRequest().getSession(true);
	    session.setAttribute("KAKAO_ACCESS_TOKEN", kakaoOAuthTokenDTO.getAccess_token());
		
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

		if(responseStateCode == HttpStatus.OK) {

			KakaoOAuthTokenDTO kakaoOAuthTokenDTO = this.kakaoOAuth.parseOAuthTokenDTO(responseEntity.getBody());

			return kakaoOAuthTokenDTO;
		} else {

			throw new LoginException("Kakao login error");
		} // if-else

	}// KakaoOAuthTokenDTO


	public KakaoUserInfoDTO getKakaoUserInfo(KakaoOAuthTokenDTO OAuthToken) throws JsonProcessingException{
		log.trace("getKakaoUserInfo(OAuthToken) invoked.");

		ResponseEntity<String> responseEntity = this.kakaoOAuth.requestUserInfo(OAuthToken);

		HttpStatus responseStateCode = responseEntity.getStatusCode();

		if(responseStateCode == HttpStatus.OK) {

			KakaoUserInfoDTO kakaoUserInfoDTO = this.kakaoOAuth.parseUserInfoDTO(responseEntity.getBody());

			return kakaoUserInfoDTO;
		} else {

			throw new LoginException("Kakao login error");
		} // if-else

	}// KakaoUserInfoDTO


	public UserVO naverLogin(String authorizationCode, String state) throws JsonProcessingException {
		log.trace("naverLogin(authorizationCode) invoked.");

		NaverOAuthTokenDTO naverOAuthTokenDTO = this.getNaverAccessToken(authorizationCode, state);
		NaverUserInfoDTO naverUserInfoDTO = this.getNaverUserInfo(naverOAuthTokenDTO);

		ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession session = servletRequestAttribute.getRequest().getSession(true);
	    session.setAttribute("NAVER_ACCESS_TOKEN", naverOAuthTokenDTO.getAccess_token());
		
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

		if(responseStateCode == HttpStatus.OK) {

			NaverOAuthTokenDTO naverOAuthTokenDTO = this.naverOAuth.parseOAuthTokenDTO(responseEntity.getBody());

			return naverOAuthTokenDTO;
		} else {

			throw new LoginException("Naver login error");
		} // if-else

	}// getNaverAccessToken


	public NaverUserInfoDTO getNaverUserInfo(NaverOAuthTokenDTO OAuthToken) throws JsonProcessingException{
		log.trace("getNaverUserInfo(OAuthToken) invoked.");

		ResponseEntity<String> responseEntity = this.naverOAuth.requestUserInfo(OAuthToken);

		HttpStatus responseStateCode = responseEntity.getStatusCode();

		if(responseStateCode == HttpStatus.OK) {

			NaverUserInfoDTO naverUserInfoDTO = this.naverOAuth.parseUserInfoDTO(responseEntity.getBody());

			return naverUserInfoDTO;
		} else {

			throw new LoginException("Naver login error");
		} // if-else

	}// getNaverUserInfo

}// end class
