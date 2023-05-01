package org.zerock.wego.service.party;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.wego.domain.party.PartyDTO;
import org.zerock.wego.domain.party.PartyViewVO;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.exception.OperationFailException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.JoinMapper;
import org.zerock.wego.mapper.NotificationMapper;
import org.zerock.wego.mapper.PartyMapper;
import org.zerock.wego.service.badge.BadgeGetService;
import org.zerock.wego.service.common.CommentService;
import org.zerock.wego.service.common.FavoriteService;
import org.zerock.wego.service.common.FileService;
import org.zerock.wego.service.common.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor
@Service
public class PartyService {
	
	private final PartyMapper partyMapper;
	private final ReportService reportService;
	private final FileService fileService;
	private final FavoriteService favoriteService;
	private final CommentService commentService;
	private final BadgeGetService badgeGetService;
	private final NotificationMapper notificationMapper;
	private final JoinMapper joinMapper;


	public List<PartyViewVO> getList() throws ServiceException {
		log.trace("getList() invoked.");
		try {
			return this.partyMapper.selectAll();
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
	
	public Set<PartyViewVO> getRandom10List() throws ServiceException {
		log.trace("getRandom10List() invoked.");
		try {
			return this.partyMapper.selectRandom10();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getRandom10List
	
	public Integer getUserIdByPartyId(Integer partyId) {
		log.trace("getUserIdByPartyId() invoked.");
		try {
			
			return this.partyMapper.selectUserIdByPartyId(partyId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getUserIdByPartyId
	
	
	// 모집글 상세 조회 
	public PartyViewVO getById(Integer partyId) throws RuntimeException{
//		log.trace("getById({}) invoked.", partyId);
		try {
			PartyViewVO party = this.partyMapper.selectById(partyId);

			if(party == null) {
				 throw new NotFoundPageException();
			}// if
			 
			this.partyMapper.visitCountUp(partyId); 
			
			return party;
			
		} catch (NotFoundPageException e) {
			throw e;
			
		} catch (RuntimeException e) {
			throw new ServiceException(e);
		}// try-catch
	}// getById
	
	// 모집글 삭제 
	@Transactional
	public void removeById(Integer partyId) throws RuntimeException{
//		log.trace("isRemovedById({}) invoked.", partyId);
		try {
			boolean isExist = this.partyMapper.isExist(partyId);
			
			if(!isExist) {
				throw new NotFoundPageException();
			}// if
			//모집글에 참여한 사용자의 ID 가져오기
	        List<Integer> userIds = joinMapper.selectUserIdsBySanPartyId(partyId);

			 // 해당 모집글에 참여한 사용자에 대한 알림 생성
			 for (Integer userId : userIds) {
		            // 사용자에게 삭제 후 알림을 이미 받았는지 확인
		            if (!notificationMapper.isExistsPartyDeletionNotification(userId,partyId)) {
		                // 그렇지 않은 경우 새 알림 만들기
		            	log.trace(">>>>>>>>>> 모집글삭제로 인해 취소알림이 긴급으로 갑니다!");
		                notificationMapper.insertPartyDeletionByPartyIdAndUserId(partyId, userId);
		            }
		        }
			 // 삭제 전에 알림가야함. 왜냐? 삭제하면 아이디도 삭제되니까?
			this.partyMapper.deleteById(partyId);
			this.reportService.removeAllByTarget("SAN_PARTY", partyId);
			this.fileService.isRemoveByTarget("SAN_PARTY", partyId);
			this.favoriteService.removeAllByTarget("SAN_PARTY", partyId);
			this.commentService.removeAllByTarget("SAN_PARTY", partyId);
			
			
		} catch (NotFoundPageException | OperationFailException e) {
			throw e;
		
		} catch (RuntimeException e) {
			throw new ServiceException(e);
		}// try-catch
	}// removeParty
	
	public boolean register(PartyDTO dto) throws ServiceException {
		log.trace("register({}) invoked.", dto);
		try {
			return this.partyMapper.insert(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // register
	
	
	public boolean modify(PartyDTO dto) throws ServiceException {
		log.trace("modify({}) invoked.", dto);
		try {
			return this.partyMapper.update(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // modify
	
}// end class


