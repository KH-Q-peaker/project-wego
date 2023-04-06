package org.zerock.wego.service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.FileDTO;
import org.zerock.wego.domain.FileVO;
import org.zerock.wego.domain.RecruitmentDTO;
import org.zerock.wego.domain.RecruitmentViewVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.FileMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;


@ToString
@Log4j2
@NoArgsConstructor

@Service
public class FileServiceImpl 
	implements FileService, InitializingBean { // POJO(상속X)

	@Setter(onMethod_= {@Autowired})
	private FileMapper mapper;
	
	@Override
	public void afterPropertiesSet() throws ServiceException { // 1회성 전처리
		log.trace("afterPropertiesSet() invoked.");
		
		try {
			Objects.requireNonNull(this.mapper);
			log.info("this.mapper: {}", this.mapper);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // afterPropertiesSet
	
	
	@Override
	public List<FileVO> getList(String targetGb, Integer targetCd) throws ServiceException {
		log.trace("getList() invoked.");
		
		try {
			return this.mapper.select(targetGb, targetCd);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
	
	@Override
	public boolean register(FileDTO dto) throws ServiceException {
		log.trace("register({}) invoked.");
		
		try {
			return this.mapper.insert(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // register


	@Override
	public boolean modify(String targetGb, Integer targetCd, Integer fileId, String fileName) throws ServiceException {
		log.trace("modify({}) invoked.");
		
		try {
			return this.mapper.update(targetGb, targetCd, fileId, fileName) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // modify
	
	@Override
	public boolean remove(String targetGb, Integer targetCd) throws ServiceException {
		log.trace("remove({}, {}) invoked.", targetGb, targetCd);
		
		try {
			return this.mapper.delete(targetGb, targetCd) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // remove

} // end class
