package org.zerock.wego.service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.ReviewDTO;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

<<<<<<< HEAD
@Service
public class ReviewService {
=======
	// 특정 후기글 상세조회(READ)
	public abstract ReviewViewVO getById(Integer reviewId) throws ServiceException;
>>>>>>> branch 'main' of https://github.com/hellou8363/project-wego.git

<<<<<<< HEAD
	private final ReviewMapper mapper;
=======
	// 특정 후기글 삭제(DELETE)
	public abstract boolean isRemoved(Integer reviewId) throws ServiceException;
>>>>>>> branch 'main' of https://github.com/hellou8363/project-wego.git

<<<<<<< HEAD
	public List<ReviewViewVO> getList() throws ServiceException {
		log.trace("getList() invoked.");
		
		try {
			return this.mapper.selectAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
=======
	// 새로운 게시물 등록(CREATE)
	public abstract boolean isRegistered(ReviewDTO dto) throws ServiceException;
>>>>>>> branch 'main' of https://github.com/hellou8363/project-wego.git

<<<<<<< HEAD
	public Set<ReviewViewVO> getRandom10List() throws ServiceException {
		log.trace("getRandom10List() invoked.");
		
		try {
			return this.mapper.selectRandom10();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
=======
	// 기존 게시물 수정(UPDATE)
	public abstract boolean isModified(ReviewDTO dto) throws ServiceException;
>>>>>>> branch 'main' of https://github.com/hellou8363/project-wego.git

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

	public boolean isReviewRemove(Integer sanReviewId) throws ServiceException {
		log.trace("remove({}) invoked.", sanReviewId);
		
		try {
			
			return this.mapper.deleteReviewByReviewId(sanReviewId) == 1;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // remove

	public boolean register(ReviewDTO dto) throws ServiceException {
		log.trace("register({}) invoked.");
		
		try {
			return this.mapper.insert(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // register

	public boolean modify(ReviewDTO dto) throws ServiceException {
		log.trace("modify({}) invoked.");
		
		try {
			return this.mapper.update(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // modify

}// end class
