package org.zerock.wego.domain;

import lombok.Data;


@Data
public class ReviewDTO {

	private Long sanReviewId;
	private String sanName;
	private Long userId;
	private String title;
	private String contents;
}// end class
