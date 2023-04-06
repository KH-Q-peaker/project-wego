package org.zerock.wego.domain;

import lombok.Value;

@Value
public class SanInfoViewVO {

	private Integer sanInfoId; // 산 ID
	private Integer mountainCd; // 코드
	private String sanName; // 이름
	private Integer height; // 높이
	private String address; // 주소
	private String reason; // 100대 명산 선정이유
	private String overview; // 개요?
	private String details; // 상세
	private Integer lon; // 
	private Integer lat; //
	private String img; // 이미지
	private Integer likeCnt; // 좋아요 수

	
} // end class
