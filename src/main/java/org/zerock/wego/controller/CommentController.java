package org.zerock.wego.controller;

import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.config.SessionConfig;
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

@Controller
@RequestMapping("/comment")
public class CommentController {

	private final CommentService commentService;
	
	
	// 댓글 offset 로딩 
	@GetMapping(path="/load")
	String loadCommentOffset(PageInfo target, Integer lastComment, Model model) throws Exception{
		log.trace("loadCommentOffset(target, {}) invoked.", lastComment);
		
		
		LinkedBlockingDeque<CommentViewVO> comments = 
					this.commentService.getCommentOffsetByTarget(target, lastComment);
		
		int totalCnt = this.commentService.getTotalCountByTarget(target.getTargetGb(), target.getTargetCd());
		
		model.addAttribute("comments", comments);
		model.addAttribute("totalCnt", totalCnt);
		
		return "comment/load";
	}// loadCommentOffset

	// 댓글 멘션 로딩 
	@GetMapping(path="/mention")
	String loadMentionsByCommentId(CommentDTO dto, Model model) throws Exception{
		log.trace("loadMentionsByCommentId(comment) invoked");
		
		LinkedBlockingDeque<CommentViewVO> mentions = 
						this.commentService.getMentionsByCommentId(dto.getCommentId());
		
		int totalCnt = this.commentService.getTotalCountByTarget(dto.getTargetGb(), dto.getTargetCd());
		
		model.addAttribute("comments", mentions);
		model.addAttribute("totalCnt", totalCnt);
		
		return "comment/load";
	}// loadCommentOffset
	

	// 댓글 작성 
	@PostMapping(path="/register", produces= "text/plain; charset=UTF-8")
	ResponseEntity<String> registerComment(CommentDTO dto,
						@SessionAttribute(SessionConfig.AUTH_KEY_NAME) UserVO user,
						Model model) throws ControllerException{
		log.trace("registerComment(dto, user, model) invoked.");

		Integer userId = user.getUserId();
		dto.setUserId(userId);
		
		try {
			this.commentService.registerCommentOrMention(dto);

			return ResponseEntity.ok("댓글이 등록되었습니다.");
		} catch (NotFoundPageException e) {
			
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			throw new ControllerException(e);
		}// try-catch
	}// registerComment
	

	// 댓글 삭제 
	@DeleteMapping(path="/{commentId}", produces= "text/plain; charset=UTF-8")
	ResponseEntity<String> removeCommentOrMention(@PathVariable("commentId")Integer commentId) throws Exception{
		log.trace("removeComment({}) invoked.", commentId);
		
		try {
			this.commentService.removeCommentOrMention(commentId);
			
			return ResponseEntity.ok("댓글이 삭제되었습니다.");
			
		} catch(Exception e) {
			return ResponseEntity.notFound().build();
			
		}// try-catch
	}// registerComment
	
	
	// 댓글 수정 
	@PatchMapping(path="/{commentId}", produces= "text/plain; charset=UTF-8", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> modifyComment(@RequestBody CommentDTO dto) throws Exception{
		log.trace("modifyComment(dto) invoked.");
		
		try {
			this.commentService.modify(dto);

			return ResponseEntity.ok("댓글이 수정되었습니다.");

		} catch (Exception e) {
			return ResponseEntity.notFound().build();
			
		} // try-catch
	}// registerComment
	 
}// end class
