package org.zerock.wego.domain;

import lombok.Data;

@Data
public class ReviewDTO {

	private Integer sanReviewId; // 후기글 번호
	private Integer sanInfoId; // 산 코드(말머리)
	private Integer userId; // 작성자 ID
	private String createDt; // 작성일(current_timestamp)
	private String modifiedDt; // 수정일
	private String title; // 제목
	private String contents; // 내용
	
} // end class
