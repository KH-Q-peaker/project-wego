package org.zerock.wego.domain;

import java.util.Date;

import lombok.Value;


@Value
public class RecruitmentVO {
	
	private Integer	sanPartyId; // 모집글 코드 시퀀스
	private Integer sanInfoId; // 모집글 말머리 FK
	private Integer userId; // 모집글 작성자 코드 FK
	private String createDt; // 모집글 작성일(current_timestamp)
	private String modifiedDt; // 모집글 수정일
	private String title; // 모집글 제목
	private String contents; // 모집글 내용
	private Date partyDt; // 모집글 등반일(yyyy-MM-dd HH:mm)
	private Integer partyMax; // 모집 최대 인원
	private String items; // 준비물
	private String condition; // 등반조건
	
} // end class
