package org.zerock.wego.mapper;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.party.JoinDTO;

public interface JoinMapper {
	
	// 참여 인원 수 
	public abstract int selectTotalCount(JoinDTO dto);
	
	// 모집 참여 여부
	public abstract String selectById(JoinDTO dto);
	
	// 모집 참여 생성
	public abstract Integer insert(JoinDTO dto);
	
	// 모집 참여 토글 (신청/취소) 
	public abstract Integer update(@Param("dto")JoinDTO dto, @Param("status")String status);
	
}// end interface
