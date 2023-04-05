package org.zerock.wego.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.domain.Target;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.LikeService;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Controller
@RequestMapping("/like")
public class LikeController {

	@Setter(onMethod_= {@Autowired})
	private LikeService likeService;
	
	
	// 좋아요
	@GetMapping("/toggle")
	public ResponseEntity<String> toggleLike(Target cri, @SessionAttribute("__AUTH__")Long userId) throws ControllerException{
		
		try {
			
			this.likeService.isToggleLike(cri, userId);
			
			String likeCnt = this.likeService.getLikeCnt(cri).toString();
			
			return ResponseEntity.ok().body(likeCnt);
			
		} catch(Exception e) {
			throw new ControllerException(e);
		}// try-catch
	}// toggleLike
	
}// end class
