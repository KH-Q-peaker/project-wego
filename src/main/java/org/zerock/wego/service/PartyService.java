package org.zerock.wego.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.PartyDTO;
import org.zerock.wego.domain.PartyViewVO;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.PartyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor
@Service
public class PartyService {
	
	private final PartyMapper partyMapper;


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
	public PartyViewVO getById(Integer partyId) throws Exception{
		log.trace("getById({}) invoked.", partyId);
		
			PartyViewVO party = this.partyMapper.selectById(partyId);

			if(party == null) {
				 throw new NotFoundPageException("party not found : " + partyId);
			}// if
			
			return party;
	}// getById
	
	// 모집글 사진 조회
	// Base64가 필요해질 때를 대비해서 남겨둠 
//	public String getImgPathById(Integer partyId) throws ServiceException {
//		log.trace("getImgPathById({}) invoked.", partyId);
//		
//		try {
//			 
//			String path = this.partyMapper.selectPartyImgByPartyId(partyId);
//			/*
//			Resource img;
//			
//			if(path == null) {
//				
//				img = appContext.getResource("resources/img/dang.JPG");
//			}else {
//				
//				img = appContext.getResource(path);
//			}
//	        
//			@Cleanup
//			InputStream inputStream = img.getInputStream();
//			byte[] imgBytes = inputStream.readAllBytes();
//	        
//	        String base64 = Base64.getEncoder().encodeToString(imgBytes);
//		    
//			return base64;
//			*/
//			
//			if(path == null) {
//				path = "/resources/img/leaf.png";
//			}
//			
//			
//			return path;
//		}catch(Exception e) {
//			throw new ServiceException(e);
//		}// try-catch
//	}// getPartyImg
	
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
	
	
	// 모집글 삭제 
	public boolean isRemoveById(Integer partyId) throws Exception{
//		log.trace("isRemovedById({}) invoked.", partyId);

			int result = this.partyMapper.deleteById(partyId);
			
			if(result != 1) {
				
				throw new NotFoundPageException("delete failed : " + partyId);
				
			}else {
				return true;
			}// if-else
	}// removeParty
	
}// end class
