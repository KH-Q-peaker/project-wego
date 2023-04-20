package org.zerock.wego.service.info;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.common.Criteria;
import org.zerock.wego.domain.info.SanInfoViewVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.SanInfoMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service
public class SanInfoService {

	private final SanInfoMapper mapper;


	public Integer getTotalAmount() throws ServiceException {
		log.trace("getTotalAmount() invoked.");
		
		try {
			return this.mapper.selectTotalCount();
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getTotalAmount
	
	public Set<SanInfoViewVO> getList(Criteria cri) throws ServiceException {
		log.trace("getList() invoked.");
		
		try {
			return this.mapper.selectAll(cri);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
	
	public Set<SanInfoViewVO> getRandom10List() throws ServiceException {
		log.trace("getRandom10List() invoked.");
		
		try {
			return this.mapper.selectRandom10();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getRandom10List
	
	public SanInfoViewVO getById(Integer sanInfoId) throws ServiceException {
		log.trace("getById({}) invoked.", sanInfoId);	
		
		try {
			return this.mapper.selectById(sanInfoId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getById
	
	public Integer getIdBySanName(String sanName) throws ServiceException {
		log.trace("getIdBySanName({}) invoked.", sanName);
		
		try {
			return this.mapper.selectIdBySanName(sanName);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // selectSanName
	
} // end class
