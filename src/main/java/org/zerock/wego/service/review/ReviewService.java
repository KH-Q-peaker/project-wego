package org.zerock.wego.service.review;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.wego.domain.common.BoardDTO;
import org.zerock.wego.domain.common.BoardSearchDTO;
import org.zerock.wego.domain.review.ReviewDTO;
import org.zerock.wego.domain.review.ReviewViewSortVO;
import org.zerock.wego.domain.review.ReviewViewVO;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.exception.OperationFailException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.ReviewMapper;
import org.zerock.wego.service.badge.BadgeGetService;
import org.zerock.wego.service.common.CommentService;
import org.zerock.wego.service.common.FavoriteService;
import org.zerock.wego.service.common.FileService;
import org.zerock.wego.service.common.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Service
public class ReviewService {

	private final ReviewMapper reviewMapper;
	private final ReportService reportService;
	private final FileService fileService;
	private final FavoriteService favoriteService;
	private final CommentService commentService;
	private final BadgeGetService badgeGetService;

	public Double getTotalCount() throws ServiceException {
		log.trace("getTotalCount() invoked.");

		try {
			return this.reviewMapper.selectTotalCount();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getTotalCount
	
	public Double getTotalCountByQuery(String query) throws ServiceException {
		log.trace("getTotalCount() invoked.");
		try {
			return this.reviewMapper.selectTotalCountByQuery(query);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getTotalCount
	
	public List<ReviewViewVO> getList() throws ServiceException {
		log.trace("getList() invoked.");

		try {
			return this.reviewMapper.selectAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList

	public List<ReviewViewSortVO> getSortNewestList(BoardDTO dto) throws ServiceException {
		log.trace("getSortNewestList(dto) invoked.");

		try {
			return this.reviewMapper.selectAllOrderByNewest(dto);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getSortNewestList

	public List<ReviewViewSortVO> getSortOldestList(BoardDTO dto) throws ServiceException {
		log.trace("getSortOldestList(dto) invoked.");

		try {
			return this.reviewMapper.selectAllOrderByOldest(dto);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getSortOldestList

	public List<ReviewViewSortVO> getSortLikeList(BoardDTO dto) throws ServiceException {
		log.trace("getSortLikeList(dto) invoked.");

		try {
			return this.reviewMapper.selectAllOrderByLike(dto);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getSortLikeList

	public List<ReviewViewSortVO> getSearchSortNewestList(BoardSearchDTO dto) throws ServiceException {
		log.trace("getSearchSortNewestList(dto, query) invoked.");

		try {
			return this.reviewMapper.selectSearchReviewByQueryOrderByNewest(dto);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getSearchSortNewestList

//	public List<ReviewViewSortVO> getSearchSortOldestList(BoardDTO dto, String query) throws ServiceException {
//		log.trace("getSearchSortOldestList(dto, query) invoked.");
//		
//		try {
//			return this.reviewMapper.selectSearchReviewByQueryOrderByOldest(dto, query);
//		} catch (Exception e) {
//			throw new ServiceException(e);
//		} // try-catch
//	} // getSearchSortOldestList

//	public List<ReviewViewSortVO> getSearchSortLikeList(BoardDTO dto, String query) throws ServiceException {
//		log.trace("getSearchSortLikeList(dto, query) invoked.");
//		
//		try {
//			return this.reviewMapper.selectSearchReviewByQueryOrderByLike(dto, query);
//		} catch (Exception e) {
//			throw new ServiceException(e);
//		} // try-catch
//	} // getSearchSortLikeList

	public List<ReviewViewSortVO> getReviewSuggestion() throws ServiceException {
		log.trace("getReviewSuggestion() invoked.");

		try {
			return this.reviewMapper.selectReviewSuggestion();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getReviewSuggestion
	
	public Set<ReviewViewVO> getRandom10List() throws ServiceException {
		log.trace("getRandom10List() invoked.");

		try {
			return this.reviewMapper.selectRandom10();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList

	// 특정 후기글 조회
	public ReviewViewVO getById(Integer reviewId) throws RuntimeException {
//		log.trace("getById({}) invoked.", reviewId);	

		try {
			ReviewViewVO review = this.reviewMapper.selectById(reviewId);

			if (review == null) {
				throw new NotFoundPageException();
			} // if

			this.reviewMapper.visitCountUp(reviewId); // 조회수증가.

			return review;

		} catch (NotFoundPageException e) {
			throw e;

		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getById

	// 특정 후기글 삭제
	@Transactional
	public void removeById(Integer reviewId) throws RuntimeException {
//		log.trace("isRemoved({}) invoked.", reviewId);

		try {
			boolean isExist = this.reviewMapper.isExist(reviewId);

			if (!isExist) {
				throw new NotFoundPageException();
			} // if

			this.reviewMapper.delete(reviewId);
			this.reportService.removeAllByTarget("SAN_REVIEW", reviewId);
			this.fileService.isRemoveByTarget("SAN_REVIEW", reviewId);
			this.favoriteService.removeAllByTarget("SAN_REVIEW", reviewId);
			this.commentService.removeAllByTarget("SAN_REVIEW", reviewId);
			// TO_DO : 좋아요 내역 삭제도 추기돼야 함,

		} catch (NotFoundPageException e) {
			throw e;

		} catch (RuntimeException e) {
			throw new ServiceException(e);
		} // try-catch
	} // remove

	public void register(ReviewDTO dto) throws RuntimeException {
		log.trace("register({}) invoked.");

		try {
			this.reviewMapper.insert(dto);

			boolean isExist = this.reviewMapper.isExist(dto.getSanReviewId());

			if (!isExist) {
				throw new OperationFailException();
			} // if

		} catch (OperationFailException e) {
			throw e;

		} catch (RuntimeException e) {
			throw new ServiceException(e);

		} // try-catch
	} // register

	public void modify(ReviewDTO dto) throws RuntimeException {
		log.trace("modify({}) invoked.");

		try {
			boolean isExist = this.reviewMapper.isExist(dto.getSanReviewId());

			if (!isExist) {
				throw new NotFoundPageException();
			} // if

			this.reviewMapper.update(dto);

		} catch (NotFoundPageException e) {
			throw e;

		} catch (RuntimeException e) {
			throw new ServiceException(e);

		} // try-catch
	} // modify

}// end class
