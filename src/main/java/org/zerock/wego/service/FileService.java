package org.zerock.wego.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.FileDTO;
import org.zerock.wego.domain.FileVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.FileMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service
public class FileService {
	
	private final FileMapper mapper;	
	
	
	public List<FileVO> getList(String targetGb, Integer targetCd) throws ServiceException {
		log.trace("getList() invoked.");
		
		try {
			return this.mapper.selectByTargetGbAndTargetCd(targetGb, targetCd);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
	
	public boolean isRegister(FileDTO dto) throws ServiceException {
		log.trace("isRegistered({}) invoked.");
		
		try {
			return this.mapper.insert(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isRegistered

	public boolean isModify(String targetGb, Integer targetCd, Integer fileId, String fileName) throws ServiceException {
		log.trace("isModified({}) invoked.");
		
		try {
			return this.mapper.update(targetGb, targetCd, fileId, fileName) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isModified
	
	public boolean isRemoveByTarget(String targetGb, Integer targetCd) throws ServiceException {
		log.trace("isRemoveByTarget({}, {}) invoked.", targetGb, targetCd);
		
		try {

			int totalCount = this.mapper.selectTotalCountByTarget(targetGb, targetCd);
			
			int deleteCount = this.mapper.deleteByTarget(targetGb, targetCd);
			
			
			return totalCount == deleteCount;

		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isRemoveByTarget
	
	public boolean isRemove(String targetGb, Integer targetCd, String uuid) throws ServiceException {
		log.trace("isRemoved({}, {}) invoked.", targetGb, targetCd, uuid);
		
		try {
			return this.mapper.deleteByTargetGbAndTargetCdAndUuid(targetGb, targetCd, uuid) == 1;

		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isRemoved

} // end class
