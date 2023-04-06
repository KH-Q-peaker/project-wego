package org.zerock.wego.service;

import org.zerock.wego.domain.UserDTO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ServiceException;



public interface UserService {

	
	// 카카오 가입 
	public abstract boolean socialSignUp(UserDTO dto) throws ServiceException;
	
	// 카카오 로그인 
	public abstract UserVO sociaLogin(String socialId) throws ServiceException;

	public abstract UserVO getTargetUserInfo(Long targetUserId) throws ServiceException;
	
}// end interface
