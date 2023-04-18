package org.zerock.wego.service.badge;

import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.badge.BadgeVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.BadgeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service("badgeService")
public class BadgeService {

	private final BadgeMapper badgeMapper;


	public LinkedBlockingDeque<BadgeVO> getAllSan() {
		log.trace("getSanBadgeData() invoked");

		LinkedBlockingDeque<BadgeVO> sanBadgeDeque = badgeMapper.selectAllSan();

		return sanBadgeDeque;
	} // getSanBadgeData
	

	public LinkedBlockingDeque<BadgeVO> getAllRanking() {
		log.trace("getRankingBadgeData() invoked");

		LinkedBlockingDeque<BadgeVO> rankingBadgeDeque = badgeMapper.selectAllRanking();

		return rankingBadgeDeque;
	} // getRankingBadgeData

} // end class
