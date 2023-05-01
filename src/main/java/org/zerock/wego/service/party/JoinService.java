package org.zerock.wego.service.party;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.party.JoinDTO;
import org.zerock.wego.domain.party.PartyViewVO;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.exception.OperationFailException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.JoinMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service
public class JoinService {

	
	private final JoinMapper joinMapper;
	private final PartyService partyService;
	
	// 모집 참여 여부 
	public boolean isJoin(JoinDTO dto) throws ServiceException {
//		log.trace("isUserJoined({}, {}) invoked.", partyId, userId);
		
		String status = this.joinMapper.selectById(dto);
		
		return (status != null);
	}// cancleJoin
	
	
	// 현재 참여 인원 
	public int getCurrentCount(JoinDTO dto) throws ServiceException {
		
		return this.joinMapper.selectTotalCount(dto);
	}// currentCount 
	

	// 모집 참여 토글
		public void createOrCancle(JoinDTO dto) throws Exception {
//			log.trace("isJoinCreated({}, {}) invoked.", partyId, userId);
		
			try {
				PartyViewVO party = this.partyService.getById(dto.getSanPartyId());

				if (party == null) {
					throw new NotFoundPageException();
				} // if

				int currentJoin = this.joinMapper.selectTotalCount(dto);
				int maxJoin = party.getPartyMax();
				boolean isJoin = isJoin(dto);

				
				if (isJoin) {
					this.joinMapper.delete(dto);
					
				} else if(currentJoin >= maxJoin){
					
					throw new OperationFailException();
					
				} else {
					this.joinMapper.insert(dto);
				}// if-else
				
			} catch (NotFoundPageException | OperationFailException e) {
				throw e;
				
			} catch (Exception e) {
				throw new ServiceException(e);
			} // try-catch
		}// createOrCancle
	
	
	// 참여 생성
	public void create(JoinDTO dto) throws Exception {
		
		this.joinMapper.insert(dto);
	}// create
	
}// end class
