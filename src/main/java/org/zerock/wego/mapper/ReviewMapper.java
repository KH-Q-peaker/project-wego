package org.zerock.wego.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.wego.domain.common.BoardDTO;
import org.zerock.wego.domain.common.BoardSearchDTO;
import org.zerock.wego.domain.review.ReviewDTO;
import org.zerock.wego.domain.review.ReviewViewSortVO;
import org.zerock.wego.domain.review.ReviewViewVO;


public interface ReviewMapper {

	@Select("SELECT count(san_review_id) FROM san_review_v WHERE san_review_id > 0")
	public abstract Double selectTotalCount();
	public abstract Double selectTotalCountByQuery(String query);
	
	public abstract List<ReviewViewVO> selectAll();	
	
	public abstract List<ReviewViewSortVO> selectAllOrderByNewest(BoardDTO dto);
	public abstract List<ReviewViewSortVO> selectAllOrderByOldest(BoardDTO dto);
	public abstract List<ReviewViewSortVO> selectAllOrderByLike(BoardDTO dto);
	
	public abstract List<ReviewViewSortVO> selectSearchReviewByQueryOrderByNewest(BoardSearchDTO dto);
//	public abstract List<ReviewViewSortVO> selectSearchReviewByQueryOrderByOldest(BoardDTO dto, String query);
//	public abstract List<ReviewViewSortVO> selectSearchReviewByQueryOrderByLike(BoardDTO dto, String query);

	public abstract List<ReviewViewSortVO> selectReviewSuggestion();
	
	
	public abstract Set<ReviewViewVO> selectRandom10(); // 10개의 게시글 랜덤 조회
	public abstract Set<ReviewViewVO> selectSearchReview3ByQuery(String query);
	
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
