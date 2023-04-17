package org.zerock.wego.mapper;

import java.util.concurrent.LinkedBlockingDeque;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.wego.domain.badge.BadgeVO;


@Mapper
public interface BadgeMapper {

	public abstract LinkedBlockingDeque<BadgeVO> selectAllSan();
	
	public abstract LinkedBlockingDeque<BadgeVO> selectAllRanking();
	
} // end interface
