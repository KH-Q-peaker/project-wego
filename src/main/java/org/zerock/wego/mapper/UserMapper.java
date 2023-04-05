package org.zerock.wego.mapper;

import org.zerock.wego.domain.UserDTO;
import org.zerock.wego.domain.UserVO;

public interface UserMapper {

	
	// 카카오 권한? 얜뭐야?
//	public abstract void authorize(UserVO userVO);
	
	// 카카오 계정 조회 ? 
	public abstract UserVO selectUserByUserId(String socialId);
	
	// 좋아요 여부 조회 
	public abstract int selectLikeByUserId(String targetGb, Long targetCd, Long userId);
	
	//회원 가입 
	public abstract int insertUser(UserDTO dto);
	
}// end interface
