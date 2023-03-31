package org.zerock.wego.mapper;

import java.util.concurrent.LinkedBlockingDeque;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.ReviewVO;

public interface ReviewMapper {

	
//	@Select({"SELECT * FROM san_review_v"})
//	public LinkedBlockingDeque<ReviewVO> selectAllReviews();
//	
//	@Select({"SELECT * FROM san_review_v WHERE review_cd = #{reviewCd}"})
//	public ReviewVO selectReview(@Param("reviewCd") Integer reviewCd);
	
	// 후기글 전체 조회 
	public abstract LinkedBlockingDeque<ReviewVO> selectAllReviews();
	
	// 특정 후기글 조회 
	public abstract ReviewVO selectReviewByReviewId(@Param("reviewId")Integer reviewId);
	
	// 특정 유저 후기글 조회 
	public abstract LinkedBlockingDeque<ReviewVO> selectReviewsByUserId(@Param("userId")Integer userId);
	
	// 제목+내용 키워드로 후기글 조회 
	
	
	// 제목 키워드로 후기글 조회 
	public abstract LinkedBlockingDeque<ReviewVO> selectReviewsByTitle(@Param("title")String title);
	
	// 산 이름으로 후기글 조회 
	public abstract LinkedBlockingDeque<ReviewVO> selectReviewsBySanName(@Param("sanName")String sanName);
	
	
	
	// 특정 후기글 삭제 
	public abstract Integer deleteReviewByReviewId(@Param("reviewId")Integer reviewId);
	

}// end interface
