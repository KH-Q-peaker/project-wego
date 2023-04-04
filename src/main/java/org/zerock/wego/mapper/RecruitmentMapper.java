package org.zerock.wego.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Select;
import org.zerock.wego.domain.RecruitmentDTO;
import org.zerock.wego.domain.RecruitmentViewVO;


public interface RecruitmentMapper {

	@Select("SELECT * FROM san_party_v ORDER BY created_dt DESC")
	public abstract List<RecruitmentViewVO> selectAll(); // 전체목록조회
	
	public abstract Set<RecruitmentViewVO> selectRandom10(); // 10개의 게시글 랜덤 조회
	public abstract RecruitmentViewVO select(Integer sanPartyId); // 상세조회
	public abstract Long insert(RecruitmentDTO dto); // 신규게시물등록
	public abstract Long update(RecruitmentDTO dto); // 기존게시물수정
	public abstract Long delete(Integer sanPartyId); // 게시물삭제
	
} // end interface
