package org.zerock.wego.service;

import java.util.concurrent.LinkedBlockingDeque;

import org.zerock.wego.domain.PartyVO;
import org.zerock.wego.exception.ServiceException;

public interface PartyService {

	// 게시글 상세화면 + 댓글 조회 
	public abstract PartyVO getPartyByPartyId(Long partyId) throws ServiceException;
	
	// 특정 유저 모집글 조회 
	public abstract LinkedBlockingDeque<PartyVO> getPartiesByUserId(Long userId) throws ServiceException;
	
	
	
	
	// 모집글 사진 조회 
	public abstract String getPartyImgByPartyId(Long partyId) throws ServiceException;

	
	
	// 모집글 사진 삭제  -- 생각해보니 없어도 되는? 고민 
//	public abstract boolean removePartyImg(Integer partyId) throws ServiceException;
	
	
	// 모집글 삭제 
	public abstract boolean isRemoveByPartyId(Long partyId, Long userId) throws ServiceException;
	
	
	
	// 모집 참여 여부 
	public abstract boolean isUserJoin(Long partyId, Long userId) throws ServiceException;
	
	// 모집 참여 등록
	public abstract boolean isJoinCreate(Long partyId, Long userId) throws ServiceException;
	
	// 모집 참여 취소 
	public abstract boolean isJoinCancle(Long partyId, Long userId) throws ServiceException;
	
	
}// end class
