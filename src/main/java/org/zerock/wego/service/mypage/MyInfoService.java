package org.zerock.wego.service.mypage;

import org.zerock.wego.domain.mypage.WegoUserTbDTO;
import org.zerock.wego.exception.ServiceException;

public interface MyInfoService {
	
	//나의 취향 저장
	public abstract boolean setMyinfo(WegoUserTbDTO dto) throws ServiceException;

	//탈퇴
	public abstract boolean withDrawMyAccount(String user_id) throws ServiceException;
}//end interface
