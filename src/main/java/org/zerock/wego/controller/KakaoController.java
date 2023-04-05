package org.zerock.wego.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.wego.domain.UserDTO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.KakaoService;
import org.zerock.wego.service.UserService;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Controller
@RequestMapping("/kakao")
public class KakaoController {

	
	@Setter(onMethod_= {@Autowired})
	private KakaoService kakaoService;
	
	@Setter(onMethod_= {@Autowired})
	private UserService userService;

	
	@GetMapping("/ddd")
	public String login(RedirectAttributes rttrs) {
		
		return "kakao/kakao";
	}
	@GetMapping("/login")
	public String getAccessToken(@RequestParam String code, HttpSession session) throws ControllerException{
		log.trace("getAccessToken({}) invoked.", code);
		
		
		try {
			String kakaoToken = kakaoService.getAccessToken(code);
//			kakaoService.unlinkKakao(kakaoToken);
			
			Map<String, String> userInfo = kakaoService.getUserInfo(kakaoToken);
			log.info("userInfo = {}", userInfo);
			
			
			String nickname = userInfo.get("nickname");
			String socialId = userInfo.get("email"); // HASH 해야됨? 
			log.info("\n::nickname = {}\n::socialId = {}", nickname, socialId);
			
			UserVO user_ = this.userService.socialLogin(socialId);
			

			if(user_ == null) {
				
				UserDTO user = new UserDTO();
				
				user.setNickname(nickname);
				user.setSocialId(socialId);
				
				this.userService.socialJoin(user);
				user_ = this.userService.socialLogin(socialId);
			}//if
			
			session.setAttribute("__AUTH__", user_.getUserId());
			
			
//			return "login/login";
			return "redirect:"+ session.getAttribute("requestURL").toString(); 
		} catch (Exception e) {
			throw new ControllerException(e);
		}// try-catch
	}// getCode
}// end class
