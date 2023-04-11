package org.zerock.wego.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.Criteria;
import org.zerock.wego.domain.ProfileCommentVO;
import org.zerock.wego.domain.ProfileVO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.ProfileMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;


@ToString
@Log4j2
@NoArgsConstructor

@Service("profileService")
public class ProfileServiceImpl
	implements ProfileService, InitializingBean {	// POJO

	@Setter(onMethod_= { @Autowired })
	private ProfileMapper mapper;
		
	@Override
	public void afterPropertiesSet() throws ServiceException {	// 1회성 전처리
		log.trace("afterPropertiesSet() invoked.");
		
		try {
			Objects.requireNonNull(this.mapper);
			log.info("\t+ this.mapper: {}", this.mapper);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // afterPropertiesSet
	
	// 1. 유저 정보 가져오기
	@Override
	public List<UserVO> getUserById(Integer userId) throws ServiceException {
		log.debug("getUserById({}) invoked.", userId);
		try {
	        Objects.requireNonNull(this.mapper);

			return this.mapper.selectUserId(userId);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} //getUserById
	
	// 2.  작성 글 관련 
	@Override
	public List<ProfileVO> getListByUserId(Integer userId, Criteria cri) throws ServiceException {
		log.debug("getListByUserId({}, {}) invoked.", userId, cri);
		try {
	        // Step 1. Get the current page number and set it to 1 if it is null or less than 1
	        int currPage = cri.getCurrPage() == null || cri.getCurrPage() < 1 ? 1 : cri.getCurrPage();
	        cri.setCurrPage(currPage);
	        
	        Objects.requireNonNull(this.mapper);

			return this.mapper.selectAll(userId, cri);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} //getListByUserId

	// 2-2 . 작성 글 페이징 관련 총 게시글 수 
	@Override
	public Integer getTotalAmountByUserId(Integer userId) throws ServiceException {
		log.debug("getTotalAmountByUserId({}) invoked.", userId);
		try {
			Objects.requireNonNull(this.mapper);
			return this.mapper.selectTotalCount(userId);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} //getTotalAmountByUserId

	// 3. 작성 댓글 관련  
	public List<ProfileCommentVO> getListComment(Integer userId,Criteria cri2) throws ServiceException {
		log.trace("getListComment({},{}) invoked.", userId,cri2);
		
		try {
	        // Step 1. Get the current page number and set it to 1 if it is null or less than 1
	        int currPage = cri2.getCurrPage() == null || cri2.getCurrPage() < 1 ? 1 : cri2.getCurrPage();
	        cri2.setCurrPage(currPage);
	        Objects.requireNonNull(this.mapper);

			return this.mapper.selectAllComment(userId,cri2);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList

	// 3-2 . 작성 댓글 페이징 관련 총 게시글 수 
	@Override
	public Integer getTotalAmountComment(Integer userId) throws ServiceException {
		log.trace("getTotalAmountComment() invoked.");
		
		try {
	        Objects.requireNonNull(this.mapper);

			return this.mapper.selectTotalCountComment(userId);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}

} // end class
