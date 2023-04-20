package org.zerock.wego.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.wego.domain.common.Criteria;
import org.zerock.wego.domain.info.SanInfoViewVO;


public interface SanInfoMapper {

	@Select("SELECT count(san_info_id) FROM san_info_v WHERE san_info_id > 0")
	public abstract Integer selectTotalCount();
	public abstract Set<SanInfoViewVO> selectAll(@Param("cri")Criteria cri); 
	
	public abstract Set<SanInfoViewVO> selectRandom10(); 
	public abstract SanInfoViewVO selectById(Integer sanInfoId); 
	public abstract Integer selectIdBySanName(String sanName); 
	
	public abstract Set<SanInfoViewVO> selectSearchSanInfo3ByQuery(String query);
	public abstract Set<SanInfoViewVO> selectSearchSanInfoByQuery(String query);
	
} // end interface
