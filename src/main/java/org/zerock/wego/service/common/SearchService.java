package org.zerock.wego.service.common;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.info.SanInfoViewVO;
import org.zerock.wego.domain.party.PartyViewVO;
import org.zerock.wego.domain.review.ReviewViewVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.SearchMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service
public class SearchService {

	private final SearchMapper mapper;

	
	public Set<SanInfoViewVO> selectSearchSanInfo3(String search) throws ServiceException {
		log.trace("selectSearchSanInfo3({}) invoked.", search);
		
		try {
			return this.mapper.selectSearchSanInfo3ByQuery(search);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // selectSearchMountainInfo

	public Set<PartyViewVO> selectSearchParty3(String search) throws ServiceException {
		log.trace("selectSearchParty3({}) invoked.", search);
		
		try {
			return this.mapper.selectSearchParty3ByQuery(search);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // selectSearchRecruitment

	public Set<ReviewViewVO> selectSearchReview3(String search) throws ServiceException {
		log.trace("selectSearchReview3({}) invoked.", search);
		
		try {
			return this.mapper.selectSearchReview3ByQuery(search);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // selectSearchReview

} // end interface
