package org.zerock.wego.mapper;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.JoinDTO;

public interface JoinMapper {
	

	// 모집 참여 여부 조회 
	public abstract String selectById(JoinDTO dto);
	
	// 모집 참여 생성
	public abstract Integer insert(JoinDTO dto);
	
	// 모집 참여 토글 
	public abstract Integer update(@Param("dto")JoinDTO dto, @Param("status")String status);
	
	// 모집 참여 삭제 
	public abstract Integer deleteById(JoinDTO dto);

}// end interface
