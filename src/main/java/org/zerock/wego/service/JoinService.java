package org.zerock.wego.service;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.JoinDTO;
import org.zerock.wego.domain.PartyViewVO;
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
		
		return (status != null && status.equals("Y"));
	}// cancleJoin
	
	
	// 현재 참여 인원 
	public int getCurrentCount(JoinDTO dto) throws ServiceException {
		
		return this.joinMapper.selectTotalCount(dto);
	}// currentCount 
	
	
	// 모집 참여 토글
	public void createOrCancle(JoinDTO dto) throws Exception {
//		log.trace("isJoinCreated({}, {}) invoked.", partyId, userId);
		
		/*
		 * party isExist 만들어서 모집글 존재하는지 확인하기 어차피 여기서 가져온다면 이때 maxJoin 가져오면 되겠다
		 */
		try {
			PartyViewVO party = this.partyService.getById(dto.getSanPartyId());

			if (party == null) {
				throw new NotFoundPageException();
			} // if

			
			int currentJoin = this.joinMapper.selectTotalCount(dto);
			int maxJoin = party.getPartyMax();

			if (currentJoin >= maxJoin) {
				throw new OperationFailException();
			} // if

			
			String status = this.joinMapper.selectById(dto);

			if (status == null) {

				this.create(dto);
			} else {

				this.joinOrCancle(dto, status);
			} // if-else
			
		} catch (NotFoundPageException | OperationFailException e) {
			throw e;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}// createOrCancle
	
	
	// 참여 생성
	public void create(JoinDTO dto) throws Exception {
		
		this.joinMapper.insert(dto);
		
		if(!this.isJoin(dto)) {
			throw new OperationFailException();
		}// if
	}// create
	
	
	// 참여/취소 토글 
	public void joinOrCancle(JoinDTO dto, String status) throws Exception {

		if (status.equals("Y")) {

			this.joinMapper.update(dto, "N");

			if (this.isJoin(dto)) {
				throw new OperationFailException();
			} // if
		} else {
			this.joinMapper.update(dto, "Y");

			if (!this.isJoin(dto)) {
				throw new OperationFailException();
			} // if
		}// if-else
	}// joinOrCancle
	
	
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
