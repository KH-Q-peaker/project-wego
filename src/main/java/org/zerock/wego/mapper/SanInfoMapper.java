package org.zerock.wego.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Select;
import org.zerock.wego.domain.common.BoardDTO;
import org.zerock.wego.domain.common.BoardSearchDTO;
import org.zerock.wego.domain.info.SanInfoViewSortVO;
import org.zerock.wego.domain.info.SanInfoViewVO;


public interface SanInfoMapper {

	@Select("SELECT count(san_info_id) FROM san_info_v WHERE san_info_id > 0")
	public abstract Double selectTotalCount();
	public abstract Double selectTotalCountByQuery(String query);
	public abstract List<SanInfoViewVO> selectAll();
	public abstract List<SanInfoViewSortVO> selectAllOrderByAbc(BoardDTO dto);
	public abstract List<SanInfoViewSortVO> selectAllOrderByLike(BoardDTO dto);
	public abstract List<SanInfoViewSortVO> selectSearchSanInfoByQueryOrderByAbc(BoardSearchDTO dto);
//	public abstract List<SanInfoViewSortVO> selectSearchSanInfoByQueryOrderByLike(BoardDTO dto, String query);
	public abstract List<SanInfoViewSortVO> selectSanInfoSuggestion();
	public abstract Set<SanInfoViewVO> selectRandom10(); 
	public abstract SanInfoViewVO selectById(Integer sanInfoId); 
	public abstract Integer selectIdBySanName(String sanName); 
	
	public abstract Set<SanInfoViewVO> selectSearchSanInfo3ByQuery(String query);
	
} // end interface
