package org.zerock.wego.service;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.BadgeGetVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.BadgeGetMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service("BadgeGetService")
public class BadgeGetService {

	private BadgeGetMapper badgeGetMapper;
	

	// 유저의 뱃지 획득 내역을 불러온다.
	public LinkedBlockingDeque<BadgeGetVO> getBadgeGetList(Long userId)
			throws ServiceException{
		log.trace("getBadgeGetList({}) invoked", userId);
		
		LinkedBlockingDeque<BadgeGetVO> badgetList = this.badgeGetMapper.selectWithUserId(userId);

		return badgetList;
	} // getBadgeGetList

	// 유저가 선택한 순서대로 대표 뱃지 설정 => 순서대로 대표 뱃지 설정 + 나머지 뱃지 상태 초기화
//	@Transactional
	public boolean changeRepresentiveBadge(Long userId, List<Long> pickList) 
			throws ServiceException {
		log.trace("changeRepresentiveBadge({}, {}) invoked", userId, pickList);

		try {
			// 순서대로 대표 상태를 변경
			for(int index = 0; index < pickList.size(); index++) {
				long badgeId = pickList.get(index);
				long sequence = index + 1;

				this.setPickedBadgeStatusToSequence(userId, badgeId, sequence);
			} // for

			// 선택되지 않은 뱃지의 상태를 변경
			this.resetNotPickedBadgeStatusToN(userId, pickList);
		}
		catch (UncategorizedSQLException e) {
			throw e;
		}
		catch (Exception e) {
			log.error(">>>>>>>> {} 에서 에러가 발생했습니다.", this);

			throw new ServiceException(e);
		} // try-catch

		return true;
	} // changeRepresentiveBadge

	// 유저가 선택한 순서로 대표 뱃지의 상태를 변경
	public boolean setPickedBadgeStatusToSequence(Long userId, Long badgeId, Long sequence) 
			throws ServiceException {
		log.trace("setPickedBadgeStatusToSequence({}, {}, {}) invoked", userId, badgeId, sequence);
		
		try {
			this.badgeGetMapper.updatePickedBadgeStatusToPickedSequence(userId, badgeId, sequence);
		}
		catch (Exception e) {
			log.error(">>>>>>>> {} 에서 에러가 발생했습니다.", this);

			throw new ServiceException(e);
		} // try-catch

		return true;
	} // setPickedBadgeStatusToSequence

	// 유저가 선택하지 않은 뱃지의 상태를 모두 선택되지 않은 상태로 초기화
	// 유저가 선하지 않은 뱃지 = 유저가 선택한 뱃지의 여집합 => 선택한 뱃지를 넘겨주고 그 나머지 것들의 상태를 초기화
	public boolean resetNotPickedBadgeStatusToN(Long userId, List<Long> pickList) 
			throws ServiceException {
		log.trace("resetNotPickedBadgeStatusToN({}, {}) invoked", userId, pickList);

		try {
			this.badgeGetMapper.updateNotPickedBadgeStautToN(userId, pickList);
		}
		catch (Exception e) {
			log.error(">>>>>>>> {} 에서 에러가 발생했습니다.", this);

			throw new ServiceException(e);
		} // try-catch
		
		return true;
	} // resetNotPickedBadgeStatusToN


} // end class
