package org.zerock.wego.mapper;

import java.util.Set;

import org.zerock.wego.domain.FavoriteDTO;
import org.zerock.wego.domain.FavoriteVO;

public interface FavoriteMapper {
	
	public abstract Set<FavoriteVO> getUserFavoriteOnList(Integer userId);
	public abstract Integer isFavoriteInfo(FavoriteDTO dto);
	public abstract Integer insert(FavoriteDTO dto);
	public abstract Integer update(FavoriteDTO dto);

} // end class
