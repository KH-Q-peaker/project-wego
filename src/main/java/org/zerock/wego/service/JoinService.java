package org.zerock.wego.service;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.JoinDTO;
import org.zerock.wego.exception.OperationFailException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.JoinMapper;
import org.zerock.wego.mapper.PartyMapper;

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
		
		return (status != null && status.equals("Y"));
//		if(status != null && status.equals("Y")) {
//			return true;
//		}else {
//			return false;
//		}// if-else
	}// cancleJoin
	

	// 모집 참여 토글
	public void createOrCancle(JoinDTO dto) throws ServiceException {
//		log.trace("isJoinCreated({}, {}) invoked.", partyId, userId);
		
		//party isExist 만들어서 모집글 존재하는지 확인하기
		
		String status = this.joinMapper.selectById(dto);

		
		if (status == null) {

			this.joinMapper.insert(dto);
			
			if(!this.isJoin(dto)) {
				throw new OperationFailException();
			}// if

		} else if (status.equals("Y")) {

			this.joinMapper.update(dto, "N");
			
			if(this.isJoin(dto)) {
				throw new OperationFailException();
			}// if

		} else {

			this.joinMapper.update(dto, "Y");
			
			if(!this.isJoin(dto)) {
				throw new OperationFailException();
			}// if
		} // if-else
	}// offerJoin


	// 모집 참여 삭제 *** 얘도 댓글처럼 스케줄링 해야할듯 *** 
	public boolean isJoinRemove(JoinDTO dto) throws ServiceException {
//		log.trace("isJoinRemoved({}) invoked.", dto);
		
		try {
			
			return (this.joinMapper.delete(dto) == 1);
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}

}// end class
