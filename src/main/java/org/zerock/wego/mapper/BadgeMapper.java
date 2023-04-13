package org.zerock.wego.mapper;

import java.util.concurrent.LinkedBlockingDeque;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.wego.domain.BadgeVO;


@Mapper
public interface BadgeMapper {

	public abstract LinkedBlockingDeque<BadgeVO> selectSanBadge();
	
	public abstract LinkedBlockingDeque<BadgeVO> selectRankingBadge();
	
} // end interface
