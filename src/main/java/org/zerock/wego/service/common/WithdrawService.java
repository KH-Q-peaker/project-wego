package org.zerock.wego.service.common;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.WithdrawMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Service
public class WithdrawService {

	
	private final WithdrawMapper withdrawMapper;

	// 회원 탈퇴 1. mypage-profile에서 올린 파일 조회
	public List<String> getMyProfileImagePathByUserId(Integer userId)
			throws ServiceException {
		log.trace("getMyProfileImagePathByUserId() invoked.");

		try {
			return this.withdrawMapper.seleteMyProfileImagePath(userId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isImageRegister

	// 회원 탈퇴 2. 모집게시판에서 내가 올린 파일 조회
	public List<String> getMyPartyImagePathByUserId(Integer userId)
			throws ServiceException {
		log.trace("getMyProfileImagePathByUserId() invoked.");

		try {
			return this.withdrawMapper.seleteMyPartyImagePath(userId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isImageRegister
	
	// 회원 탈퇴 3. 리뷰게시판에서 내가 올린 파일 조회
	public List<String> getMyReviewImagePathByUserId(Integer userId)
			throws ServiceException {
		log.trace("getMyProfileImagePathByUserId() invoked.");

		try {
			return this.withdrawMapper.seleteMyReviewImagePath(userId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isImageRegister
	
	// 회원 탈퇴 4. mypage-profile에서 올린 파일 테이블데이터 삭제
	public Integer deleteMyProfileImageFileTableListByUserId(Integer userId)
			throws ServiceException {
		log.trace("deleteMyProfileImageFileTableListByUserId() invoked.");

		try {
			return this.withdrawMapper.deleteMyProfileImageFileList(userId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isImageRegister
	
	// 회원 탈퇴 5. 모집게시판에서 내가 올린 파일 테이블데이터 삭제
	public Integer deleteMyPartyImageFileTableListByUserId(Integer userId)
			throws ServiceException {
		log.trace("deleteMyPartyImageFileTableListByUserId() invoked.");

		try {
			return this.withdrawMapper.deleteMyPartyImageFileList(userId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isImageRegister
	
	// 회원 탈퇴 6. 리뷰게시판에서 내가 올린 파일 테이블데이터 삭제
	public Integer deleteMyReviewImageFileTableListByUserId(Integer userId)
			throws ServiceException {
		log.trace("deleteMyReviewImageFileTableListByUserId() invoked.");

		try {
			return this.withdrawMapper.deleteMyReviewImageFileList(userId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isImageRegister
	
	// 회원 탈퇴 7. 나와 관련된 데이터 테이블 삭제
	// #{tableName}에 들어갈 목록: ALARM_TB, BADGE_GET_TB, CHAT_TB, COMMENT_TB, JOIN_TB, LIKE_TB,
	// REPORT_TB, SAN_PARTY_TB, SAN_REVIEW_TB, USER_TB
	public Integer deleteAllTableByMeByTableNameAndUserId(String tableName, Integer userId)
			throws ServiceException {
		log.trace("deleteAllTableByMeByTableNameAndUserId() invoked.");

		try {			
			return this.withdrawMapper.deleteAllTableByMe(tableName, userId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isImageRegister
	
} // end class