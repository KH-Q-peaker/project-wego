package org.zerock.wego.service.common;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.common.FavoriteDTO;
import org.zerock.wego.domain.common.FavoriteVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.FavoriteMapper;
import org.zerock.wego.mapper.NotificationMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service
public class FavoriteService {

	private final FavoriteMapper favoriteMapper;
	private final NotificationService notificationService;

	
	public Set<FavoriteVO> getUserFavoriteOnList(Integer userId) throws ServiceException {
		log.trace("getUserFavoriteOnList({}) invoked.", userId);

		try {
			return this.favoriteMapper.getUserFavoriteOnList(userId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
	
	public boolean isExist(FavoriteDTO dto) throws ServiceException {
		
		return this.favoriteMapper.isExist(dto);
	}// isExist
	
	public boolean isFavorite(FavoriteDTO dto) throws ServiceException {
		log.trace("isFavoriteInfo({}) invoked.", dto);

		try {
			FavoriteVO favoriteVO = this.favoriteMapper.select(dto);
			
			if(favoriteVO == null) {
				return false;
			}
			
			return favoriteVO.getStatus().equals("Y");
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getCount
	
	public boolean register(FavoriteDTO dto) throws ServiceException {
		log.trace("register({}) invoked.", dto);

		try {
			 // 좋아요 등록 시 알림 추가
            this.favoriteMapper.insert(dto);
            this.notificationService.registerFavoriteByTargetCdAndUserId(dto);
			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // register

	public boolean modify(FavoriteDTO dto) throws ServiceException {
		log.trace("modify({}) invoked.", dto);

		try {
			return this.favoriteMapper.update(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // modify
	
	public boolean removeAllByTarget(String targetGb, Integer targetCd) {
		log.trace("removeAllByTarget({}, {}) invoked.", targetGb, targetCd);
		
		int totalCount = this.favoriteMapper.selectTotalCountByTarget(targetGb, targetCd);
		int deleteCount = this.favoriteMapper.deleteByTarget(targetGb, targetCd);
		
		return totalCount == deleteCount;
	}// removeAllByTarget
} // end class
