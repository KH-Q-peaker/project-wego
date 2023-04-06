package org.zerock.wego.service;

import org.springframework.stereotype.Service;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.JoinMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service
public class JoinService {

	
	private final JoinMapper joinMapper;
	

	// 모집 참여 여부 
	public boolean isUserJoined(Integer partyId, Integer userId) throws ServiceException {
		log.trace("isUserJoined({}, {}) invoked.", partyId, userId);
		
		return (this.joinMapper.selectById(partyId, userId) == 1); 
	}// cancleJoin
	

	
	// 모집 참여 등록 
	public boolean isJoinCreated(Integer partyId, Integer userId) throws ServiceException {
		log.trace("isJoinCreated({}, {}) invoked.", partyId, userId);
		
		try {
			
			return (this.joinMapper.insert(partyId, userId) == 1);
			
		}catch (Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// offerJoin


	
	// 모집 참여 취소 
	public boolean isJoinCancled(Integer partyId, Integer userId) throws ServiceException {
		log.trace("isJoinCancled({}, {}) invoked.", partyId, userId);
		
		try {
			
			return (this.joinMapper.deleteById(partyId, userId) == 1);
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}

}// end class
