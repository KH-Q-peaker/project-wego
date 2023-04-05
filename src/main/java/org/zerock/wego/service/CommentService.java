package org.zerock.wego.service;

import java.util.concurrent.LinkedBlockingDeque;

import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.domain.CommentDTO;
import org.zerock.wego.domain.CommentVO;
import org.zerock.wego.domain.Target;

public interface CommentService {
	
	// 더미
//	public abstract void garaComments() throws ServiceException;
	
	
	// 특정 게시글 댓글+멘션 조회
//	public abstract LinkedBlockingDeque<CommentVO> getAllCommentsByTarget(
//			@Param("targetGb")String targetGb, 
//			@Param("targetCd")Integer targetCd) throws ServiceException;
	
	// 타겟의 댓글 총합 개수 
	public abstract int getCommentsCount(Target cri) throws ServiceException;
	
	// 댓글 offset 조회 
	public abstract LinkedBlockingDeque<CommentVO> getCommentsOffsetByTarget(Target cri) throws ServiceException;
	
	// 댓글 코드로 조회 
	public abstract CommentVO getCommentByCommentId(Long commentId) throws ServiceException;
	
	
	// 삭제 분리...?
	public abstract boolean isCommentOrMentionRemove(Long commentId) throws ServiceException;
	
	// 댓글 삭제 
	public abstract boolean isCommentRemove(Long commentId) throws ServiceException;
	
	// 멘션 삭제 
	public abstract boolean isMentionRemove(Long commentId) throws ServiceException;

		
	// 댓글 작성 
	public abstract boolean isCommentRegister(CommentDTO dto) throws ServiceException;
	
	// 멘션 작성 
	public abstract boolean isMentionRegister(CommentDTO dto) throws ServiceException;
	
	
	
	// 댓글 수정 
	public abstract boolean isCommentModify(CommentDTO dto) throws ServiceException; 
	
	
}// end interface

