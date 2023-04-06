package org.zerock.wego.controller;

import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.domain.UserDTO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.domain.oauth.kakao.KakaoOAuthTokenDTO;
import org.zerock.wego.domain.oauth.kakao.KakaoUserInfoDTO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.oauth.KakaoOAuth;
import org.zerock.wego.service.KakaoService;
import org.zerock.wego.service.UserLoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@SessionAttributes({"__AUTH__"})
@RequestMapping("/login")
@Controller
public class LoginController 
implements InitializingBean {


	private final KakaoService kakaoService;

	private final UserLoginService userLoginService;

	private final KakaoOAuth kakaoOAuth;

	@Override
	public void afterPropertiesSet() throws ControllerException {
		log.trace("afterPropertiesSet() invoked");

		try {
			Objects.requireNonNull(this.kakaoService);
			Objects.requireNonNull(this.userLoginService);
			Objects.requireNonNull(this.kakaoOAuth);
		} 
		catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // afterPropertiesSet
	
	@GetMapping("/")
	public String showLogin() throws ControllerException{
		log.trace("showLogin() invoked.");

		try {
			return "/common/login";
		} catch (Exception e) {
			throw new ControllerException(e);
		}// try-catch
	}// getCode


	@GetMapping("/kakao")
	public String getKakaoLogin() throws ControllerException{
		log.trace("getKakaoLogin() invoked.");

		try {
			StringBuffer redirectString = new StringBuffer("redirect:");

			redirectString.append(kakaoOAuth.getLoginURLToGetAuthorizationCode());

			return redirectString.toString();
		} catch (Exception e) {
			throw new ControllerException(e);
		}// try-catch
	}// getCode


	@GetMapping("/kakao/oauth")
	public String getKakaoCallBack(
			@RequestParam("code") String authorizationCode, 
			@SessionAttribute(value = "LOGIN_CALL_BACK_URI", required = false)Object loginCallBackURI,
			Model model
			) throws ControllerException{
		log.trace("getAccessToken({}, {}, model) invoked.", authorizationCode, loginCallBackURI);

		try {
			// access Token 받기
			KakaoOAuthTokenDTO kakaoToken = kakaoService.getAccessToken(authorizationCode);
			//			kakaoService.unlinkKakao(kakaoToken);

			// 카카오계정 정보 받기 
			KakaoUserInfoDTO userInfo = kakaoService.getUserInfo(kakaoToken);

			log.info("userInfo = {}", userInfo);

			String nickname = userInfo.getProperties().getNickname();
			String socialId = userInfo.getKakao_account().getEmail();

			log.info("\n::nickname = {}\n::socialId = {}", nickname, socialId);

			UserVO auth = this.userLoginService.getUserVOBySocialId(socialId);

			// 없는 이메일이면 == 회원아니면
			if(auth == null) {

				// 카카오 가입
				UserDTO user = new UserDTO();

				user.setNickname(nickname);
				user.setSocialId(socialId);

				this.userLoginService.signUp(user);

				auth = this.userLoginService.getUserVOBySocialId(socialId);
				// 가입하고 바로 로그인
				/// 추가 로직 고민 해봐야 
				// 모달을 띄운다 등
			}//if

			model.addAttribute("__AUTH__", auth);

			if(loginCallBackURI == null) {

				return "redirect:/";
			} // if

			return "redirect:" + (String) loginCallBackURI;

		} catch (Exception e) {
			throw new ControllerException(e);
		}// try-catch
	}// getCode



}// end class
