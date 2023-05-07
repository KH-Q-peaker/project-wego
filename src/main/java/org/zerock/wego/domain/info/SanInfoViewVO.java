package org.zerock.wego.domain.info;

import lombok.Value;

@Value
public class SanInfoViewVO {

	private Integer sanInfoId; 
	private Integer mountainCd; 
	private String sanName; 
	private Integer height; 
	private String address; 
	private String reason; 
	private String overview; 
	private String details; 
	private Double lon; 
	private Double lat; 
	private String img; 
	private Integer likeCnt;

	
} // end class
