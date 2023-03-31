package org.zerock.wego.mapper;

import java.util.concurrent.LinkedBlockingDeque;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.CommentDTO;
import org.zerock.wego.domain.CommentVO;
import org.zerock.wego.domain.Criteria;

public interface CommentMapper {


//	// 특정 게시글 댓글 조회 
//	public abstract LinkedBlockingDeque<CommentVO> selectCommentsByTarget(
//			@Param("targetGb")String targetGb, 
//			@Param("targetCd")Integer targetCd);
	// 댓글 총 개수 
	public abstract Integer selectCommentCnt(Criteria cri);
	
	// 댓글offset
	public abstract LinkedBlockingDeque<CommentVO> selectCommentsOffsetByTarget(@Param("cri")Criteria cri);

	// 댓글 코드로 댓글 조회 
	public abstract CommentVO selectCommentByCommentId(@Param("commentId")Integer commentId);
	
	// 특정 댓글 대댓글 여부 조회 
	public abstract Integer selectMentionsByComment(@Param("commentId")Integer commentId);
	
	// 특정 회원 댓글 조회 
	public abstract LinkedBlockingDeque<CommentVO> selectCommentByUser(@Param("userId") Integer userId);
	
	
	
	
	// 댓글 코드로 댓글 삭제 
	public abstract Integer deleteCommentByCommentId(@Param("commentId")Integer commentId);
	
	// 특정 유저 댓글 삭제 
	public abstract Integer deleteCommentByUserId(@Param("userCd")Integer userId);
	
	
	
	
	// 댓글 작성 
	public abstract Integer insertComment(CommentDTO comment);
	
	// 멘션 작성 
	public abstract Integer insertMention(CommentDTO comment);
	
	
	// 댓글 수정 
	public abstract Integer updateComment(CommentDTO comment);
	
	
}// end class
