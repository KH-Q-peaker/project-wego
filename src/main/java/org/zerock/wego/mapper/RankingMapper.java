package org.zerock.wego.mapper;

import java.util.concurrent.LinkedBlockingDeque;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.ranking.RankingVO;


@Mapper
public interface RankingMapper {
	
	// 한우물왕 조회 => 범위 (start ~ end) 
	public abstract LinkedBlockingDeque<RankingVO> selectOneWayFromStartToEnd(@Param("start")Integer strat, @Param("end")Integer end);

	// 한우물왕 조회 특정 유저만
	public abstract RankingVO selectOneWayByUserId(@Param("userId")Integer targetUserId);
	
	// 제일높왕 조회 => 범위 (start ~ end) 
	public abstract LinkedBlockingDeque<RankingVO> selectHighestFromStartToEnd(@Param("start")Integer strat, @Param("end")Integer end);
	
	// 제일높왕 조회 특정 유저만
	public abstract RankingVO selectHighestByUserId(@Param("userId")Integer targetUserId);
	
	// 참대왕 조회 => 범위 (start ~ end) 
	public abstract LinkedBlockingDeque<RankingVO> selectPartyKingFromStartToEnd(@Param("start")Integer strat, @Param("end")Integer end);
	
	// 참대왕 조회 특정 유저만
	public abstract RankingVO selectPartyKingByUserId(@Param("userId")Integer targetUserId);
	
	// 후기왕 조회 => 범위 (start ~ end) 
	public abstract LinkedBlockingDeque<RankingVO> selectReviewKingFromStartToEnd(@Param("start")Integer strat, @Param("end")Integer end);
	
	// 후기왕 조회 특정 유저만
	public abstract RankingVO selectReviewKingByUserId(@Param("userId")Integer targetUserId);
	
} // end interface
