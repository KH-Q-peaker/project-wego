package org.zerock.wego.domain.common;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class NotificationDTO {
	// 고민중 일단 VO와 같은 모습입니다.
	private Integer alarmId;				// 알림 시퀀스 PK 
	private String targetGb;				// 알림 유형 ( 좋아요(후기글, 모집글), 댓글, 뱃지) 
	private Integer targetCd;				// 알림 소속코드
	private Integer userId;					// 알림 대상 
	private Integer createdByUserId;		// 알림을 눌린 사람 
	private String alarmGb;					// 알림 등급 ( 일반 , 긴급, 공지)
	private Timestamp createdDt;			// 알림 발생일
	private Timestamp readDt;				// 알림 확인일 
	private String contents;				// 알림 내용 
	private String status;					// 알림상태값
	private String nickname;				// 유저 닉네임 
	private String userPic;					// 알림을 대상 사진 
	private String title;					// 알림 게시물의 제목 
	private String commentStatus;			// 댓글상태값

} // end class
