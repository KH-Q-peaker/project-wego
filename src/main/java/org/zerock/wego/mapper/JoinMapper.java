package org.zerock.wego.mapper;

import org.zerock.wego.domain.party.JoinDTO;

public interface JoinMapper {
	
	// 참여 인원 조회
	public abstract int selectTotalCount(JoinDTO dto);
	
	// 모집 참여 여부
	public abstract String selectById(JoinDTO dto);
	
	// 모집 참여 생성
	public abstract Integer insert(JoinDTO dto);
	
	// 모집 참여 삭제 
	public abstract Integer delete(JoinDTO dto);
	
}// end interface
