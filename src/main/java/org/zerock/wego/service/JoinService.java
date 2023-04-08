package org.zerock.wego.service;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.JoinDTO;
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
	public boolean isUserJoined(JoinDTO dto) throws ServiceException {
//		log.trace("isUserJoined({}, {}) invoked.", partyId, userId);
		
		String status = this.joinMapper.selectById(dto);
		
		if(status != null && status.equals("Y")) {
			return true;
		}else {
			return false;
		}// if-else
	}// cancleJoin
	

	
	// 모집 참여 토글
	public boolean isJoinCreatedOrCancled(JoinDTO dto) throws ServiceException {
//		log.trace("isJoinCreated({}, {}) invoked.", partyId, userId);
		
		try {
			String status = this.joinMapper.selectById(dto);
			
			boolean isSuccess;
			
			if(status == null) {
				
				isSuccess = (this.joinMapper.insert(dto) == 1);
				
			}else if(status.equals("Y")){
				
				isSuccess = (this.joinMapper.update(dto, "N") == 1);
				
			}else{
				
				isSuccess = (this.joinMapper.update(dto, "Y") == 1);
			}// if-else
			
			return isSuccess;
		}catch (Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// offerJoin


	
//	// 모집 참여 취소 
//	public boolean isJoinCancled(JoinDTO dto) throws ServiceException {
////		log.trace("isJoinCancled({}, {}) invoked.", partyId, userId);
//		
//		try {
//			
//			return (this.joinMapper.update(dto, "N") == 1);
//			
//		}catch(Exception e) {
//			throw new ServiceException(e);
//		}// try-catch
//	}
	
	// 모집 참여 삭제 *** 얘도 댓글처럼 스케줄링 해야할듯 *** 
	public boolean isJoinRemoved(JoinDTO dto) throws ServiceException {
//		log.trace("isJoinRemoved({}) invoked.", dto);
		
		try {
			
			return (this.joinMapper.deleteById(dto) == 1);
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}

}// end class
