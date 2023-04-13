package org.zerock.wego.service.mypage;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.mypage.WegoUserTbDTO;
import org.zerock.wego.domain.mypage.WegoUserTbVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.mypage.WegoUserTbMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@ToString
@Log4j2
@NoArgsConstructor

@Service
public class WegoUserTbServiceImpl implements WegoUserTbService,InitializingBean {
	
	@Setter(onMethod_= {@Autowired})
	WegoUserTbMapper mapper;
	
	@Override	//1회성 전처리
	public void afterPropertiesSet() throws ServiceException {
		log.trace("afterPropertiesSet() invoked");
		try {
			Objects.requireNonNull(mapper);
			log.info("mapper:{}", mapper);
		}catch(Exception e) {
			throw new ServiceException(e);
		}
	}//afterPropertiesSet
	
	@Override
	public List<WegoUserTbVO> searchAll() throws ServiceException {
		log.trace("searchAll() invoked");
		try {
			List<WegoUserTbVO> vo = mapper.selectAll();
			return vo;
		}catch(Exception e) {
			throw new ServiceException(e);
		}
	}//searchAll

	@Override
	public WegoUserTbVO search(String user_id) throws ServiceException {
		log.trace("search({}) invoked",user_id);
		try {
			return mapper.select(user_id);
		}catch(Exception e) {
			throw new ServiceException(e);
		}
	}//search

	@Override
	public boolean withdraw(String user_id) throws ServiceException {
		log.trace("withdraw({}) invoked",user_id);
		try {
			return mapper.delete(user_id) ==1;
		}catch(Exception e) {
			throw new ServiceException(e);
		}
	}//withdraw

	@Override
	public boolean register(WegoUserTbDTO dto) throws ServiceException {
		log.trace("register({}) invoked",dto);
		try {
			return mapper.insert(dto)==1;
		}catch(Exception e) {
			throw new ServiceException(e);
		}
	}//register

	@Override
	public boolean modify(WegoUserTbDTO dto) throws ServiceException {
		log.trace("modify({}) invoked",dto);
		try {
			return mapper.update(dto)==1;
		}catch(Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean modifyNick(WegoUserTbDTO dto) throws ServiceException {
		log.trace("modifyNick({}) invoked",dto);
		try {
			return mapper.updateNick(dto)==1;
		}catch(Exception e) {
			throw new ServiceException(e);
		}
	}//modify

}//end class
