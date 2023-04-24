package org.zerock.wego.service.badge;

import java.util.Deque;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.zerock.wego.domain.badge.BadgeGetVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.BadgeGetMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service("BadgeGetService")
public class BadgeGetService {

	private final BadgeGetMapper badgeGetMapper;
	
	
	// 뱃지 획득
	public boolean register(Integer badgeId, Integer userId) {
		log.trace("register({}, {}) invoked", badgeId, userId);
		
		return this.badgeGetMapper.insertByBadgeIdAndUserID(badgeId, userId) == 1;
	} // register

	// 유저의 뱃지 획득 내역을 불러온다.
	public LinkedBlockingDeque<BadgeGetVO> getAllByUserId(Integer userId) throws RuntimeException {
		log.trace("getDeque({}) invoked", userId);

		LinkedBlockingDeque<BadgeGetVO> badgetDeque = badgeGetMapper.selectAllByUserId(userId);

		return badgetDeque;
	} // getDeque


	// 유저가 대표 뱃지로 설정한 획득 내역을 불러온다.
	public LinkedBlockingDeque<BadgeGetVO> getPickBadgeDequeByUserId(Integer userId)  throws RuntimeException {
		log.trace("getDeque({}) invoked", userId);

		LinkedBlockingDeque<BadgeGetVO> badgetDeque = badgeGetMapper.selectAllPickBadgeByUserId(userId);

		return badgetDeque;
	} // getDeque


	// 유저가 선택한 순서대로 대표 뱃지 설정 => 순서대로 대표 뱃지 설정 + 나머지 뱃지 상태 초기화
	//	@Transactional
	public boolean updatePickBadgeByUserIdAndPickList(Integer userId, List<Integer> pickList) throws RuntimeException {
		log.trace("updateRepresentiveBadge({}, {pickList}) invoked", userId);

		try {
			for(int index = 0; index < pickList.size(); index++) {
				int badgeId = pickList.get(index);
				int sequence = index + 1;

				updatePickBadgeStatusToSequence(userId, badgeId, sequence);
			} // for

			updateNotPickBadgeStautToN(userId, pickList);
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
	private boolean updatePickBadgeStatusToSequence(Integer userId, Integer badgeId, Integer sequence) throws RuntimeException { 
		log.trace("setPickedBadgeStatusToSequence({}, {}, {}) invoked", userId, badgeId, sequence);

		try {
			badgeGetMapper.updatePickBadgeStatusToPickedSequence(userId, badgeId, sequence);
		}
		catch (Exception e) {
			log.error(">>>>>>>> {} 에서 에러가 발생했습니다.", this);

			throw new ServiceException(e);
		} // try-catch

		return true;
	} // setPickedBadgeStatusToSequence

	// 유저가 선택하지 않은 뱃지의 상태를 모두 선택되지 않은 상태로 초기화
	// 유저가 선하지 않은 뱃지 = 유저가 선택한 뱃지의 여집합 => 선택한 뱃지를 넘겨주고 그 나머지 것들의 상태를 초기화
	private boolean updateNotPickBadgeStautToN(Integer userId, List<Integer> pickList) throws RuntimeException { 
		log.trace("resetNotPickedBadgeStatusToN({}, {}) invoked", userId, pickList);

		try {
			badgeGetMapper.updateNotPickBadgeStautToN(userId, pickList);
		}
		catch (Exception e) {
			log.error(">>>>>>>> {} 에서 에러가 발생했습니다.", this);

			throw new ServiceException(e);
		} // try-catch

		return true;
	} // resetNotPickedBadgeStatusToN


} // end class
