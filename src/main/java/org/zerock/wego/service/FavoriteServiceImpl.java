package org.zerock.wego.service;

import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.FavoriteDTO;
import org.zerock.wego.domain.FavoriteVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.FavoriteMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@ToString
@Log4j2
@NoArgsConstructor

@Service
public class FavoriteServiceImpl implements FavoriteService, InitializingBean {

	@Setter(onMethod_ = { @Autowired })
	private FavoriteMapper mapper;

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
	public Set<FavoriteVO> getList(Long userId) throws ServiceException {
		log.trace("get({}) invoked.", userId);

		try {
			return this.mapper.selectAll(userId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList

	@Override
	public Integer getCount(FavoriteDTO dto) throws ServiceException {
		log.trace("get({}) invoked.", dto);

		try {
			return this.mapper.selectCount(dto);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getCount
	
	@Override
	public boolean register(FavoriteDTO dto) throws ServiceException {
		log.trace("register({}) invoked.", dto);

		try {
			return this.mapper.insert(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // register

	@Override
	public boolean modify(FavoriteDTO dto) throws ServiceException {
		log.trace("modify({}) invoked.", dto);

		try {
			return this.mapper.update(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // modify
	
} // end class
