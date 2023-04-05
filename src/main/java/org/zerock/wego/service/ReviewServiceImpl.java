package org.zerock.wego.service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.ReviewDTO;
import org.zerock.wego.domain.ReviewVO;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.ReviewMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;


@ToString
@Log4j2
@NoArgsConstructor

@Service
public class ReviewServiceImpl 
	implements ReviewService, InitializingBean { // POJO(상속X)

	@Setter(onMethod_= {@Autowired})
	private ReviewMapper mapper;
	
	
	@Override
	public void afterPropertiesSet() throws ServiceException { // 1회성 전처리
		log.trace("afterPropertiesSet() invoked.");
		
		try {
			Objects.requireNonNull(this.mapper);
			log.info("this.mapper: {}", this.mapper);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // afterPropertiesSet
	
	
	@Override
	public List<ReviewViewVO> getList() throws ServiceException {
		log.trace("getList() invoked.");
		
		try {
			return this.mapper.selectAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
	
	
	@Override
	public Set<ReviewViewVO> getRandom10List() throws ServiceException {
		log.trace("getRandom10List() invoked.");
		
		try {
			return this.mapper.selectRandom10();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
	
	
	// 특정 후기글 조회 
	@Override
	public ReviewViewVO getReviewByReviewId(Integer sanReviewId) throws ServiceException {
		log.trace("get({}) invoked.", sanReviewId);	
		
		try {
			ReviewViewVO review = this.mapper.selectReviewByReviewId(sanReviewId);
			Objects.requireNonNull(review);

			
			return review;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // get
	
	
	// 특정 후기글 삭제
	@Override
	public boolean isReviewRemove(Integer sanReviewId) throws ServiceException {
		log.trace("remove({}) invoked.", sanReviewId);
		
		try {
			
			return this.mapper.deleteReviewByReviewId(sanReviewId) == 1;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // remove
	
	
	@Override
	public boolean register(ReviewDTO dto) throws ServiceException {
		log.trace("register({}) invoked.");
		
		try {
			return this.mapper.insert(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // register
	
	
	@Override
	public boolean modify(ReviewDTO dto) throws ServiceException {
		log.trace("modify({}) invoked.");
		
		try {
			return this.mapper.update(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // modify

} // end class
