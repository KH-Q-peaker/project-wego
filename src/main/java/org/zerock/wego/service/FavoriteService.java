package org.zerock.wego.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.FavoriteDTO;
import org.zerock.wego.domain.FavoriteVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.FavoriteMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service
public class FavoriteService {

	private final FavoriteMapper mapper;

	
	public Set<FavoriteVO> getUserFavoriteOnList(Integer userId) throws ServiceException {
		log.trace("getUserFavoriteOnList({}) invoked.", userId);

		try {
			return this.mapper.getUserFavoriteOnList(userId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList

	public boolean isFavoriteInfo(FavoriteDTO dto) throws ServiceException {
		log.trace("isFavoriteInfo({}) invoked.", dto);

		try {
			return this.mapper.isFavoriteInfo(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getCount
	
	public boolean register(FavoriteDTO dto) throws ServiceException {
		log.trace("register({}) invoked.", dto);

		try {
			return this.mapper.insert(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // register

	public boolean modify(FavoriteDTO dto) throws ServiceException {
		log.trace("modify({}) invoked.", dto);

		try {
			return this.mapper.update(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // modify
	
} // end class
