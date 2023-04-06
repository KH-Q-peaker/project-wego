package org.zerock.wego.service;

import java.util.List;
import java.util.Set;

import org.zerock.wego.domain.ReviewDTO;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.exception.ServiceException;

public interface ReviewService {

//	// 게시물 전체목록 조회(LIST)
	public abstract List<ReviewViewVO> getList() throws ServiceException;

	// 10개의 게시물 랜덤 조회(LIST) - 메인페이지용
	public abstract Set<ReviewViewVO> getRandom10List() throws ServiceException;

	// 특정 후기글 상세조회(READ)
	public abstract ReviewViewVO getById(Integer reviewId) throws ServiceException;

	// 특정 후기글 삭제(DELETE)
	public abstract boolean isRemoved(Integer reviewId) throws ServiceException;

	// 새로운 게시물 등록(CREATE)
	public abstract boolean isRegistered(ReviewDTO dto) throws ServiceException;

	// 기존 게시물 수정(UPDATE)
	public abstract boolean isModified(ReviewDTO dto) throws ServiceException;

} // end interface
	
