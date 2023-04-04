package org.zerock.wego.service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.MountainInfoViewVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.MountainInfoMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;


@ToString
@Log4j2
@NoArgsConstructor

@Service
public class MountainInfoServiceImpl 
	implements MountainInfoService, InitializingBean { // POJO(상속X)

	@Setter(onMethod_= {@Autowired})
	private MountainInfoMapper mapper;
	
	
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
	public List<MountainInfoViewVO> getList() throws ServiceException {
		log.trace("getList() invoked.");
		
		try {
			return this.mapper.selectAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
	
	
	@Override
	public Set<MountainInfoViewVO> getRandom10List() throws ServiceException {
		log.trace("getRandom10List() invoked.");
		
		try {
			return this.mapper.selectRandom10();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
	
	
	@Override
	public MountainInfoViewVO get(Integer sanInfoId) throws ServiceException {
		log.trace("get({}) invoked.", sanInfoId);	
		
		try {
			return this.mapper.select(sanInfoId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // get
	
	
	@Override
	public Integer selectSanName(String sanName) throws ServiceException {
		log.trace("selectSanName({}) invoked.", sanName);
		
		try {
			return this.mapper.selectSanName(sanName);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // selectSanName
	
} // end class
