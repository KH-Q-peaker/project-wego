package org.zerock.wego.domain;

import java.util.Date;

import lombok.Value;

@Value
public class ReviewViewVO {

	private Integer sanReviewId; // 후기글 번호
	private String sanName; // 산 이름
	private Integer userId; // 작성자 ID
	private String nickName; // 작성자의 닉네임
	private String userPic; // 작성자의 프로필 이미지
	private Date createDt; // 작성일(current_timestamp)
	private String modifiedDt; // 수정일
	private String title; // 제목
	private String contents; // 내용
	private Integer likeCnt; // 좋아요 수
	private Integer reportCnt; // 조회 수
	
} // end class
