package org.zerock.wego.service;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.CommentDTO;
import org.zerock.wego.domain.CommentVO;
import org.zerock.wego.domain.Target;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.CommentMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Service
public class CommentServiceImpl implements CommentService {

	
	
	@Setter(onMethod_= {@Autowired})
	private CommentMapper commentMapper;

	
	// 댓글 총합 
	@Override
	public int getCommentsCount(Target cri) throws ServiceException {
		log.trace("getCommentsCount({}) invoked.", cri);
		
		
		try {
			int cnt = this.commentMapper.selectCommentCountByTarget(cri);
			
			return cnt;
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch 
	}// getCommentCnt

	
	// 댓글 offset 로딩
	@Override
	public LinkedBlockingDeque<CommentVO> getCommentsOffsetByTarget(Target cri) throws ServiceException{
		log.trace("getCommentsOffsetByTarget({}) invoked.", cri);
		
		try {
			LinkedBlockingDeque<CommentVO> comments = this.commentMapper.selectCommentsOffsetByTarget(cri);
			
			if(comments.isEmpty()) {
				
				return null;
			}else {
				
				return comments;
			}// if-else

		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// getCommentsOffsetByTarget

	
	// 댓글 코드로 조회 
	@Override
	public CommentVO getCommentByCommentId(Long commentId) throws ServiceException{
		log.trace("getCommentByCommentId({}}) invoked.", commentId);
		
		try {
			CommentVO comment = this.commentMapper.selectCommentByCommentId(commentId);
			Objects.requireNonNull(comment);
			
			
			return comment;

		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// getCommentsOffsetByTarget
	

	// 댓글 작성 
	@Override
	public boolean isCommentRegister(CommentDTO dto) throws ServiceException{
		log.trace("isCommentRegister({}) invoked", dto);
		
		try {

			return (this.commentMapper.insertComment(dto) == 1);
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// registerComment
	
	
	
	// 멘션 작성 
	@Override
	public boolean isMentionRegister(CommentDTO dto) throws ServiceException{
		log.trace("isMentionRegister({}) invoked", dto);
		
		try {

			return (this.commentMapper.insertMention(dto) == 1);
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// registerComment


	
	// 뭐를 삭제하냐....
	@Override
	public boolean isCommentOrMentionRemove(Long commentId) throws ServiceException {

 		boolean isRemove;
		CommentVO originComment = this.commentMapper.selectCommentByCommentId(commentId);
		 
		 
		 if(originComment.getCommentGb().equals("COMMENT")){
			 
			 isRemove = this.isCommentRemove(commentId);
		 }else {
			 
			 isRemove = this.isMentionRemove(commentId);
		 }// if-else
		 
		 
		return isRemove;
	}// isCommentOrMentionRemove
	
	
	// 댓글 삭제
	public boolean isCommentRemove(Long commentId) throws ServiceException {

		
		CommentDTO comment = new CommentDTO();
		comment.setCommentId(commentId);
		comment.setContents("삭제된 댓글입니다.");
		
		
		boolean isMentionExist = (this.commentMapper.selectMentionsByCommentId(commentId) != null);

		if (isMentionExist) {
			
			comment.setStatus("P");
			
		}else {
			comment.setStatus("Y");
		}// if-else
			
		
		return (this.commentMapper.updateComment(comment) == 1);
	}// removeComment
	
	
	// 멘션 삭제
	public boolean isMentionRemove(Long commentId) throws ServiceException {

		CommentDTO comment = new CommentDTO();
		comment.setCommentId(commentId);
		comment.setContents("삭제된 댓글입니다.");
		comment.setStatus("Y");

		
		boolean isUpdate = (this.commentMapper.updateComment(comment) == 1);
		
		
		if(isUpdate) {
			
		CommentVO originComment = this.commentMapper.selectCommentByCommentId(commentId);
		
		
		boolean isMentionExist = 
				(this.commentMapper.selectMentionsByCommentId(originComment.getMentionId()) != null);
		
			if (!isMentionExist) {
				
				comment.setCommentId(originComment.getMentionId());
				comment.setStatus("Y");
			
				isUpdate = (this.commentMapper.updateComment(comment) == 1);
			}// if
		}// if
		
		return isUpdate;
	}// removeComment

	
	
	// 댓글 수정 
	@Override
	public boolean isCommentModify(CommentDTO dto) throws ServiceException{
		log.trace("isCommentModify({}) invoked", dto);

		try {
			
			CommentVO originComment = this.commentMapper.selectCommentByCommentId(dto.getCommentId());
			
			dto.setStatus(originComment.getStatus());
			
			
			return this.commentMapper.updateComment(dto) == 1;
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// modifyComment

}// end class
