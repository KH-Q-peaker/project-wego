package org.zerock.wego.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.ReviewDTO;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service
public class ReviewService {
	
	private final ReviewMapper reviewMapper;
	
	
	public List<ReviewViewVO> getList() throws ServiceException {
		log.trace("getList() invoked.");
		
		try {
			return this.reviewMapper.selectAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
	
	
	public Set<ReviewViewVO> getRandom10List() throws ServiceException {
		log.trace("getRandom10List() invoked.");
		
		try {
			return this.reviewMapper.selectRandom10();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
	
	
	// 특정 후기글 조회 
	public ReviewViewVO getById(Integer reviewId) throws Exception {
		log.trace("getById({}) invoked.", reviewId);	
		
		try {
			ReviewViewVO review = this.reviewMapper.selectById(reviewId);
			
			if(review == null) {
				throw new NotFoundPageException("review not found : " + reviewId); // 얘왜 기본생성자없엄 
			}// if
			
			return review;
			
		} catch (NotFoundPageException e) {
			throw new NotFoundPageException(e);
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getById
	
	
	// 특정 후기글 삭제
	public boolean isRemoved(Integer reviewId) throws ServiceException {
		log.trace("isRemoved({}) invoked.", reviewId);
		
		try {
			return (this.reviewMapper.delete(reviewId) == 1);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // remove
	
	
	public boolean isRegistered(ReviewDTO dto) throws ServiceException {
		log.trace("register({}) invoked.");
		
		try {
			return this.reviewMapper.insert(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // register
	
	
	public boolean isModified(ReviewDTO dto) throws ServiceException {
		log.trace("modify({}) invoked.");
		
		try {
			return this.reviewMapper.update(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // modify

}// end class
