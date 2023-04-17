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
import org.zerock.wego.domain.common.CommentDTO;
import org.zerock.wego.domain.common.CommentViewVO;
import org.zerock.wego.domain.common.PageInfo;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.exception.OperationFailException;
import org.zerock.wego.service.common.CommentService;

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
		log.trace("loadCommentOffset({}, {}) invoked.", target, lastComment);
		
		
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
		log.trace("registerComment() invoked.");
		
		ModelAndView mav = new ModelAndView();

		
		PageInfo target = new PageInfo();
		target.setTargetGb(dto.getTargetGb());
		target.setTargetCd(dto.getTargetCd());
		
		Integer userId = user.getUserId();
		dto.setUserId(userId);
		
		
		try {
			this.commentService.registerCommentOrMention(dto);

			LinkedBlockingDeque<CommentViewVO> comments 
						= this.commentService.getCommentOffsetByTarget(target, 0);

			mav.addObject("comments", comments);
			mav.setViewName("comment/comment");

			return mav;

		} catch (OperationFailException | NotFoundPageException e) {
			throw e;
			
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
			this.commentService.registerCommentOrMention(dto);
			
			CommentViewVO comment = this.commentService.getById(dto.getCommentId());

			mav.addObject("comment", comment);
			
			
			return mav;

		} catch (Exception e) {
			throw new ControllerException(e);
		}// try-catch
	}// registerComment
	
	
	// 댓글 삭제 
//	@PostMapping("/remove")
	@DeleteMapping(path="/{commentId}")
	ResponseEntity<String> removeCommentOrMention(@PathVariable("commentId")Integer commentId) throws Exception{
//		log.trace("removeComment({}) invoked.", commentId);
		
		try {
			this.commentService.removeCommentOrMention(commentId); 
		
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.notFound().build();
			
		}// try-catch
	}// registerComment
	
	
	// 댓글 수정 
//	@PostMapping("/modify")
//	@PatchMapping("/{commentId}")
//	ResponseEntity<String> modifyComment(@PathVariable("commentId")Integer commentId, 
//										 @RequestBody String contents) throws Exception{
	@PatchMapping(path="/{commentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> modifyComment(@RequestBody CommentDTO dto) throws Exception{
//		log.trace("modifyComment({}) invoked.", dto);
		
		try {

			this.commentService.modify(dto);

			return ResponseEntity.ok().build();

		} catch (Exception e) {
			return ResponseEntity.notFound().build();
			
		} // try-catch
	}// registerComment
	 
}// end class
