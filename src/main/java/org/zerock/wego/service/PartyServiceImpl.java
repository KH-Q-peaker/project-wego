package org.zerock.wego.service;

import java.io.InputStream;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.PartyVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.PartyMapper;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Service
public class PartyServiceImpl implements PartyService{

	
	@Setter(onMethod_= {@Autowired})
	private PartyMapper partyMapper;
	
	
	/* 실ㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹ험ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ*/
	@Autowired
	 private ApplicationContext appContext;
	
	
	
	
	// 모집글 상세 조회 
	@Override
	public PartyVO getPartyByPartyId(Long partyId) throws ServiceException{
		log.trace("getPartyByPartyId({}) invoked.", partyId);
		
		try {
			PartyVO party = this.partyMapper.selectPartyByPartyId(partyId);
			Objects.requireNonNull(party);

			
			return party;

		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}// getPartyByPartyId


	
	// 모집글 사진 조회 
	@Override
	public String getPartyImgByPartyId(Long partyId) throws ServiceException {
		log.trace("getPartyImgByPartyId({}) invoked.", partyId);
		
		try {
			 
			String path = this.partyMapper.selectPartyImgByPartyId(partyId);
			/*
			Resource img;
			
			if(path == null) {
				
				img = appContext.getResource("resources/img/dang.JPG");
			}else {
				
				img = appContext.getResource(path);
			}
	        
			@Cleanup
			InputStream inputStream = img.getInputStream();
			byte[] imgBytes = inputStream.readAllBytes();
	        
	        String base64 = Base64.getEncoder().encodeToString(imgBytes);
		    
			return base64;
			*/
			
			if(path == null) {
				path = "/resources/img/leaf.png";
			}
			
			
			return path;
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// getPartyImg

	
	
	// 작성자로 모집글 조회 
	@Override
	public LinkedBlockingDeque<PartyVO> getPartiesByUserId(Long userId) throws ServiceException{
		log.trace("getPartiesByUserId({}) invoked.", userId);
		
		try {
			LinkedBlockingDeque<PartyVO> parties = this.partyMapper.selectPartiesByUserId(userId);
			Objects.requireNonNull(parties);

			return parties;

		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}// getPartiesByUserId


	
	// 모집글 삭제 
	@Override
	public boolean isRemoveByPartyId(Long partyId, Long userId) throws ServiceException{
		log.trace("isRemoveByPartyId({}) invoked.", partyId);
		
		try {
			
			boolean delParty = this.partyMapper.deletePartyByPartyId(partyId) == 1;
			boolean delImg = this.partyMapper.deletePartyImgByPartyId(partyId) == 1;
			boolean delJoin = this.partyMapper.deleteJoinByPartyIdUserId(partyId, userId) == 1;
			
			
			return delParty && delImg && delJoin; 
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch 
	}// removeParty

	
	
	// 모집 참여 여부 
	@Override
	public boolean isUserJoin(Long partyId, Long userId) throws ServiceException {
		log.trace("isUserJoin({}, {}) invoked.", partyId, userId);
		
		return this.partyMapper.selectJoinByPartyIdUserId(partyId, userId) != null; 
	}// cancleJoin
	

	
	
	// 모집 참여 등록 
	@Override
	public boolean isJoinCreate(Long partyId, Long userId) throws ServiceException {
		log.trace("isJoinCreate({}, {}) invoked.", partyId, userId);
		
		try {
			
			return this.partyMapper.insertJoinByPartyIdUserId(partyId, userId) == 1;
			
		}catch (Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// offerJoin


	
	// 모집 참여 취소 
	@Override
	public boolean isJoinCancle(Long partyId, Long userId) throws ServiceException {
		log.trace("isJoinCancle({}, {}) invoked.", partyId, userId);
		
		try {
			
			return this.partyMapper.deleteJoinByPartyIdUserId(partyId, userId) == 1;
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}



	

}// end class
