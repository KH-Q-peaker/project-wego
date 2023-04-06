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
			return this.mapper.select(targetGb, targetCd);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
	
	public boolean register(FileDTO dto) throws ServiceException {
		log.trace("register({}) invoked.");
		
		try {
			return this.mapper.insert(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // register

	public boolean modify(String targetGb, Integer targetCd, Integer fileId, String fileName) throws ServiceException {
		log.trace("modify({}) invoked.");
		
		try {
			return this.mapper.update(targetGb, targetCd, fileId, fileName) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // modify
	
	public boolean remove(String targetGb, Integer targetCd) throws ServiceException {
		log.trace("remove({}, {}) invoked.", targetGb, targetCd);
		
		try {
			return this.mapper.delete(targetGb, targetCd) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // remove

} // end class
