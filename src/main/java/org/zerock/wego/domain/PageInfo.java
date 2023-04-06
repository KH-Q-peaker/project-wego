package org.zerock.wego.domain;

import lombok.Data;

@Data
public class PageInfo {
	
	private String targetGb;
	private Integer targetCd;
	private Integer currPage = 1; // 페이지 기본 값 
	private Integer amount = 5; // 불러올 데이터 기본 값
	
}// end class
