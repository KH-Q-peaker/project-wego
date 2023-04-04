package org.zerock.wego.service;

import java.util.Set;

import org.zerock.wego.domain.FavoriteDTO;
import org.zerock.wego.domain.FavoriteVO;
import org.zerock.wego.exception.ServiceException;

public interface FavoriteService {
	
	// 특정 유저의 좋아요 전체목록조회(READ)
	public abstract Set<FavoriteVO> getList(Long userId) throws ServiceException;
	// 기존 좋아요 조회(READ)
	public abstract Integer getCount(FavoriteDTO dto) throws ServiceException;
	// 새로운 좋아요 등록(CREATE)
	public abstract boolean register(FavoriteDTO dto) throws ServiceException;
	// 기존 좋아요 수정(UPDATE)
	public abstract boolean modify(FavoriteDTO dto) throws ServiceException;
	
} // end class
