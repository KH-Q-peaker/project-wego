package org.zerock.wego.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.common.NotificationVO;

public interface NotificationMapper {

		// 유저 코드로 알림목록 조회
		public abstract List<NotificationVO> selectAllByUserId(@Param("userId")Integer userId);

		// 알림 코드로 알림 조회
		public abstract NotificationVO selectByAlarmId(@Param("alarmId")Integer alarmId);

		// 좋아요과 함께 알림 추가 
		public abstract Integer insertFavoriteByTargetCdAndUserId(@Param("targetCd")Integer targetCd,@Param("userId")Integer userId);
				
		// 댓글 작성과 함께 알림 추가 
		public abstract Integer insertCommentByCommentIdAndUserId(@Param("commentId")Integer commentId,@Param("userId")Integer userId);
		
		// 멘션 작성과 함께 알림 추가
		public abstract Integer insertMentionByCommentIdAndUserId(@Param("commentId")Integer commentId,@Param("mentionId")Integer mentionId,@Param("userId")Integer userId);
		
		// 뱃지 획득(후기게시글작성)과 함께 알림 추가 
		public abstract Integer insertBadgeByBadgeIdAndUserId(@Param("badgeId")Integer badgeId, @Param("userId")Integer userId);
		
		// 파티 삭제 알림 존재 여부
	    public abstract boolean isExistsPartyDeletionNotification(@Param("userId") Integer userId, @Param("partyId") Integer partyId);
		
		// 모집글 삭제(모집취소)와 함께 알림 추가 
	    public abstract Integer insertPartyDeletionByPartyIdAndUserId(@Param("partyId")Integer partyId, @Param("userId")Integer userId);		
		
	    // 알림 읽고 상태 바꿔주기
		public abstract Integer updateStatusByAlarmIdAndUserId(@Param("alarmId")Integer alarmId,@Param("userId")Integer userId);
		
		// 알림 삭제
		public abstract Integer deleteByAlarmId(@Param("alarmId")Integer alarmId);
		
		// 알림 존재유무
		public abstract boolean isExistByUserId(@Param("alarmId")Integer alarmId);

		
} // end interface