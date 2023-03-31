package org.zerock.wego.domain;

import lombok.Data;

@Data
public class Criteria {
// 페이징 처리에 관한 전송 파라미터를 가질 클래스 
// DTO의 부품 객체가 될 것
	
	private String targetGb;
	private Integer targetCd;
//	private Integer userId;
	private Integer currPage = 1; // 페이지 기본 값 
	private Integer amount = 5; // 불러올 데이터 기본 값
	
	
}// end class
