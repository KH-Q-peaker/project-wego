package org.zerock.wego.service;

import org.zerock.wego.domain.UserDTO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ServiceException;



public interface UserService {

	
	// 카카오 가입 
	public abstract boolean socialJoin(UserDTO dto) throws ServiceException;
	
	// 카카오 로그인 
	public abstract UserVO socialLogin(String socialId) throws ServiceException;
	
	
	// 유저 사진 조회
	public abstract String getUserPic(String userPic) throws ServiceException;
		
	
}// end interface
