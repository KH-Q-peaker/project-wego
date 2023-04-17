package org.zerock.wego.domain.common;

import java.sql.Timestamp;

import lombok.Value;

@Value
public class CommentViewVO {

	private Integer commentId;		// 댓글 식별자
	private String targetGb;		// 소속 게시판 
	private Integer targetCd;		// 소속 글 식별자
	private String commentGb;		// 댓글 구분 
	private Integer mentionId;		// 멘션 코드 
	private Integer userId;			// 유저 코드 
	private String nickname;		// 작성자 닉네임
	private String userPic;			// 유저 사진 
	private Timestamp createdDt;	// 작성일 
	private Timestamp modifiedDt;	// 수정일 
	private String contents;		// 댓글 내용 
	private String status;			// 삭제 상태값 
	private Integer reportCnt;		// 신고수 
	private Integer mentionCnt;		// 답글수

	
}// end class
