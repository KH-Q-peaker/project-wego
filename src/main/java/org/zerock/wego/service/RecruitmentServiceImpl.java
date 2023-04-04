package org.zerock.wego.service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.RecruitmentDTO;
import org.zerock.wego.domain.RecruitmentViewVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.RecruitmentMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@ToString
@Log4j2
@NoArgsConstructor

@Service
public class RecruitmentServiceImpl implements RecruitmentService, InitializingBean { // POJO(상속X)

	@Setter(onMethod_ = { @Autowired })
	private RecruitmentMapper mapper;

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
	public List<RecruitmentViewVO> getList() throws ServiceException {
		log.trace("getList() invoked.");

		try {
			return this.mapper.selectAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList

	@Override
	public Set<RecruitmentViewVO> getRandom10List() throws ServiceException {
		log.trace("getRandom10List() invoked.");

		try {
			return this.mapper.selectRandom10();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList

	@Override
	public RecruitmentViewVO get(Integer sanPartyId) throws ServiceException {
		log.trace("get({}) invoked.", sanPartyId);

		try {
			return this.mapper.select(sanPartyId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // get

	@Override
	public boolean remove(Integer sanPartyId) throws ServiceException {
		log.trace("remove({}) invoked.", sanPartyId);

		try {
			return this.mapper.delete(sanPartyId) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // remove

	@Override
	public boolean register(RecruitmentDTO dto) throws ServiceException {
		log.trace("register({}) invoked.", dto);

		try {
			return this.mapper.insert(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // register

	@Override
	public boolean modify(RecruitmentDTO dto) throws ServiceException {
		log.trace("modify({}) invoked.", dto);

		try {
			return this.mapper.update(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // modify

} // end class
