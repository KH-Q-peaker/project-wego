package org.zerock.wego.mapper;

import org.apache.ibatis.annotations.Param;

public interface JoinMapper {
	

	// 모집 참여 여부 조회 
	public abstract Integer selectById(@Param("partyId")Integer partyId, @Param("userId")Integer userId);
	
	// 모집 참여 등록
	public abstract Integer insert(@Param("partyId")Integer partyId, @Param("userId")Integer userId);
	
	// 모집 참여 취소 
	public abstract Integer deleteById(@Param("partyId")Integer partyId, @Param("userId")Integer userId);

}// end interface
