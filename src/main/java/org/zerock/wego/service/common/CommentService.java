package org.zerock.wego.service.common;

import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.common.CommentDTO;
import org.zerock.wego.domain.common.CommentViewVO;
import org.zerock.wego.domain.common.PageInfo;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.exception.OperationFailException;
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
	public int getTotalCountByTarget(PageInfo target) throws ServiceException {
//		log.trace("getCommentsCount({}) invoked.", target);
		
		try {
			int totalCount = this.commentMapper.selectTotalCountByTarget(target);

			return totalCount;

		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}// getCommentCnt

	// 댓글 offset 로딩
	public LinkedBlockingDeque<CommentViewVO> getCommentOffsetByTarget(PageInfo target, Integer lastCommentId)
			throws ServiceException {
//		log.trace("getCommentsOffsetByTarget({}, {}) invoked.", target, lastCommentId);

		try {
			LinkedBlockingDeque<CommentViewVO> comments 
					= this.commentMapper.selectCommentOffsetByTarget(target, lastCommentId);

			if (comments.isEmpty()) {

				return null;
			} else {

				return comments;
			} // if-else

		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}// getCommentsOffsetByTarget
		
	// 댓글의 멘션 전체 조회
	public LinkedBlockingDeque<CommentViewVO> getMentionsByCommentId(Integer commentId) throws ServiceException {

			try {
				LinkedBlockingDeque<CommentViewVO> mentions 
							= this.commentMapper.selectMentionsByCommentId(commentId);

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
//		log.trace("getCommentByCommentId({}}) invoked.", commentId);
		
		try {
			CommentViewVO comment = this.commentMapper.selectById(commentId);
			
			return comment;

		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// getCommentsOffsetByTarget
	
	
	// 댓글 작성 합치는게 낫나 
	public void registerCommentOrMention(CommentDTO dto) throws Exception{
//		log.trace("isCommentRegister({}) invoked", dto);
		
		try {
			if(dto.getMentionId() == null) {
				
				this.commentMapper.insertComment(dto); 
			}else {
				
				this.commentMapper.insertMention(dto);
			}// if-else 
		
			boolean isExist 
				= (this.commentMapper.selectById(dto.getCommentId()) != null);
			
			if(!isExist) {
				throw new OperationFailException();
			}// if
			
		} catch(OperationFailException e) {
			throw e;
			
		}catch(Exception e) {
			throw new ServiceException(e);
			
		}// try-catch
	}// registerComment
	
	
	// 뭐를 삭제하냐....
	public void removeCommentOrMention(Integer commentId) throws ServiceException {
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
			
		} catch (OperationFailException e) {
			throw e;

		} catch (Exception e) {
			throw new ServiceException(e);
			
		}// try-catch
	}// isCommentOrMentionRemove
	
	
	// 댓글 삭제
	public void removeComment(Integer commentId) throws Exception {
//		log.trace("removeComment() invoked.");
		try {

			CommentDTO comment = new CommentDTO();
			comment.setCommentId(commentId);
			comment.setContents("삭제된 댓글입니다.");

			boolean isExist = (this.commentMapper.selectById(commentId) != null);

			if (!isExist) {
				throw new NotFoundPageException();
			} // if

			boolean isMentionExist = (this.commentMapper.hasMentionById(commentId) != null);

			if (isMentionExist) {

				comment.setStatus("P");
			} else {

				comment.setStatus("Y");
			} // if-else

			this.commentMapper.updateComment(comment);
			
		} catch (NotFoundPageException e) {
			throw e;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}// try-catch 
		
	}// removeComment
	
	
	// 멘션 삭제
	public void removeMention(Integer commentId) throws ServiceException {
//		log.trace("removeMention() invoked.");

		try {
			CommentDTO comment = new CommentDTO();
			comment.setCommentId(commentId);
			comment.setContents("삭제된 댓글입니다.");
			comment.setStatus("Y");

			CommentViewVO originComment = this.commentMapper.selectById(commentId);

			if (originComment == null) {
				throw new NotFoundPageException();
			} // if

			this.commentMapper.updateComment(comment);

			boolean isMentionExist = (this.commentMapper.hasMentionById(originComment.getMentionId()) != null);

			if (!isMentionExist) {

				comment.setCommentId(originComment.getMentionId());
				comment.setStatus("Y");

				this.commentMapper.updateComment(comment);
			} // if
			
		} catch (NotFoundPageException e) {
			throw e;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}// removeComment

	
	
	// 댓글 수정 
	public void modify(CommentDTO dto) throws ServiceException{
//		log.trace("isModified({}) invoked", dto);

		try {
			CommentViewVO originComment = this.commentMapper.selectById(dto.getCommentId());
			
			if(originComment == null) {
				throw new NotFoundPageException();
			}// if
			
			dto.setStatus(originComment.getStatus());
			
			this.commentMapper.updateComment(dto); 
			
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
