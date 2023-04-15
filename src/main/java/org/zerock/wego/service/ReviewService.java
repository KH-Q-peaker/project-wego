package org.zerock.wego.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.ReviewDTO;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.exception.AccessBlindException;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.exception.OperationFailException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service
public class ReviewService {
	
	private final ReviewMapper reviewMapper;
	private final ReportService reportService;
	
	
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
	
	
	// 존재여부 
	public boolean isExist(Integer reviewId) throws ServiceException{
		
		return (this.reviewMapper.find(reviewId) != null);
	}// isExist
	
	// 특정 후기글 조회 
	public ReviewViewVO getById(Integer reviewId) throws Exception {
//		log.trace("getById({}) invoked.", reviewId);	
		
		try {
			ReviewViewVO review = this.reviewMapper.selectById(reviewId);
			
			if(review == null) {
				throw new NotFoundPageException();
			}// if
			
			if(review.getReportCnt() >= 5) {
				throw new AccessBlindException();
			}// if
			
			this.reviewMapper.visitCountUp(reviewId); //조회수증가.
			
			return review;
			
		} catch (NotFoundPageException | AccessBlindException e) { // 걍 나눌까 
			throw e;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getById
	
	
	// 특정 후기글 삭제
	public void removeById(Integer reviewId) throws Exception {
//		log.trace("isRemoved({}) invoked.", reviewId);
		
		try {
			if(!isExist(reviewId)) { 
				throw new NotFoundPageException();
			}// if
			
			this.reviewMapper.delete(reviewId);
			this.reportService.removeByTarget("SAN_REVIEW", reviewId);
			// 좋아요 내역 삭제도 추기돼야 함 
			// 파일 삭제도 추가돼야 함
			
			if(isExist(reviewId)) {
				throw new OperationFailException();
			}// if
			
		} catch(NotFoundPageException e) {
			throw e;
		} catch(OperationFailException e) {
			throw e;
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // remove
	
	
	public void register(ReviewDTO dto) throws Exception {
		log.trace("register({}) invoked.");
		
		try {
			this.reviewMapper.insert(dto);
			
			if(!isExist(dto.getSanReviewId())) {
				throw new OperationFailException();
			}// if
			
		}catch(OperationFailException e) { 
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // register
	
	
	public void modify(ReviewDTO dto) throws ServiceException {
		log.trace("modify({}) invoked.");
		
		try {
			if(!isExist(dto.getSanReviewId())) {
				throw new NotFoundPageException();
			}// if
			
			this.reviewMapper.update(dto);
			
		}catch (NotFoundPageException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // modify

}// end class
