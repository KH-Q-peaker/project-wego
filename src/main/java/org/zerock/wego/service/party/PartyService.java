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


