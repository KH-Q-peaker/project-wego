package org.zerock.wego.controller;

import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

//@Controller
@RestController
@RequestMapping("/comment")
public class CommentController {

	private final CommentService commentService;
	
	
	// 댓글 offset 로딩 
	@GetMapping(path="/load")
	ModelAndView loadCommentOffset(PageInfo target, Integer lastComment) throws Exception{
//		log.trace("loadCommentOffset({}, {}) invoked.", target, lastComment);
		
		
		ModelAndView mav = new ModelAndView();
		
		LinkedBlockingDeque<CommentViewVO> comments = 
					this.commentService.getCommentOffsetByTarget(target, lastComment);
		
		if(comments == null) {
			
			return null;
		}
		mav.addObject("comments", comments);

		return mav;
	}// loadCommentOffset

	// 댓글 멘션 로딩 
	@GetMapping(path="/mention")
	ModelAndView loadMentionsByCommentId(Integer commentId) throws Exception{
		log.trace("loadMentionsByCommentId({}) invoked", commentId);
		
				
		ModelAndView mav = new ModelAndView();
		
		LinkedBlockingDeque<CommentViewVO> mentions = 
					this.commentService.getMentionsByCommentId(commentId);
		
		if(mentions == null) {
			
			return null;
		}
		mav.addObject("comments", mentions);
		mav.setViewName("comment/load");

		return mav;
	}// loadCommentOffset
	

	// 댓글 작성 
	@PostMapping(path="/register")
	ModelAndView registerComment(@RequestBody CommentDTO dto, 
								@SessionAttribute("__AUTH__") UserVO user) throws ControllerException{
//		log.trace("registerComment({}) invoked.", dto);

		ModelAndView mav = new ModelAndView();

		PageInfo target = new PageInfo();
		target.setTargetGb(dto.getTargetGb());
		target.setTargetCd(dto.getTargetCd());

		
		Integer userId = user.getUserId();
		dto.setUserId(userId);
		
		try {
			boolean isRegistered = this.commentService.isCommentRegister(dto);
			
			if(isRegistered) {
				
				LinkedBlockingDeque<CommentViewVO> comments 
							= this.commentService.getCommentOffsetByTarget(target, 0);

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
	@PostMapping(path="/reply")
	ModelAndView registerMention(@RequestBody CommentDTO dto, 
								@SessionAttribute("__AUTH__") UserVO user) throws ControllerException{
//		log.trace("registerMention({}, {}) invoked.", dto, user);
		
		ModelAndView mav = new ModelAndView();

		Integer userId = user.getUserId();
		dto.setUserId(userId);

		try {
			boolean isRegister = this.commentService.isMentionRegister(dto);
			
			if(isRegister) {
				
				CommentViewVO comment = this.commentService.getById(dto.getCommentId());

				mav.addObject("comment", comment);
				return mav;
			}else {
				return null;
			}
		} catch (Exception e) {
			throw new ControllerException(e);
		}// try-catch
	}// registerComment
	
//	@PostMapping(path="/reply",
//				 produces=MediaType.APPLICATION_JSON_VALUE)
//	CommentViewVO registerMention(@RequestBody CommentDTO dto, 
//								 @SessionAttribute("__AUTH__") UserVO user) throws ControllerException{
////		log.trace("registerMention({}, {}) invoked.", dto, user);
//		
//		Integer userId = user.getUserId();
//		dto.setUserId(userId);
//
//		try {
//			this.commentService.isMentionRegister(dto);
//			
//			CommentViewVO comment = this.commentService.getById(dto.getCommentId());
//
//			return comment;
//		} catch (Exception e) {
//			throw new ControllerException(e);
//		}// try-catch
//	}// registerComment
	
	// 댓글 삭제 
//	@PostMapping("/remove")
	@DeleteMapping(path="/{commentId}")
	ResponseEntity<String> removeCommentOrMention(@PathVariable("commentId")Integer commentId) throws Exception{
//		log.trace("removeComment({}) invoked.", commentId);
		
		boolean isRemove = this.commentService.isCommentOrMentionRemove(commentId); 
		
		if(isRemove) {
			
			return ResponseEntity.ok().build();
		}// if
		
		return ResponseEntity.notFound().build();
	}// registerComment
	
	
	// 댓글 수정 
//	@PostMapping("/modify")
//	@PatchMapping("/{commentId}")
//	ResponseEntity<String> modifyComment(@PathVariable("commentId")Integer commentId, 
//										 @RequestBody String contents) throws Exception{
	@PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> modifyComment(@RequestBody CommentDTO dto) throws Exception{
//		log.trace("modifyComment({}) invoked.", dto);
		
		boolean isModify = this.commentService.isModify(dto);
		
		if(isModify) {
			
			return ResponseEntity.ok().build();
		}// if
		
		return ResponseEntity.notFound().build();
	}// registerComment
	 
}// end class
