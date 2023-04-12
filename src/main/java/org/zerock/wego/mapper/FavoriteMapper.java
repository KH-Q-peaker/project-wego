package org.zerock.wego.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.FavoriteDTO;
import org.zerock.wego.domain.FavoriteVO;

public interface FavoriteMapper {
	
	public abstract Set<FavoriteVO> getUserFavoriteOnList(Integer userId);
	public abstract Integer isFavoriteInfo(FavoriteDTO dto);
	public abstract Integer insert(FavoriteDTO dto);
	public abstract Integer update(FavoriteDTO dto);
	
	public abstract Integer selectTotalCountByTarget(@Param("targetGb")String targetGb, @Param("targetCd")Integer targetCd);
	public abstract Integer deleteByTarget(@Param("targetGb")String targetGb, @Param("targetCd")Integer targetCd);
} // end class
