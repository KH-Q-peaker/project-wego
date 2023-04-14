package org.zerock.wego.service;

import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.CommentDTO;
import org.zerock.wego.domain.CommentViewVO;
import org.zerock.wego.domain.PageInfo;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Service
public class CommentService {

	private final CommentMapper commentMapper;

	
	// 댓글 총합 
	public int getCommentsCount(PageInfo target) throws ServiceException {
		log.trace("getCommentsCount({}) invoked.", target);
		
		
		try {
			int totalCount = this.commentMapper.selectTotalCountByTarget(target);

			return totalCount;

		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}// getCommentCnt

	
//	// 댓글 offset 로딩
//	public LinkedBlockingDeque<CommentViewVO> getCommentsOffsetByTarget(PageInfo target 
//											) throws ServiceException{
//		log.trace("getCommentsOffsetByTarget({}) invoked.", target);
//		
//		try {
//			LinkedBlockingDeque<CommentViewVO> comments 
//					= this.commentMapper.selectCommentsOffsetByTarget(target);
//			
//			if(comments.isEmpty()) {
//				
//				return null;
//			}else {
//				
//				return comments;
//			}// if-else
//
//		}catch(Exception e) {
//			throw new ServiceException(e);
//		}// try-catch
//	}// getCommentsOffsetByTarget
	
	// 댓글 offset 로딩
		public LinkedBlockingDeque<CommentViewVO> getCommentOffsetByTarget(PageInfo target, Integer lastCommentId) throws ServiceException{
			log.trace("getCommentsOffsetByTarget({}, {}) invoked.", target, lastCommentId);
			
			try {
				LinkedBlockingDeque<CommentViewVO> comments 
						= this.commentMapper.selectOnlyCommentOffsetByTarget(target, lastCommentId);
				
				if(comments.isEmpty()) {
					
					return null;
				}else {
					
					return comments;
				}// if-else

			}catch(Exception e) {
				throw new ServiceException(e);
			}// try-catch
		}// getCommentsOffsetByTarget
		
		
		
		// 댓글의 멘션 전체 조회
		public LinkedBlockingDeque<CommentViewVO> getMentionsByCommentId(Integer commentId) throws ServiceException {

			try {
				LinkedBlockingDeque<CommentViewVO> mentions 
							= this.commentMapper.selectOnlyMentionsByCommentId(commentId);

				if (mentions.isEmpty()) {

					return null;
				} else {

					return mentions;
				} // if-else

			} catch (Exception e) {
				throw new ServiceException(e);
			} // try-catch
		}// getCommentsOffsetByTarget

	
	// 댓글 코드로 조회 
	public CommentViewVO getById(Integer commentId) throws ServiceException{
		log.trace("getCommentByCommentId({}}) invoked.", commentId);
		
		try {
			CommentViewVO comment = this.commentMapper.selectById(commentId);
			
			
			return comment;

		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// getCommentsOffsetByTarget
	

	// 댓글 작성 
	public boolean isCommentRegister(CommentDTO dto) throws ServiceException{
		log.trace("isCommentRegister({}) invoked", dto);
		
		try {
			boolean isRegister = (this.commentMapper.insertComment(dto) == 1); 
		
			return isRegister;
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// registerComment
	
	
	
	// 멘션 작성 
	public boolean isMentionRegister(CommentDTO dto) throws ServiceException{
		log.trace("isMentionRegister({}) invoked", dto);
		
		try {
			boolean isRegister = (this.commentMapper.insertMention(dto) == 1);
			
			return isRegister;
			
		} catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// registerComment


	
	// 뭐를 삭제하냐....
	public boolean isCommentOrMentionRemove(Integer commentId) throws ServiceException {

 		boolean isRemove;
 		
		CommentViewVO originComment = this.commentMapper.selectById(commentId);
		 
		 
		 if(originComment.getCommentGb().equals("COMMENT")){
			 
			 isRemove = this.isCommentRemove(commentId);
		 } else {
			 
			 isRemove = this.isMentionRemoved(commentId);
		 }// if-else
		 
		 
		return isRemove;
	}// isCommentOrMentionRemove
	
	
	// 댓글 삭제
	public boolean isCommentRemove(Integer commentId) throws ServiceException {

		
		CommentDTO comment = new CommentDTO();
		comment.setCommentId(commentId);
		comment.setContents("삭제된 댓글입니다.");
		
		
		boolean isMentionExist = (this.commentMapper.hasMentionById(commentId) != null);

		if (isMentionExist) {
			
			comment.setStatus("P");
			
		}else {
			comment.setStatus("Y");
		}// if-else
			
		
		return (this.commentMapper.updateComment(comment) == 1);
	}// removeComment
	
	
	// 멘션 삭제
	public boolean isMentionRemoved(Integer commentId) throws ServiceException {

		CommentDTO comment = new CommentDTO();
		comment.setCommentId(commentId);
		comment.setContents("삭제된 댓글입니다.");
		comment.setStatus("Y");

		
		boolean isUpdate = (this.commentMapper.updateComment(comment) == 1);
		
		
		if(isUpdate) {
			
		CommentViewVO originComment = this.commentMapper.selectById(commentId);
		
		
		boolean isMentionExist = 
				(this.commentMapper.hasMentionById(originComment.getMentionId()) != null);
		
			if (!isMentionExist) {
				
				comment.setCommentId(originComment.getMentionId());
				comment.setStatus("Y");
			
				isUpdate = (this.commentMapper.updateComment(comment) == 1);
			}// if
		}// if
		
		return isUpdate;
	}// removeComment

	
	
	// 댓글 수정 
	public boolean isModify(CommentDTO dto) throws ServiceException{
//		log.trace("isModified({}) invoked", dto);

		try {
			CommentViewVO originComment = this.commentMapper.selectById(dto.getCommentId());
			
			dto.setStatus(originComment.getStatus());
			
			boolean isUpdate = (this.commentMapper.updateComment(dto) == 1); 
			
			return isUpdate; 
			
		}catch (Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// modifyComment

	// 댓글 영구 삭제
	@Async
	@Scheduled(fixedRate = 100000)
	public boolean isClear() throws ServiceException{
		
		try {
			boolean isClear = (this.commentMapper.deleteDeadComment() == 1);
			/*생각해보니까  이거 아닌데...? 뭐어차피 쿼츠 배우면 바꿀꺼니까 */
			
			return isClear;
			
		}catch (Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// isCleared
	
}// end class
