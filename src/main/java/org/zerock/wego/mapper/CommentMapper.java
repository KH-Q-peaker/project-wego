package org.zerock.wego.mapper;

import java.util.concurrent.LinkedBlockingDeque;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.CommentDTO;
import org.zerock.wego.domain.CommentViewVO;
import org.zerock.wego.domain.PageInfo;

public interface CommentMapper {

	
	// 존재 여부 
	public abstract boolean isExist(@Param("commentId")Integer commentId);
	
	// 특정 글 댓글 총 개수 
	public abstract Integer selectTotalCountByTarget(PageInfo target);
	
	
	// 특정 글 댓글 offset 조회 (5개씩) 
//	public abstract LinkedBlockingDeque<CommentViewVO> selectCommentsOffsetByTarget(@Param("target")PageInfo target);
//	public abstract LinkedBlockingDeque<CommentViewVO> ttt(@Param("target")PageInfo target, @Param("lastComment")Integer lastComment);
	public abstract LinkedBlockingDeque<CommentViewVO> selectOnlyCommentOffsetByTarget(@Param("target")PageInfo target, @Param("lastCommentId")Integer lastCommentId);
	public abstract LinkedBlockingDeque<CommentViewVO> selectOnlyMentionsByCommentId(Integer commentId);
	
	
	// 댓글 코드로 댓글 조회 
	public abstract CommentViewVO selectById(@Param("commentId")Integer commentId);
	
	
	// 특정 댓글 멘션 여부 조회 
	public abstract Integer hasMentionById(@Param("commentId")Integer commentId);
	
	// 특정 회원 댓글 조회 
	//	public abstract LinkedBlockingDeque<CommentVO> selectCommentByUser(@Param("userId") Long userId);
	
	
	// 댓글 코드로 댓글 삭제 
	public abstract Integer deleteById(@Param("commentId")Integer commentId);
	
	// 댓글 영구 삭제
	public abstract Integer deleteDeadComment();
		
	// 특정 유저 댓글 삭제 
	//	public abstract Integer deleteCommentByUserId(@Param("userId")Long userId);
	
	
	
	
	// 댓글 작성 
	public abstract Integer insertComment(CommentDTO comment);
	
	// 멘션 작성 
	public abstract Integer insertMention(CommentDTO comment);
	
	
	// 댓글 수정 
	public abstract Integer updateComment(CommentDTO comment);
	
	
}// end class
