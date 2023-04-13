package org.zerock.wego.mapper;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.BadgeGetVO;


@Mapper
public interface BadgeGetMapper {

	// 유저(=userId)의 모든 뱃지 획득 목록 조회
	public abstract LinkedBlockingDeque<BadgeGetVO> selectWithUserId(@Param("userId")Long userId);
	// 유저(=userId)가 선택한 대표 뱃지(pickedBadgeId)를 선택된 순서(pickedSequence)에 맞게 대표뱃지 설정
	public abstract void updatePickedBadgeStatusToPickedSequence(@Param("userId")Long userId, @Param("pickedBadgeId")Long pickedBadgeId, @Param("pickedSequence")Long pickedSequence);
	// 유저(=userId)가 대표 뱃지로 선택하지 않은 모든 뱃지의 상태를 초기화
	// 선택한 뱃지의 리스트를 넘겨주고 리스트 이외의 모든 뱃지 획득 내역의 상태를 'N'으로 초기화
	public abstract void updateNotPickedBadgeStautToN(@Param("userId")Long userId, @Param("pickList")List<Long> pickList);
	
	
} // end interface
