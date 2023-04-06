package org.zerock.wego.domain;

import java.util.Date;

import lombok.Value;


@Value
public class PartyViewVO {
	
	private Integer	sanPartyId; // 모집글 코드 시퀀스
	private String sanName; // 산이름
	private Integer userId; // 작성자 ID
	private String nickName; // 작성자의 닉네임
	private String userPic; // 작성자의 프로필 이미지
	private Date createDt; // 작성일(current_timestamp)
	private Date modifiedDt; // 수정일
	private String title; // 제목
	private String contents; // 내용
	private Date partyDt; // 모집글 등반일(yyyy-MM-dd HH:mm)
	private Integer partyMax; // 최대 인원
	private Integer userCnt; // 참여 인원
	private String items; // 준비물
	private String condition; // 등반조건
	private String partyPic; // 업로드 이미지
	private Integer likeCnt; // 좋아요 수
	private Integer reportCnt; // 조회 수
	
} // end class
