package org.zerock.wego.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.party.PartyDTO;
import org.zerock.wego.domain.party.PartyViewVO;


public interface PartyMapper {

	public abstract List<PartyViewVO> selectAll();
	public abstract Set<PartyViewVO> selectRandom10();
	public abstract boolean isExist(@Param("partyId")Integer partyId);
	public abstract PartyViewVO selectById(@Param("partyId")Integer partyId);
	public abstract Integer selectUserIdByPartyId(Integer partyId);
	public abstract Long insert(PartyDTO dto);
	public abstract Long update(PartyDTO dto);
	public abstract Integer deleteById(@Param("partyId")Integer partyId);
	public abstract Integer visitCountUp(@Param("partyId")Integer partyId);		
	public abstract Set<PartyViewVO> selectSearchParty3ByQuery(String query);
	
} // end interface
