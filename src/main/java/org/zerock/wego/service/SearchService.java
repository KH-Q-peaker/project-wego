package org.zerock.wego.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.PartyViewVO;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.domain.SanInfoViewVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.SearchMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service
public class SearchService {

	private final SearchMapper mapper;

	
	public Set<SanInfoViewVO> selectSearchMountainInfo(String search) throws ServiceException {
		log.trace("selectSearchMountainInfo({}) invoked.", search);
		
		try {
			return this.mapper.selectSearchMountainInfo(search);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // selectSearchMountainInfo

	public Set<PartyViewVO> selectSearchRecruitment(String search) throws ServiceException {
		log.trace("selectSearchRecruitment({}) invoked.", search);
		
		try {
			return this.mapper.selectSearchRecruitment(search);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // selectSearchRecruitment

	public Set<ReviewViewVO> selectSearchReview(String search) throws ServiceException {
		log.trace("selectSearchReview({}) invoked.", search);
		
		try {
			return this.mapper.selectSearchReview(search);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // selectSearchReview

} // end interface
