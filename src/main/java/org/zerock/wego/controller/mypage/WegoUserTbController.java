package org.zerock.wego.controller.mypage;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.wego.domain.mypage.WegoUserTbDTO;
import org.zerock.wego.domain.mypage.WegoUserTbVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.service.mypage.ProfileUploadService;
import org.zerock.wego.service.mypage.WegoUserTbService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor

@RequestMapping("/mypage")
@Controller

public class WegoUserTbController {
		
	private WegoUserTbService service;
	private ProfileUploadService service1;
	
	@GetMapping({"/mypage","/mypage/myinfo"})
	public String search(@RequestParam("user_id") String user_id, Model model) throws ServiceException {
		log.trace("search({},{}) invoked ",user_id, model);
		try {
			WegoUserTbVO vo = service.search(user_id);
			String path = service1.selectProfilePath(user_id);
			if(! ( path == null || path.isEmpty())) {
				String [] pathArray = path.split("profileImage");
				log.info("pathArray[1]: {}", pathArray[1]);
				model.addAttribute("profileImageNextpath",pathArray[1]);
				log.info(path);
			}
			model.addAttribute("vo",vo);
			
			return "/mypage/mypage";
		}catch(Exception e) {
			throw new ServiceException(e);
		}//try-catch
	}
	
	
	@GetMapping("/myinfo")
	public String selectInfo(@RequestParam("user_id") String user_id, Model model) throws ServiceException {
		log.trace("selectInfo({},{}) invoked ",user_id, model);
		try {
			WegoUserTbVO vo = service.search(user_id);
			String path = service1.selectProfilePath(user_id);
			if(! ( path == null || path.isEmpty())) {
				String [] pathArray = path.split("profileImage");
				log.info("pathArray[1]: {}", pathArray[1]);
				model.addAttribute("profileImageNextpath",pathArray[1]);
				log.info(path);
			}
			model.addAttribute("vo",vo);
			
			return "mypage/myinfo";
		}catch(Exception e) {
			throw new ServiceException(e);
		}//try-catch
	}//selectInfo
	
	@GetMapping("/mypage/changeNick")
	public String changeNick(
			@RequestParam("user_id")String user_id, @RequestParam("nickname") String nickname, Model model,HttpServletRequest req) throws ServiceException {
		
		try {
			WegoUserTbDTO dto = new WegoUserTbDTO();
			dto.setUser_id(user_id);
			dto.setNickname(nickname);
			model.addAttribute("dto",dto);
			boolean is = service.modifyNick(dto);
			StringBuffer buffer = req.getRequestURL();
			String requrl = buffer.toString();
			log.info("~~~~~~~~!!! req url:{}",requrl);
			
			//ajax 닉네임jsp로 모델도 같이 전달
			return "/mypage/nickname";
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}//try-catch
	}
	
	
}//end class
