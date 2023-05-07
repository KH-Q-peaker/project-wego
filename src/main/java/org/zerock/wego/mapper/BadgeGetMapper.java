package org.zerock.wego.mapper;

import java.util.Deque;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.badge.BadgeGetVO;
import org.zerock.wego.domain.review.ReviewDTO;


@Mapper
public interface BadgeGetMapper {
	
	// 유저(=userId)가 뱃지를 획득
	public abstract Integer insertByBadgeIdAndUserID(@Param("badgeId")Integer badgeId, @Param("userId")Integer userId);
	
	// 유저(=userId)의 모든 뱃지 획득 목록 조회
	public abstract LinkedBlockingDeque<BadgeGetVO> selectAllByUserId(@Param("userId")Integer userId);
	
	// 유저(=userId)의 모든 뱃지 획득 목록 조회
	public abstract LinkedBlockingDeque<BadgeGetVO> selectAllPickBadgeByUserId(@Param("userId")Integer userId);
	
	// 유저(=userId)가 선택한 대표 뱃지(pickBadgeId)를 선택된 순서(pickSequence)에 맞게 대표뱃지 설정
	public abstract Integer updatePickBadgeStatusToPickedSequence(@Param("userId")Integer userId, @Param("pickBadgeId")Integer pickBadgeId, @Param("pickSequence")Integer pickSequence);
	
	// 유저(=userId)가 대표 뱃지로 선택하지 않은 모든 뱃지의 상태를 초기화
	// 선택한 뱃지의 리스트를 넘겨주고 리스트 이외의 모든 뱃지 획득 내역의 상태를 'N'으로 초기화
	public abstract Integer updateNotPickBadgeStautToN(@Param("userId")Integer userId, @Param("pickList")List<Integer> pickList);
	
	// 유저가 뱃지를 갖고있는지 확인합니다.
	public abstract boolean isExistByUserIdAndBadgeId(@Param("userId")Integer userId, @Param("badgeId")Integer badgeId);
	
	
} // end interface
