package org.zerock.wego.mapper;

import java.util.List;

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
	
	// 모집에 참여한 사용자의 ID 가져오기
	public abstract List<Integer> selectUserIdsBySanPartyId(Integer partyId);
}// end interface
