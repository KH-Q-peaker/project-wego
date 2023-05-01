package org.zerock.wego.mapper;

import java.util.concurrent.LinkedBlockingDeque;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.common.CommentDTO;
import org.zerock.wego.domain.common.CommentViewVO;
import org.zerock.wego.domain.common.PageInfo;

public interface CommentMapper {

	
	// 존재 여부 
	public abstract boolean isExist(@Param("commentId")Integer commentId);
	// 특정 글 댓글 총 개수 
	public abstract Integer selectTotalCountByTarget(@Param("targetGb")String targetGb, @Param("targetCd")Integer targetCd);
	
	
	// 특정 글 댓글 offset 조회 (5개씩) 
	public abstract LinkedBlockingDeque<CommentViewVO> selectCommentOffsetByTarget(@Param("target")PageInfo target, @Param("lastCommentId")Integer lastCommentId);
	// 특정 댓글 멘션 조회 
	public abstract LinkedBlockingDeque<CommentViewVO> selectMentionsByCommentId(Integer commentId);
	
	
	// 댓글 코드로 댓글 조회 
	public abstract CommentViewVO selectById(@Param("commentId")Integer commentId);
	
	// 특정 댓글 멘션 여부 조회 
	public abstract Integer hasMentionById(@Param("commentId")Integer commentId);
	
	
	// 댓글 코드로 댓글 삭제 
	public abstract Integer deleteById(@Param("commentId")Integer commentId);
	// 특정글 댓글 삭제 
	public abstract Integer deleteAllByTarget(@Param("targetGb")String targetGb, @Param("targetCd")Integer targetCd);
	
//	// 댓글 영구 삭제
//	public abstract Integer deleteDeadComment();
		

	// 댓글 작성 
	public abstract Integer insertComment(CommentDTO comment);
	
	// 댓글 수정 
	public abstract Integer updateComment(CommentDTO comment);
	
	
	// 타겟글 댓글수 증가  
	public abstract Integer updateTargetCommentCnt(@Param("comment")CommentDTO comment, @Param("act")String act);
	
}// end class
