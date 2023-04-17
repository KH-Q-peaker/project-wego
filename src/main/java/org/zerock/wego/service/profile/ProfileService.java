package org.zerock.wego.service.profile;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.common.Criteria;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.domain.profile.ProfileCommentVO;
import org.zerock.wego.domain.profile.ProfileVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.ProfileMapper;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;


@ToString
@Log4j2
@RequiredArgsConstructor

@Service("profileService")
public class ProfileService {	// POJO

	private final ProfileMapper mapper;
		
	// 1. 클릭한 유저 정보가져오기 
	public List<UserVO> getUserById(Integer userId) throws ServiceException {
		log.debug("getUserById({}) invoked.", userId);
		try {
	        Objects.requireNonNull(this.mapper);

			return this.mapper.selectUserId(userId);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} //getUserById
	
	// 2. 게시물 전체목록 조회(LIST)
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

	// 3. 현재 총 게시물 건수 반환 
	public Integer getTotalAmountByUserId(Integer userId) throws ServiceException {
		log.debug("getTotalAmountByUserId({}) invoked.", userId);
		try {
			Objects.requireNonNull(this.mapper);
			return this.mapper.selectTotalCount(userId);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} //getTotalAmountByUserId

	// 4. 댓글 전체 목록조회 (LIST)
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

	// 5. 현재 총 댓글 게시물의 건수 반환 
	public Integer getTotalAmountComment(Integer userId) throws ServiceException {
		log.trace("getTotalAmountComment() invoked.");
		
		try {
	        Objects.requireNonNull(this.mapper);

			return this.mapper.selectTotalCountComment(userId);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getTotalAmountComment

} // end class
