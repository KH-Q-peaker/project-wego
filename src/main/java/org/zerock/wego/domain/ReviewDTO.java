package org.zerock.wego.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReviewDTO {

	private Integer sanReviewId; 
	private Integer sanInfoId; 
	private Integer userId; 
	private Timestamp createDt; 
	private Timestamp modifiedDt; 
	private String title; 
	private String contents;
	
} // end class
