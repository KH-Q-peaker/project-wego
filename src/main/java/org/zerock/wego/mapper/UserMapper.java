package org.zerock.wego.mapper;

import org.zerock.wego.domain.UserDTO;
import org.zerock.wego.domain.UserVO;

public interface UserMapper {

	// 카카오 가입 등록 
	public abstract Integer insertUser(UserDTO dto);
	
	// 카카오 권한? 얜뭐야?
	public abstract void authorize(UserVO userVO);
	
	// userId로 유저 조회
	public abstract UserVO selectUserByUserId(Long targetUserId);
	// 카카오 계정 조회 ? 
	public abstract UserVO selectUserBySocialId(String socialId);
	
	
}// end interface
