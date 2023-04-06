package org.zerock.wego.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.PartyDTO;
import org.zerock.wego.domain.PartyViewVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.PartyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service
public class PartyService {
	
	private final PartyMapper mapper;


	public List<PartyViewVO> getList() throws ServiceException {
		log.trace("getList() invoked.");

		try {
			return this.mapper.selectAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList

	public Set<PartyViewVO> getRandom10List() throws ServiceException {
		log.trace("getRandom10List() invoked.");

		try {
			return this.mapper.selectRandom10();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList

	public PartyViewVO get(Integer sanPartyId) throws ServiceException {
		log.trace("get({}) invoked.", sanPartyId);

		try {
			return this.mapper.select(sanPartyId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // get

	public boolean remove(Integer sanPartyId) throws ServiceException {
		log.trace("remove({}) invoked.", sanPartyId);

		try {
			return this.mapper.delete(sanPartyId) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // remove

	public boolean register(PartyDTO dto) throws ServiceException {
		log.trace("register({}) invoked.", dto);

		try {
			return this.mapper.insert(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // register

	public boolean modify(PartyDTO dto) throws ServiceException {
		log.trace("modify({}) invoked.", dto);

		try {
			return this.mapper.update(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // modify

} // end class
