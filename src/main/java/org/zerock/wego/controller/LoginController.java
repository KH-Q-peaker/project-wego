package org.zerock.wego.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.config.SessionConfig;
import org.zerock.wego.config.WegoConfig;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.oauth.GoogleOAuth;
import org.zerock.wego.oauth.KakaoOAuth;
import org.zerock.wego.oauth.NaverOAuth;
import org.zerock.wego.service.oauth.OAuthService;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/login")
@Controller
public class LoginController {

	private final KakaoOAuth kakaoOAuth;
	private final NaverOAuth naverOAuth;
	private final GoogleOAuth googleOAuth;
	private final OAuthService oAuthService;


	@GetMapping
	public String showLogin() throws ControllerException{
		log.trace("showLogin(String) invoked.");

		return "common/login";
	}// showLogin
	
	@GetMapping("/logout")
	public String logout() throws ControllerException{
		log.trace("logout(String) invoked.");

		return "redirect:/";
	}// logout
	
	
	@GetMapping("/google")
	public String showGoogleLogin(@SessionAttribute(SessionConfig.SIGN_IN_STATE_NAME)String sessionState) throws ControllerException{
		log.trace("googleLogin(String) invoked.");
		
		return "redirect:" + this.googleOAuth.getLoginURLToGetAuthorizationCode(sessionState);
	}// googleLogin


	@GetMapping("/google/oauth")
	public String googleLoginSuccess(
			@RequestParam("code") String authorizationCode,
			@RequestParam("state")String callBackState,
			@SessionAttribute(SessionConfig.SIGN_IN_STATE_NAME)String sessionState,
			String scope, Model model) throws RuntimeException, JsonProcessingException {
		log.trace("googleLoginSuccess({}, {}, {}, {}) invoked.", authorizationCode, callBackState, sessionState, scope);
		
		if(!callBackState.equals(sessionState)) {
			log.error("구글 로그인 state 불일치");
			
			throw new org.zerock.wego.exception.LoginException("잘못된 접근입니다.");
		}

		UserVO naverVO = this.oAuthService.googleLogin(authorizationCode, callBackState);
		
		model.addAttribute(SessionConfig.AUTH_KEY_NAME, naverVO);

		return "redirect:/";
	}// googleLoginSuccess


	@GetMapping("/kakao")
	public String showKakaoLogin() throws ControllerException{
		log.trace("kakaoLogin(String) invoked.");
		
		return "redirect:" + this.kakaoOAuth.getLoginURLToGetAuthorizationCode();
	}// kakaoLogin


	@GetMapping("/kakao/oauth")
	public String kakaoLoginSuccess(@RequestParam("code") String authorizationCode, Model model) throws JsonProcessingException {
		log.trace("kakaoLoginSuccess(authorizationCode, String) invoked.");

		UserVO kakaoUserVO = this.oAuthService.kakaoLogin(authorizationCode);

		model.addAttribute(SessionConfig.AUTH_KEY_NAME, kakaoUserVO);

		return "redirect:/";
	}// kakaoLoginSecces

	
	@GetMapping("/naver")
	public String showNaverLogin(@SessionAttribute(SessionConfig.SIGN_IN_STATE_NAME)String sessionState) throws ControllerException{
		log.trace("naverLogin(String) invoked.");
		
		return "redirect:" + this.naverOAuth.getLoginURLToGetAuthorizationCode(sessionState);
	}// naverLogin


	@GetMapping("/naver/oauth")
	public String naverLoginSuccess(@RequestParam("code") String authorizationCode,
			@RequestParam("state")String callBackState,
			@SessionAttribute(SessionConfig.SIGN_IN_STATE_NAME)String sessionState,
			String error, String error_description, Model model) throws JsonProcessingException {
		log.trace("naverLoginSuccess(authorizationCode, {}, {}, {}, {}) invoked.", callBackState, sessionState, error, error_description);
		
		if(!callBackState.equals(sessionState)) {
			log.error("네이버 로그인 state 불일치");
			
			throw new org.zerock.wego.exception.LoginException("잘못된 접근입니다.");
		}
		if(error != null) {
			log.error("네이버 로그인 Error {} : {}", error, error_description);
						
			throw new org.zerock.wego.exception.LoginException("네이버 로그인 실패");
		}

		UserVO naverVO = this.oAuthService.naverLogin(authorizationCode, callBackState);
		
		model.addAttribute(SessionConfig.AUTH_KEY_NAME, naverVO);

		return "redirect:/";
	}// naverLoginSuccess
	
	
}// end class

