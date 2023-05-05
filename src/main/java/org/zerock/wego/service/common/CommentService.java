package org.zerock.wego.service.common;

import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.common.CommentDTO;
import org.zerock.wego.domain.common.CommentViewVO;
import org.zerock.wego.domain.common.PageInfo;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Service
public class CommentService {

	private final CommentMapper commentMapper;
	private final NotificationService notificationService;

	
	// 댓글 총합
	public int getTotalCountByTarget(String targetGb, Integer targetCd) throws ServiceException {
//		log.trace("getCommentsCount({}) invoked.", target);

		try {
			int totalCount = this.commentMapper.selectTotalCountByTarget(targetGb, targetCd);

			return totalCount;

		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}// getCommentCnt
	
	
	// 댓글 offset 로딩
	public LinkedBlockingDeque<CommentViewVO> getCommentOffsetByTarget(PageInfo target, Integer lastCommentId)
			throws RuntimeException {
//		log.trace("getCommentsOffsetByTarget({}, {}) invoked.", target, lastCommentId);

		try {
			LinkedBlockingDeque<CommentViewVO> comments 
					= this.commentMapper.selectCommentOffsetByTarget(target, lastCommentId);

			if (comments.isEmpty()) {

				return null;
			} else {

				return comments;
			} // if-else

		} catch (RuntimeException e) {
			throw new ServiceException(e);
		} // try-catch
	}// getCommentsOffsetByTarget
		
	// 댓글의 멘션 전체 조회
	public LinkedBlockingDeque<CommentViewVO> getMentionsByCommentId(Integer commentId) throws RuntimeException {

			try {
				LinkedBlockingDeque<CommentViewVO> mentions 
							= this.commentMapper.selectMentionsByCommentId(commentId);
				
				return mentions;

			} catch (NullPointerException e) {
				return null;
				
			} catch (RuntimeException e) {
				throw new ServiceException(e);
			} // try-catch
		}// getCommentsOffsetByTarget

	
	// 댓글 코드로 조회 
	public CommentViewVO getById(Integer commentId) throws RuntimeException{
//		log.trace("getCommentByCommentId({}}) invoked.", commentId);
		
		try {
			CommentViewVO comment = this.commentMapper.selectById(commentId);
			
			return comment;
		} catch(NullPointerException e) {
			return null;
			
		} catch(RuntimeException e) {
			throw new ServiceException(e);
		}// try-catch
	}// getCommentsOffsetByTarget
	
	
	// 댓글/멘션 작성 
	public void registerCommentOrMention(CommentDTO dto) throws RuntimeException{
//		log.trace("isCommentRegister({}) invoked", dto);
		
		try {
			if(dto.getMentionId() != null) {
				dto.setCommentGb("MENTION");
			}// if-else 
			
			this.commentMapper.insertComment(dto);
			this.commentMapper.updateTargetCommentCnt(dto, "INSERT");
			
			
			this.notificationService.registerCommentNotification(dto);
			
		} catch(RuntimeException e) {
			throw new ServiceException(e);
		}// try-catch
	}// registerComment
	
	
	// 댓글/멘션 삭제
	public void removeCommentOrMention(Integer commentId) throws RuntimeException {
//		log.trace("removeCommentOrMention() invoked.");
		
		try {
			
			CommentViewVO originComment = this.commentMapper.selectById(commentId);
			
			if(originComment == null) {
				throw new NotFoundPageException();
			}// if

			if (originComment.getCommentGb().equals("COMMENT")) {

				this.removeComment(commentId);
			} else {

				this.removeMention(commentId);
			} // if-else
			
			CommentDTO dto = CommentDTO.convertCommentViewVOToCommentDTO(originComment);
			
			this.commentMapper.updateTargetCommentCnt(dto, "DELETE");
			
		} catch (NotFoundPageException e) {
			throw e;

		} catch (RuntimeException e) {
			throw new ServiceException(e);
			
		}// try-catch
	}// isCommentOrMentionRemove
	
	
	// 댓글 삭제
	public void removeComment(Integer commentId) throws RuntimeException {
//		log.trace("removeComment() invoked.");
		try {
			boolean isMentionExist = (this.commentMapper.hasMentionById(commentId) != null);
			
			if (isMentionExist) {

				CommentDTO comment = CommentDTO.builder()
												.commentId(commentId)
												.contents("삭제된 댓글입니다.")
												.status("Y").build();

				this.commentMapper.updateComment(comment);
				
			}else {
				this.commentMapper.deleteById(commentId);
			}// if-else 
			
		} catch (RuntimeException e) {
			throw new ServiceException(e);
		}// try-catch 
		
	}// removeComment
	
	
	// 멘션 삭제
	public void removeMention(Integer commentId) throws RuntimeException {
//		log.trace("removeMention() invoked.");

		try {
			CommentViewVO originComment = this.commentMapper.selectById(commentId);

			this.commentMapper.deleteById(commentId);
			
			
			CommentViewVO parentComment = this.commentMapper.selectById(originComment.getMentionId());
			
			if(parentComment.getStatus().equals("Y")) {
				
				boolean isMentionExist 
					= (this.commentMapper.hasMentionById(parentComment.getCommentId()) != null);
				
				if(!isMentionExist) {
					
					this.commentMapper.deleteById(parentComment.getCommentId());
				}// if
			}// if
		
		} catch (RuntimeException e) {
			throw new ServiceException(e);
		} // try-catch
	}// removeComment

	
	// 댓글 수정 
	public void modify(CommentDTO dto) throws RuntimeException{
//		log.trace("isModified({}) invoked", dto);

		try {
			CommentViewVO originComment = this.commentMapper.selectById(dto.getCommentId());
			
			if(originComment == null) {
				throw new NotFoundPageException();
			}// if
			
			dto.setStatus(originComment.getStatus());
			
			this.commentMapper.updateComment(dto); 
			
		}catch (RuntimeException e) {
			throw new ServiceException(e);
		}// try-catch
	}// modifyComment
	
	public void removeAllByTarget(String targetGb, Integer targetCd) throws RuntimeException{
		
		try {
			this.commentMapper.deleteAllByTarget(targetGb, targetCd);
			
		} catch(RuntimeException e) {
			throw new ServiceException(e);
		}// try-catch
	}// removeAllbyTarget
	
}// end class
