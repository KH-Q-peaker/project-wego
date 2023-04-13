package org.zerock.wego.service.mypage;

import java.util.List;

import org.zerock.wego.domain.mypage.WegoUserTbDTO;
import org.zerock.wego.domain.mypage.WegoUserTbVO;
import org.zerock.wego.exception.ServiceException;

public interface WegoUserTbService {
//	전체유저 조회
//	상세유저 조회
//	특정유저 탈퇴(삭제)
//	신규유저 가입
//	유저 정보수정
	public abstract List<WegoUserTbVO> searchAll() throws ServiceException;
	
	public abstract WegoUserTbVO search(String user_id) throws ServiceException;

	public abstract boolean withdraw(String user_id) throws ServiceException;
	
	public abstract boolean register(WegoUserTbDTO dto) throws ServiceException;
	
	public abstract boolean modify(WegoUserTbDTO dto) throws ServiceException;
	
	public abstract boolean modifyNick(WegoUserTbDTO dto) throws ServiceException;
}
