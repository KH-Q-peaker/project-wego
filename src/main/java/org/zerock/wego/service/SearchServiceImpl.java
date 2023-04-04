package org.zerock.wego.service;

import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.MountainInfoViewVO;
import org.zerock.wego.domain.RecruitmentViewVO;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.SearchMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;


@ToString
@Log4j2
@NoArgsConstructor

@Service
public class SearchServiceImpl 
	implements SearchService, InitializingBean {

	@Setter(onMethod_ = { @Autowired })
	private SearchMapper mapper;

	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.trace("afterPropertiesSet() invoked.");

		try {
			Objects.requireNonNull(this.mapper);
			log.info("this.mapper: {}", this.mapper);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // afterPropertiesSet

	
	@Override
	public Set<MountainInfoViewVO> selectSearchMountainInfo(String search) throws ServiceException {
		log.trace("selectSearchMountainInfo({}) invoked.", search);
		
		try {
			return this.mapper.selectSearchMountainInfo(search);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // selectSearchMountainInfo

	@Override
	public Set<RecruitmentViewVO> selectSearchRecruitment(String search) throws ServiceException {
		log.trace("selectSearchRecruitment({}) invoked.", search);
		
		try {
			return this.mapper.selectSearchRecruitment(search);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // selectSearchRecruitment


	@Override
	public Set<ReviewViewVO> selectSearchReview(String search) throws ServiceException {
		log.trace("selectSearchReview({}) invoked.", search);
		
		try {
			return this.mapper.selectSearchReview(search);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // selectSearchReview

} // end interface
