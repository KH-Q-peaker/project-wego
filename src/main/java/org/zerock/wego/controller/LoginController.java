package org.zerock.wego.controller;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.config.SessionConfig;
import org.zerock.wego.domain.UserDTO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.oauth.KakaoOAuth;
import org.zerock.wego.service.LoginService;
import org.zerock.wego.service.OAuthService;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/login")
@Controller
public class LoginController {

	private final KakaoOAuth kakaoOAuth;
	private final OAuthService oAuthService;


	@GetMapping
	public String showLogin() throws ControllerException{
		log.trace("showLogin() invoked.");

		return "common/login";
	}// showLogin


	@GetMapping("/kakao")
	public String getKakaoLogin() throws ControllerException{
		log.trace("getKakaoLogin() invoked.");

		return "redirect:" + kakaoOAuth.getLoginURLToGetAuthorizationCode();
	}// getKakaoLogin


	@GetMapping("/kakao/oauth")
	public String getKakaoCallBack(@RequestParam("code") String authorizationCode, Model model) throws JsonProcessingException {
		log.trace("getAccessToken({}, ModelAndView) invoked.", authorizationCode);

		UserVO kakaoUserVO = oAuthService.kakaoLogin(authorizationCode);

		model.addAttribute(SessionConfig.AUTH_KEY_NAME, kakaoUserVO);

		return "redirect:/";
	}// getKakaoCallBack


}// end class

