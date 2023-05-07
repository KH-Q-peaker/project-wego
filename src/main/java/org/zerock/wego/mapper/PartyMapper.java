package org.zerock.wego.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.wego.domain.common.BoardDTO;
import org.zerock.wego.domain.common.BoardSearchDTO;
import org.zerock.wego.domain.party.PartyDTO;
import org.zerock.wego.domain.party.PartyViewSortVO;
import org.zerock.wego.domain.party.PartyViewVO;


public interface PartyMapper {

	@Select("SELECT count(san_party_id) FROM san_party_v WHERE san_party_id > 0")
	public abstract Double selectTotalCount();
	public abstract Double selectTotalCountByQuery(String query);
	
	public abstract List<PartyViewVO> selectAll();
	
	public abstract List<PartyViewSortVO> selectAllOrderByNewest(BoardDTO dto);
	public abstract List<PartyViewSortVO> selectAllOrderByOldest(BoardDTO dto);
	public abstract List<PartyViewSortVO> selectAllOrderByLike(BoardDTO dto);
	
	public abstract List<PartyViewSortVO> selectSearchPartyByQueryOrderByNewest(BoardSearchDTO dto);
//	public abstract List<PartyViewSortVO> selectSearchPartyByQueryOrderByOldest(BoardDTO dto, String query);
//	public abstract List<PartyViewSortVO> selectSearchPartyByQueryOrderByLike(BoardDTO dto, String query);

	public abstract List<PartyViewSortVO> selectPartySuggestion();
	
	
	public abstract Set<PartyViewVO> selectRandom10();
	public abstract Set<PartyViewVO> selectSearchParty3ByQuery(String query);
	public abstract boolean isExist(@Param("partyId")Integer partyId);
	public abstract PartyViewVO selectById(@Param("partyId")Integer partyId);
	public abstract Integer selectUserIdByPartyId(Integer partyId);
	public abstract Long insert(PartyDTO dto);
	public abstract Long update(PartyDTO dto);
	public abstract Integer deleteById(@Param("partyId")Integer partyId);
	public abstract Integer visitCountUp(@Param("partyId")Integer partyId);		
	
} // end interface
