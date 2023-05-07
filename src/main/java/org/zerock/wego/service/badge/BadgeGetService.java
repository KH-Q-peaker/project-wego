package org.zerock.wego.service.badge;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.badge.BadgeGetVO;
import org.zerock.wego.exception.OperationFailException;
import org.zerock.wego.mapper.BadgeGetMapper;
import org.zerock.wego.service.common.NotificationService;
import org.zerock.wego.service.common.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service
public class BadgeGetService {

	private final BadgeGetMapper badgeGetMapper;
	private final UserService userService;
	private final NotificationService notificationService;
	// 뱃지 획득
	public boolean register(Integer badgeId, Integer userId) {
		log.trace("register({}, {}) invoked", badgeId, userId);
		
		this.userService.isExistById(userId);
     	this.badgeGetMapper.insertByBadgeIdAndUserID(badgeId, userId);
     	this.notificationService.registerBadgeNotification(badgeId,userId);
     	return true;

	} // register

	// 유저가 뱃지를 갖고 있니?
	public boolean isExistByUserIdAndBadgeId(Integer userId, Integer badgeId) {
		log.trace("isExistById({}) invoked.", userId);

		if(!badgeGetMapper.isExistByUserIdAndBadgeId(userId, badgeId)){
			log.error("{} 유저는 {} 뱃지가 없습니다.", userId, badgeId);

			throw new OperationFailException("잘못된 접근입니다.");
		}

		return true;
	}// isExistById


	// 유저의 뱃지 획득 내역을 불러온다.
	public LinkedBlockingDeque<BadgeGetVO> getAllByUserId(Integer userId) throws RuntimeException {
		log.trace("getDeque({}) invoked", userId);

		this.userService.isExistById(userId);

		LinkedBlockingDeque<BadgeGetVO> badgetDeque = badgeGetMapper.selectAllByUserId(userId);

		return badgetDeque;
	} // getDeque


	// 유저가 대표 뱃지로 설정한 획득 내역을 불러온다.
	public LinkedBlockingDeque<BadgeGetVO> getPickBadgeDequeByUserId(Integer userId)  throws RuntimeException {
		log.trace("getDeque({}) invoked", userId);

		this.userService.isExistById(userId);

		LinkedBlockingDeque<BadgeGetVO> badgetDeque = badgeGetMapper.selectAllPickBadgeByUserId(userId);

		return badgetDeque;
	} // getDeque


	// 유저가 선택한 순서대로 대표 뱃지 설정 => 순서대로 대표 뱃지 설정 + 나머지 뱃지 상태 초기화
	//	@Transactional
	public boolean modifyPickBadgeByUserIdAndPickList(Integer userId, List<Integer> pickList) throws RuntimeException {
		log.trace("modifyPickBadgeByUserIdAndPickList({}, {pickList}) invoked", userId);

		this.userService.isExistById(userId);

		for(int index = 0; index < pickList.size(); index++) {
			int badgeId = pickList.get(index);
			int sequence = index + 1;

			modifyPickBadgeStatusToSequence(userId, badgeId, sequence);
		} // for

		modifyNotPickBadgeStautToN(userId, pickList);

		return true;
	} // changeRepresentiveBadge

	// 유저가 선택한 순서로 대표 뱃지의 상태를 변경
	private boolean modifyPickBadgeStatusToSequence(Integer userId, Integer badgeId, Integer sequence) throws RuntimeException { 
		log.trace("modifyPickBadgeStatusToSequence({}, {}, {}) invoked", userId, badgeId, sequence);

		this.isExistByUserIdAndBadgeId(userId, badgeId);
		
		badgeGetMapper.updatePickBadgeStatusToPickedSequence(userId, badgeId, sequence);

		return true;
	} // setPickedBadgeStatusToSequence

	// 유저가 선택하지 않은 뱃지의 상태를 모두 선택되지 않은 상태로 초기화
	// 유저가 선하지 않은 뱃지 = 유저가 선택한 뱃지의 여집합 => 선택한 뱃지를 넘겨주고 그 나머지 것들의 상태를 초기화
	private boolean modifyNotPickBadgeStautToN(Integer userId, List<Integer> pickList) { 
		log.trace("modifyNotPickBadgeStautToN({}, {}) invoked", userId, pickList);

		badgeGetMapper.updateNotPickBadgeStautToN(userId, pickList);

		return true;
	} // resetNotPickedBadgeStatusToN


} // end class
