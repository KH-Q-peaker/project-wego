package org.zerock.wego.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.zerock.wego.domain.UserDTO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@SessionAttributes({"__AUTH__"})
@RequestMapping("/sign-up")
@Controller
public class SignUpController 
implements InitializingBean {

	private final LoginService loginService;

	
	@Override
	public void afterPropertiesSet() throws ControllerException {
		log.trace("afterPropertiesSet() invoked");

		try {

		} 
		catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // afterPropertiesSet
	

	@GetMapping("/")
	public String showSignUp() throws ControllerException{
		log.trace("showSignUp() invoked.");

		return "common/signup";
	}// showSignUp
	
	@GetMapping("/confirm")
	public String showCallBackURI() {
		
		return "redirect:/login/callback";
	} // showCallBackURI

	@PostMapping("/confirm")
	public void signUp(
			@SessionAttribute(value = "__AUTH__", required = false)Object signUpUserInfo,
			Model model
			) throws ControllerException{
		log.trace("showSignUp({}) invoked.", signUpUserInfo);
		
		UserDTO signUpUserDTO = (UserDTO) signUpUserInfo;
		
		loginService.signUp(signUpUserDTO);
		
		UserVO auth = loginService.login(signUpUserDTO);
		
		model.addAttribute("__AUTH__", auth);
	}// showSignUp

}// end class

