package org.zerock.wego.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.review.ReviewDTO;
import org.zerock.wego.domain.review.ReviewViewVO;


public interface ReviewMapper {

	public abstract List<ReviewViewVO> selectAll();	// 전체 목록 조회	
	public abstract Set<ReviewViewVO> selectRandom10(); // 10개의 게시글 랜덤 조회
	
	// 존재여부 
	public abstract boolean isExist(@Param("reviewId")Integer reviewId);
	
	// 특정 후기글 조회 
	public abstract ReviewViewVO selectById(@Param("reviewId")Integer reviewId);
	
	public abstract Integer insert(ReviewDTO dto); // 신규게시물등록
	public abstract Integer update(ReviewDTO dto); // 기존게시물수정
	
	// 특정 후기글 삭제 
	public abstract Integer delete(@Param("reviewId")Integer reviewId);
	
	public abstract Integer visitCountUp(@Param("reviewId")Integer reviewId); // 조회수 반영하기
}// end interface
