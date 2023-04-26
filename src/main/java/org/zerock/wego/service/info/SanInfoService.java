package org.zerock.wego.service.info;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.common.BoardDTO;
import org.zerock.wego.domain.info.SanInfoViewSortVO;
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
	
	public Set<SanInfoViewVO> getList() throws ServiceException {
		log.trace("getList() invoked.");
		
		try {
			return this.mapper.selectAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
	
	
	public List<SanInfoViewSortVO> getSortAbcList(BoardDTO dto) throws ServiceException {
		log.trace("getSortList({}) invoked.");
		
		try {
			return this.mapper.selectAllByAbc(dto);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getSortList
	
	public List<SanInfoViewSortVO> getSortLikeList(BoardDTO dto) throws ServiceException {
		log.trace("getSortList({}) invoked.");
		
		try {
			return this.mapper.selectAllByLike(dto);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getSortList
	
	
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
