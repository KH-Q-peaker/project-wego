package org.zerock.wego.controller.mypage;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.wego.domain.mypage.WegoUserTbDTO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.mypage.MyInfoService;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Controller
@RequestMapping("/mypage")
public class MyinfoController {
	
	@Inject
	private MyInfoService service;
	
	@PostMapping("/infoset")
	public String infoSet(@RequestParam("user_id")String user_id, String address, 
			String san_range, String san_taste, Model model) throws ControllerException{
		log.trace("infoSet({},{},{},{}) invoked",user_id,address,san_range,san_taste);
		try {
			WegoUserTbDTO dto = new WegoUserTbDTO();
			dto.setUser_id(user_id);
			dto.setAddress(address);
			dto.setSan_range(san_range);
			dto.setSan_taste(san_taste);
			service.setMyinfo(dto);
			log.info("계정의 취향정보가 업데이트 되었습니다");
			
		}catch(Exception e) {
			throw new ControllerException(e);
		}
		return "redirect:mypage/myinfo?user_id=" + user_id;
	}//infoSet

	
	
}//end class
