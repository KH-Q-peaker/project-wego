package org.zerock.wego.controller;

import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.domain.CommentDTO;
import org.zerock.wego.domain.CommentVO;
import org.zerock.wego.domain.Target;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.CommentService;
import org.zerock.wego.service.UserService;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Controller
//@SessionAttributes("userId")
@RequestMapping("/comment")
public class CommentController {

	@Setter(onMethod_= {@Autowired})
	private CommentService commentService;
	
	@Setter(onMethod_= {@Autowired})
	private UserService userService;
	
	
	// 댓글 offset 로딩 
	@GetMapping("/load")
	ModelAndView loadCommentOffset(Target cri) throws Exception{
		
		ModelAndView mav = new ModelAndView();

		LinkedBlockingDeque<CommentVO> comments = 
				this.commentService.getCommentsOffsetByTarget(cri);
		
		if(comments == null) {
			
			return null;
		}
		mav.addObject("comments", comments);

		return mav;
	}// loadcomment
	
	
	
	
	// 댓글 작성 
	@PostMapping("/register")
	ModelAndView registerComment(CommentDTO dto, @SessionAttribute("__AUTH__") Long userId) throws ControllerException{
		log.trace("registerComment({}) invoked.", dto);

		ModelAndView mav = new ModelAndView();

		Target cri = new Target();
		cri.setTargetGb(dto.getTargetGb());
		cri.setTargetCd(dto.getTargetCd());

		dto.setUserId(userId);

		
		try {
			this.commentService.isCommentRegister(dto);
			LinkedBlockingDeque<CommentVO> comments = this.commentService.getCommentsOffsetByTarget(cri);

			mav.addObject("comments", comments);
			mav.setViewName("comment/comment");

			return mav;
		} catch (Exception e) {
			throw new ControllerException(e);
		}
			
	}// registerComment
	
	

	
	// 멘션 작성 
	@PostMapping("/reply")
	ModelAndView registerMention(CommentDTO dto, @SessionAttribute("__AUTH__") Long userId) throws ControllerException{

		ModelAndView mav = new ModelAndView();

		Target cri = new Target();
		cri.setTargetGb(dto.getTargetGb());
		cri.setTargetCd(dto.getTargetCd());

		dto.setUserId(userId);

		try {
			this.commentService.isMentionRegister(dto);
			
			CommentVO comment = this.commentService.getCommentByCommentId(dto.getCommentId());

			mav.addObject("c", comment);

			return mav;
		} catch (Exception e) {
			throw new ControllerException(e);
		}
			
	}// registerComment
	
	
	
	
	// 댓글 삭제 
//	@PostMapping("/remove")
	@DeleteMapping("/{commentId}")
	ResponseEntity<String> removeComment(@PathVariable("commentId")Long commentId) throws Exception{
		log.trace("removeComment({}) invoked.", commentId);
		
		
		if(this.commentService.isCommentOrMentionRemove(commentId)) {
			
			return new ResponseEntity<>("OK", HttpStatus.OK);
		}// if
		
		return new ResponseEntity<String>("XX", HttpStatus.NOT_FOUND);
	}// registerComment
	
	
	
	// 댓글 수정 
//	@PostMapping("/modify")
//	@PatchMapping("/{commentId}")
//	ResponseEntity<String> modifyComment(@PathVariable("commentId")Integer commentId, 
//										 @RequestBody String contents) throws Exception{
	@PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> modifyComment(@RequestBody CommentDTO dto) throws Exception{
		log.trace("modifyComment({}) invoked.", dto);
		
		if(this.commentService.isCommentModify(dto)) {
			
			return new ResponseEntity<>("OK", HttpStatus.OK);
		}// if
		
		return new ResponseEntity<String>("XX", HttpStatus.NOT_FOUND);
	}// registerComment
	 

}// end class
