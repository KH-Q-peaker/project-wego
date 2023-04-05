package org.zerock.wego.service;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.ReviewVO;
import org.zerock.wego.exception.ServiceException;

public interface ReviewService {

	// 후기글 상세조회 
	public abstract ReviewVO getReviewByReviewId(@Param("reviewId")Long reviewId) throws ServiceException;
	
	// 특정 게시글 삭제 
	public abstract boolean isReviewRemove(@Param("reviewId")Long reviewId) throws ServiceException;
	
}// end class
