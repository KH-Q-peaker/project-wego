package org.zerock.wego.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.config.SessionConfig;
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
@RestController
public class LoginController {

	private final KakaoOAuth kakaoOAuth;
	private final NaverOAuth naverOAuth;
	private final GoogleOAuth googleOAuth;
	private final OAuthService oAuthService;


	@GetMapping
	public ModelAndView showLogin(ModelAndView mav) throws ControllerException{
		log.trace("showLogin(ModelAndView) invoked.");

		mav.setViewName("common/login");
		
		return mav;
	}// showLogin
	
	@GetMapping("/logout")
	public ModelAndView logout(ModelAndView mav) throws ControllerException{
		log.trace("logout(ModelAndView) invoked.");

		mav.setViewName("redirect:/");
		
		return mav;
	}// logout
	
	
	@GetMapping("/google")
	public ModelAndView showGoogleLogin(@SessionAttribute(SessionConfig.SIGN_IN_STATE_NAME)String sessionState) throws ControllerException{
		log.trace("googleLogin(ModelAndView) invoked.");
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:" + this.googleOAuth.getLoginURLToGetAuthorizationCode(sessionState));

		return mav;
	}// googleLogin


	@GetMapping("/google/oauth")
	public ModelAndView googleLoginSuccess(@RequestParam("code") String authorizationCode, @RequestParam("state")String callBackState, @SessionAttribute(SessionConfig.SIGN_IN_STATE_NAME)String sessionState, String scope) throws JsonProcessingException {
		log.trace("googleLoginSuccess({}, {}, {}, {}) invoked.", authorizationCode, callBackState, sessionState, scope);
		
		if(!callBackState.equals(sessionState)) {
			throw new RuntimeException("구글 로그인 실패 : state 불일치");
		}

		UserVO naverVO = this.oAuthService.googleLogin(authorizationCode, callBackState);
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/");

		mav.addObject(SessionConfig.AUTH_KEY_NAME, naverVO);

		return mav;
	}// googleLoginSuccess


	@GetMapping("/kakao")
	public ModelAndView showKakaoLogin(ModelAndView mav) throws ControllerException{
		log.trace("kakaoLogin(ModelAndView) invoked.");
		
		mav.setViewName("redirect:" + this.kakaoOAuth.getLoginURLToGetAuthorizationCode());

		return mav;
	}// kakaoLogin


	@GetMapping("/kakao/oauth")
	public ModelAndView kakaoLoginSuccess(@RequestParam("code") String authorizationCode, ModelAndView mav) throws JsonProcessingException {
		log.trace("kakaoLoginSuccess(authorizationCode, ModelAndView) invoked.");

		UserVO kakaoUserVO = this.oAuthService.kakaoLogin(authorizationCode);
		
		mav.setViewName("redirect:/");

		mav.addObject(SessionConfig.AUTH_KEY_NAME, kakaoUserVO);

		return mav;
	}// kakaoLoginSecces

	
	@GetMapping("/naver")
	public ModelAndView showNaverLogin(@SessionAttribute(SessionConfig.SIGN_IN_STATE_NAME)String sessionState) throws ControllerException{
		log.trace("naverLogin(ModelAndView) invoked.");
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:" + this.naverOAuth.getLoginURLToGetAuthorizationCode(sessionState));

		return mav;
	}// naverLogin


	@GetMapping("/naver/oauth")
	public ModelAndView naverLoginSuccess(@RequestParam("code") String authorizationCode, @RequestParam("state")String callBackState, @SessionAttribute(SessionConfig.SIGN_IN_STATE_NAME)String sessionState, String error, String error_description) throws JsonProcessingException {
		log.trace("naverLoginSuccess(authorizationCode, {}, {}, {}, {}) invoked.", callBackState, sessionState, error, error_description);
		
		if(!callBackState.equals(sessionState)) {
			throw new RuntimeException("네이버 로그인 실패 : state 불일치");
		}
		if(error != null) {
			throw new RuntimeException("네이버 로그인 실패");
		}

		UserVO naverVO = this.oAuthService.naverLogin(authorizationCode, callBackState);
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/");

		mav.addObject(SessionConfig.AUTH_KEY_NAME, naverVO);

		return mav;
	}// naverLoginSuccess
	
	
}// end class

