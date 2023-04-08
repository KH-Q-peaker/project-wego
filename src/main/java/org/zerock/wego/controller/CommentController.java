package org.zerock.wego.controller;

import java.util.concurrent.LinkedBlockingDeque;

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
import org.zerock.wego.domain.CommentViewVO;
import org.zerock.wego.domain.PageInfo;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Controller
@RequestMapping("/comment")
public class CommentController {

	private final CommentService commentService;
	
	
	// 댓글 offset 로딩 
	@GetMapping("/load")
	ModelAndView loadCommentOffset(PageInfo target) throws Exception{
		
		ModelAndView mav = new ModelAndView();

		LinkedBlockingDeque<CommentViewVO> comments = 
					this.commentService.getCommentsOffsetByTarget(target);
		
		if(comments == null) {
			
			return null;
		}
		mav.addObject("comments", comments);

		return mav;
	}// loadCommentOffset
	
	
	
	
	// 댓글 작성 
	@PostMapping("/register")
	ModelAndView registerComment(CommentDTO dto, 
								@SessionAttribute("__AUTH__") UserVO user) throws ControllerException{
		log.trace("registerComment({}) invoked.", dto);

		ModelAndView mav = new ModelAndView();

		PageInfo target = new PageInfo();
		target.setTargetGb(dto.getTargetGb());
		target.setTargetCd(dto.getTargetCd());

		Integer userId = user.getUserId();
		dto.setUserId(userId);

		
		try {
			boolean isRegistered = this.commentService.isCommentRegistered(dto);
			
			if(isRegistered) {
				
				LinkedBlockingDeque<CommentViewVO> comments = this.commentService.getCommentsOffsetByTarget(target);

				mav.addObject("comments", comments);
				mav.setViewName("comment/comment");
				
				return mav;
			}else {
				return null; // ..?
			}// if-else
		} catch (Exception e) {
			throw new ControllerException(e);
		}// try-catch
	}// registerComment
	
	

	
	// 멘션 작성 
	@PostMapping("/reply")
	ModelAndView registerMention(CommentDTO dto, 
								@SessionAttribute("__AUTH__") UserVO user) throws ControllerException{

		ModelAndView mav = new ModelAndView();

		PageInfo target = new PageInfo();
		target.setTargetGb(dto.getTargetGb());
		target.setTargetCd(dto.getTargetCd());

		Integer userId = user.getUserId();
		dto.setUserId(userId);

		try {
			this.commentService.isMentionRegister(dto);
			
			CommentViewVO comment = this.commentService.getById(dto.getCommentId());

			mav.addObject("comment", comment);

			return mav;
		} catch (Exception e) {
			throw new ControllerException(e);
		}
			
	}// registerComment
	
	
	
	
	// 댓글 삭제 
//	@PostMapping("/remove")
	@DeleteMapping("/{commentId}")
	ResponseEntity<String> removeCommentOrMention(@PathVariable("commentId")Integer commentId) throws Exception{
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
		
		if(this.commentService.isModified(dto)) {
			
			return new ResponseEntity<>("OK", HttpStatus.OK);
		}// if
		
		return new ResponseEntity<String>("XX", HttpStatus.NOT_FOUND);
	}// registerComment
	 
}// end class
