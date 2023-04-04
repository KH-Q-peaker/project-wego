package org.zerock.wego.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Select;
import org.zerock.wego.domain.FavoriteDTO;
import org.zerock.wego.domain.FavoriteVO;

public interface FavoriteMapper {
	
	@Select("SELECT * FROM like_tb WHERE user_id = ${userId}")
	public abstract Set<FavoriteVO> selectAll(Long userId); // 유저ID가 일치하는 좋아요 전체목록조회
	public abstract Integer selectCount(FavoriteDTO dto); // 기존 좋아요 정보가 있는지 조회
	public abstract Long insert(FavoriteDTO dto);
	public abstract Long update(FavoriteDTO dto);

} // end class
