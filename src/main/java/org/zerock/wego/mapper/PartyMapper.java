package org.zerock.wego.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Select;
import org.zerock.wego.domain.PartyDTO;
import org.zerock.wego.domain.PartyViewVO;


public interface PartyMapper {

	@Select("SELECT * FROM san_party_v ORDER BY created_dt DESC")
	public abstract List<PartyViewVO> selectAll(); // 전체목록조회
	
	public abstract Set<PartyViewVO> selectRandom10(); // 10개의 게시글 랜덤 조회
	public abstract PartyViewVO select(Integer sanPartyId); // 상세조회
	public abstract Long insert(PartyDTO dto); // 신규게시물등록
	public abstract Long update(PartyDTO dto); // 기존게시물수정
	public abstract Long delete(Integer sanPartyId); // 게시물삭제
	
} // end interface
