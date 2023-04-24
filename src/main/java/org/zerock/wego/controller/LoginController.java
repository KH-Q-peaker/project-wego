package org.zerock.wego.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.config.SessionConfig;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.oauth.KakaoOAuth;
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
		log.trace("getKakaoLogin(ModelAndView) invoked.");
		
		mav.setViewName("redirect:" + this.kakaoOAuth.getLoginURLToGetAuthorizationCode());

		return mav;
	}// kakaoLogin


	@GetMapping("/kakao/oauth")
	public ModelAndView kakaoLoginSuccess(@RequestParam("code") String authorizationCode, ModelAndView mav) throws JsonProcessingException {
		log.trace("kakaoLoginSecces({}, ModelAndView) invoked.", authorizationCode);

		UserVO kakaoUserVO = this.oAuthService.kakaoLogin(authorizationCode);
		
		mav.setViewName("redirect:/");

		mav.addObject(SessionConfig.AUTH_KEY_NAME, kakaoUserVO);

		return mav;
	}// kakaoLoginSecces


}// end class

