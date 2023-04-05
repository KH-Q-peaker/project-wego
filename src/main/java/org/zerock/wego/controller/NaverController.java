package org.zerock.wego.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.wego.domain.UserDTO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.NaverService;
import org.zerock.wego.service.UserService;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Controller
@RequestMapping("/naver")
public class NaverController {

	
	@Setter(onMethod_= {@Autowired})
	private NaverService naverService;
	
	@Setter(onMethod_= {@Autowired})
	private UserService userService;

	
	@GetMapping("/ddd")/*tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt*/
	public String login() {
		
		return "kakao/kakao";
	}
	
	
	@GetMapping("/login")
	public String getAccessToken(@RequestParam String code, @RequestParam String state, HttpSession session) throws ControllerException{
		log.trace("getAccessToken({}) invoked.", code);
		
		
		try {
			// access Token 받기
			String naverToken = naverService.getAccessToken(code, state);
			
			// 카카오계정 정보 받기 
			Map<String, String> userInfo = naverService.getUserInfo(naverToken);
			log.info("userInfo = {}", userInfo);
//			this.naverService.unlinkNaver(kakaoToken);
			

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
			
			session.setAttribute("userId", user_.getUserId());
			
			
			return "login/login";
		} catch (Exception e) {
			throw new ControllerException(e);
		}// try-catch
	}// getCode
}// end class
