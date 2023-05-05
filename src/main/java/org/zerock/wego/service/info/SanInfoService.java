package org.zerock.wego.service.info;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.common.BoardDTO;
import org.zerock.wego.domain.common.BoardSearchDTO;
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

	private final SanInfoMapper sanInfoMapper;

	public List<SanInfoViewVO> getList() throws ServiceException {
		log.trace("getList() invoked.");

		try {
			return this.sanInfoMapper.selectAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList

	public List<SanInfoViewSortVO> getSortAbcList(BoardDTO dto) throws ServiceException {
		log.trace("getSortAbcList(dto) invoked.");

		try {
			return this.sanInfoMapper.selectAllOrderByAbc(dto);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getSortAbcList

	public List<SanInfoViewSortVO> getSortLikeList(BoardDTO dto) throws ServiceException {
		log.trace("getSortLikeList(dto) invoked.");

		try {
			return this.sanInfoMapper.selectAllOrderByLike(dto);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getSortLikeList

	public List<SanInfoViewSortVO> getSearchSortAbcList(BoardSearchDTO dto) throws ServiceException {
		log.trace("getSearchSortAbcList(dto, query) invoked.");

		try {
			return this.sanInfoMapper.selectSearchSanInfoByQueryOrderByAbc(dto);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getSearchSortAbcList

//	public List<SanInfoViewSortVO> getSearchSortLikeList(BoardDTO dto, String query) throws ServiceException {
//		log.trace("getSearchSortLikeList(dto, query) invoked.");
//		
//		try {
//			return this.sanInfoMapper.selectSearchSanInfoByQueryOrderByLike(dto, query);
//		} catch (Exception e) {
//			throw new ServiceException(e);
//		} // try-catch
//	} // getSearchSortLikeList

	public Set<SanInfoViewVO> getRandom10List() throws ServiceException {
		log.trace("getRandom10List() invoked.");

		try {
			return this.sanInfoMapper.selectRandom10();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getRandom10List

	public SanInfoViewVO getById(Integer sanInfoId) throws ServiceException {
		log.trace("getById({}) invoked.", sanInfoId);

		try {
			return this.sanInfoMapper.selectById(sanInfoId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getById

	public Integer getIdBySanName(String sanName) throws ServiceException {
		log.trace("getIdBySanName({}) invoked.", sanName);

		try {
			return this.sanInfoMapper.selectIdBySanName(sanName);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // selectSanName

} // end class
