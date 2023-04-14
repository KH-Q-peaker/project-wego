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
	
	
	// 특정 후기글 존재여부 확인 
	public boolean isExist(Integer reviewId) throws ServiceException{
		
		boolean isExist = (this.reviewMapper.findById(reviewId) == 1);
		
		return isExist;
	}// isExist
	
	
	// 특정 후기글 조회 
	public ReviewViewVO getById(Integer reviewId) throws Exception {
//		log.trace("getById({}) invoked.", reviewId);	
		
			ReviewViewVO review = this.reviewMapper.selectById(reviewId);
			
			if(review == null) {
				throw new NotFoundPageException("해당 글을 찾을 수 없습니다."); 
			}// if
			
			this.reviewMapper.visitCountUp(reviewId);
			
			
			return review;
	} // getById
	
	
	// 특정 후기글 삭제
	public boolean remove(Integer reviewId) throws ServiceException {
//		log.trace("isRemoved({}) invoked.", reviewId);
			
			if(!this.isExist(reviewId)) {
				throw new NotFoundPageException("해당 글을 찾을 수 없습니다.");
			}// if
			
			boolean isRemove = (this.reviewMapper.delete(reviewId) == 1);
			
			if(this.isExist(reviewId)) {
				throw new ServiceException("삭제 안됨 이거 예외 뭐로날릴지 생각 ");
			}// if 
			
			return isRemove;
	} // remove
	
	
	public boolean register(ReviewDTO dto) throws ServiceException {
//		log.trace("register({}) invoked.");
		
		try {
			
			boolean isRegister = (this.reviewMapper.insert(dto) == 1);
			
			int insertPK = dto.getSanReviewId();
			
			if(!isExist(insertPK)) {
				throw new ServiceException("등록 안됨 이거 예외 뭐로날릴지 생각 ");
			}//if
			
			return isRegister;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // register
	
	
	public boolean modify(ReviewDTO dto) throws ServiceException {
//		log.trace("modify({}) invoked.");
		
		try {
			int modifyPK = dto.getSanReviewId();
			
			if(!isExist(modifyPK)) {
				throw new ServiceException("등록 안됨 이거 예외 뭐로날릴지 생각 ");
			}// if
			
			boolean isModify = (this.reviewMapper.update(dto) == 1);
			
			return isModify;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // modify

}// end class