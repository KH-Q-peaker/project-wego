package org.zerock.wego.domain.info;

import lombok.Value;

@Value
public class SanInfoViewSortVO {

	private Integer sanInfoId; 
	private Integer mountainCd; 
	private String sanName; 
	private Integer height; 
	private String address; 
	private String reason; 
	private String overview; 
	private String details; 
	private Integer lon; 
	private Integer lat; 
	private String img; 
	private Integer likeCnt;
	private Integer sortNum;

	
} // end class
