package org.zerock.wego.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.wego.domain.PartyDTO;
import org.zerock.wego.domain.PartyViewVO;


public interface PartyMapper {

	@Select("SELECT * FROM san_party_v ORDER BY created_dt DESC")
	public abstract List<PartyViewVO> selectAll();
	
	public abstract Set<PartyViewVO> selectRandom10();
	public abstract Integer selectUserIdByPartyId(Integer partyId);
	public abstract Long insert(PartyDTO dto);
	public abstract Long update(PartyDTO dto);
	
	// 특정 모집글 조회 
	public abstract PartyViewVO selectById(@Param("partyId")Integer partyId);

	// 특정 모집글 삭제 
	public abstract Integer deleteById(@Param("partyId")Integer partyId);
		
		
//====================================================================
//	// 모집글 이미지 절대경로 조회 
//	public abstract String selectPartyImgByPartyId(@Param("partyId")Integer partyId);
//	// 모집글 이미지 삭제 (이거 파일 서비스가 있다면 그걸로해야할까? )
//	public abstract Integer deletePartyImgByPartyId(@Param("partyId")Integer partyId);
	// 특정 유저 모집글 조회 
//	public abstract LinkedBlockingDeque<PartyViewVO> selectPartiesByUserId(@Param("userId")Long userId);	
	// 산 이름으로 모집글 조회 
//	public abstract LinkedBlockingDeque<PartyViewVO> selectPartiesBySanName(@Param("sanName")String sanName);
		
		
		
} // end interface
