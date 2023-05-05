package org.zerock.wego.service.common;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.info.SanInfoViewVO;
import org.zerock.wego.domain.party.PartyViewVO;
import org.zerock.wego.domain.review.ReviewViewVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.PartyMapper;
import org.zerock.wego.mapper.ReviewMapper;
import org.zerock.wego.mapper.SanInfoMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service
public class SearchService {

	private final SanInfoMapper sanInfoMapper;
	private final PartyMapper partyMapper;
	private final ReviewMapper reviewMapper;

	
	public Set<SanInfoViewVO> getSearchSanInfo3(String search) throws ServiceException {
		log.trace("getSearchSanInfo3(search) invoked.");
		
		try {
			return this.sanInfoMapper.selectSearchSanInfo3ByQuery(search);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // selectSearchMountainInfo

	public Set<PartyViewVO> getSearchParty3(String search) throws ServiceException {
		log.trace("getSearchParty3(search) invoked.");
		
		try {
			return this.partyMapper.selectSearchParty3ByQuery(search);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // selectSearchRecruitment

	public Set<ReviewViewVO> getSearchReview3(String search) throws ServiceException {
		log.trace("getSearchReview3(search) invoked.");
		
		try {
			return this.reviewMapper.selectSearchReview3ByQuery(search);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // selectSearchReview

} // end interface
