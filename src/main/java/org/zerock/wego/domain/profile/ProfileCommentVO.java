package org.zerock.wego.domain.profile;

import java.sql.Timestamp;

import lombok.Value;


@Value
public class ProfileCommentVO {
	
	private String targetGb; 		// 카테고리 이름 
	private Integer targetCb; 		// 게시글 번호
	private Integer commentId; 		// 댓글id
	private String userId; 			// 유저아이디 
	private String contents; 		// 내용
	private Timestamp createdDt; 	// 생성일 
	private String mentionCnt; 		// 맨션카운트수
} // end class
