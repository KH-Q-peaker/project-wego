package org.zerock.wego.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Select;
import org.zerock.wego.domain.ReviewDTO;
import org.zerock.wego.domain.ReviewViewVO;


public interface ReviewMapper { 

	@Select("SELECT * FROM san_review_v ORDER BY created_dt DESC")
	public abstract List<ReviewViewVO> selectAll(); // 전체목록조회
	
	public abstract Set<ReviewViewVO> selectRandom10(); // 10개의 게시글 랜덤 조회
	public abstract ReviewViewVO select(Integer sanReviewId); // 상세조회
	public abstract Integer insert(ReviewDTO dto); // 신규게시물등록
	public abstract Integer update(ReviewDTO dto); // 기존게시물수정
	public abstract Integer delete(Integer sanReviewId); // 게시물삭제
	
} // end interface
