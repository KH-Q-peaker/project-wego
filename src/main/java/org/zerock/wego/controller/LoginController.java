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
import org.zerock.wego.domain.UserDTO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.oauth.KakaoOAuth;
import org.zerock.wego.service.LoginService;
import org.zerock.wego.service.OAuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@SessionAttributes({"__AUTH__"})
@RequestMapping("/login")
@Controller
public class LoginController 
implements InitializingBean {


	private final KakaoOAuth kakaoOAuth;
	private final LoginService loginService;
	private final OAuthService oAuthService;

	@Override
	public void afterPropertiesSet() throws ControllerException {
		log.trace("afterPropertiesSet() invoked");

		try {
			Objects.requireNonNull(this.kakaoOAuth);
			Objects.requireNonNull(this.loginService);
			Objects.requireNonNull(this.oAuthService);

		} 
		catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // afterPropertiesSet

	@GetMapping
	public String showLogin() throws ControllerException{
		log.trace("showLogin() invoked.");

		return "common/login";
	}// showLogin


//	@GetMapping("/kakao")
	public String getKakaoLogin() throws ControllerException{
		log.trace("getKakaoLogin() invoked.");

		try {
			StringBuffer redirectString = new StringBuffer("redirect:");

			redirectString.append(kakaoOAuth.getLoginURLToGetAuthorizationCode());

			return redirectString.toString();
		} catch (Exception e) {
			throw new ControllerException(e);
		}// try-catch
		
	}// getKakaoLogin
	
	@GetMapping("/kakao")
	public void getKakaoAuthUrl(HttpServletResponse response) throws IOException {
		log.trace("getKakaoAuthUrl({}) invoked.", response);
		
		response.sendRedirect(kakaoOAuth.getLoginURLToGetAuthorizationCode());
	} // getKakaoAuthUrl


	@GetMapping("/kakao/oauth")
	public String getKakaoCallBack(
			@RequestParam("code") String authorizationCode,
			Model model
			) throws ControllerException{
		log.trace("getAccessToken({}, model) invoked.", authorizationCode);

		try {
			UserDTO kakaoUserDTO = oAuthService.kakaoLogin(authorizationCode);
			// 수정 유저 인포 갖고와서 그걸로 다시 회원가입 되어 있는지 로그인 서비스에 전달하자

			log.info("userDTO = {}", kakaoUserDTO);

			UserVO kakaoOAuth = this.loginService.login(kakaoUserDTO);

			if(kakaoOAuth == null) {
				model.addAttribute("__AUTH__", kakaoUserDTO);
				
				return "redirect:/sign-up/";
			}
			else {
				model.addAttribute("__AUTH__", kakaoOAuth);
				
				return "redirect:/login/callback";
			}
		} catch (Exception e) {
			throw new ControllerException(e);
		}// try-catch

	}// getKakaoCallBack

	@GetMapping("/callback")
	public String getLoginCallBack(
			@SessionAttribute(value = "LOGIN_CALL_BACK_URI", required = false)Object loginCallBackURI
			) {
		log.trace("getLoginCallBack({}) invoked.", loginCallBackURI);

		if(loginCallBackURI == null) {

			return "redirect:/";
		} // if

		return "redirect:" + (String) loginCallBackURI;

	} // getLoginCallBack

}// end class

