package org.zerock.wego.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.ReviewVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.ReviewMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService{
	
	
	@Setter(onMethod_= {@Autowired})
	private ReviewMapper reviewMapper;

	
	@Override
	public ReviewVO getReviewByReviewId(Long reviewId) throws ServiceException{
		log.trace("getReviewByReviewId({}) invoked.", reviewId);
		
		try {
			ReviewVO review = this.reviewMapper.selectReviewByReviewId(reviewId);
			Objects.requireNonNull(review);

			
			return review;

		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}// getReviewByReviewId


	@Override
	public boolean isReviewRemove(Long reviewId) throws ServiceException{
		log.trace("isRemove({}) invoked.", reviewId);
		
		try {
			
			return this.reviewMapper.deleteReviewByReviewId(reviewId) == 1;
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// selectReviewByReviewCd
	
}// end class
