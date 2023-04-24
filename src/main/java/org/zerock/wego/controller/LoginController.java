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


	@GetMapping("/kakao")
	public ModelAndView kakaoLogin(ModelAndView mav) throws ControllerException{
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
	public ModelAndView naverLogin(@SessionAttribute(SessionConfig.NAVER_STATE_NAME)String naverSessionState) throws ControllerException{
		log.trace("naverLogin(ModelAndView) invoked.");
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:" + this.naverOAuth.getLoginURLToGetAuthorizationCode(naverSessionState));

		return mav;
	}// kakaoLogin


	@GetMapping("/naver/oauth")
	public ModelAndView naverLoginSuccess(@RequestParam("code") String authorizationCode, @RequestParam("state")String callBackState, @SessionAttribute(SessionConfig.NAVER_STATE_NAME)String naverSessionState, String error, String error_description) throws JsonProcessingException {
		log.trace("naverLoginSuccess(authorizationCode, {}, {}, {}, {}) invoked.", callBackState, naverSessionState, error, error_description);
		
		if(!callBackState.equals(naverSessionState)) {
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

