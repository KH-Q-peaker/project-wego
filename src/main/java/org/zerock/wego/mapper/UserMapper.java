package org.zerock.wego.mapper;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.common.UserDTO;
import org.zerock.wego.domain.common.UserVO;

public interface UserMapper {

	// userId로 유저 조회
	public abstract UserVO selectByUserId(Integer userId);

	// 소셜 계정 조회
	public abstract UserVO selectBySocialId(String socialId);
	
	// 닉네임 중복 확인	
	public abstract UserVO selectByNickname(String nickname);

	// 카카오 가입 등록 
	public abstract Integer insert(UserDTO dto);
	
	// 존재하는 유저인가?
	public abstract Integer isExist(Integer userId);
	
	// 유저 status 수정
	public abstract Integer updateStatusById(@Param("userId") Integer userId, @Param("status") String status);

}// end interface
