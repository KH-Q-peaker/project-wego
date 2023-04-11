package org.zerock.wego.service;

import java.util.List;

import org.zerock.wego.domain.Criteria;
import org.zerock.wego.domain.ProfileCommentVO;
import org.zerock.wego.domain.ProfileVO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ServiceException;


public interface ProfileService {
	
	// 1. 클릭한 유저 정보가져오기 
	public abstract List<UserVO> getUserById(Integer userId) throws ServiceException;
	
	// 2. 게시물 전체목록 조회(LIST)
	public abstract List<ProfileVO> getListByUserId(Integer userId, Criteria cri) throws ServiceException;
	
	// 3. 현재 총 게시물 건수 반환 
	public abstract Integer getTotalAmountByUserId(Integer userId) throws ServiceException; 
	
	// 4. 댓글 전체 목록조회 (LIST)
	public abstract List<ProfileCommentVO> getListComment(Integer userId, Criteria cri2) throws ServiceException;
	
	// 5. 현재 총 댓글 게시물의 건수 반환 
	public abstract Integer getTotalAmountComment(Integer userId) throws ServiceException;


} // end class