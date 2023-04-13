package org.zerock.wego.service;

import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.BadgeVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.BadgeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service("BadgeService")
public class BadgeService {
	
	private final BadgeMapper badgeMapper;

	
	public LinkedBlockingDeque<BadgeVO> getSanBadgeData() 
			throws ServiceException {
		log.trace("getSanBadgeData() invoked");
		
		try {
			LinkedBlockingDeque<BadgeVO> sanBadgeList = this.badgeMapper.selectSanBadge();
			
			return sanBadgeList;
		}
		catch (Exception e) {
			log.error(">>>>>>>> {} 에서 에러가 발생했습니다.", this);

			throw new ServiceException(e);
		} // try-catch
		
	} // getSanBadgeData
	
	public LinkedBlockingDeque<BadgeVO> getRankingBadgeData() 
			throws ServiceException {
		log.trace("getRankingBadgeData() invoked");
		
		try {
			LinkedBlockingDeque<BadgeVO> rankingBadgeList = this.badgeMapper.selectRankingBadge();
			
			return rankingBadgeList;
		}
		catch (Exception e) {
			log.error(">>>>>>>> {} 에서 에러가 발생했습니다.", this);

			throw new ServiceException(e);
		} // try-catch
		
	} // getRankingBadgeData
	
} // end class
