package org.zerock.wego.domain.common;

import lombok.Data;

@Data
public class BoardSearchDTO {
	private Integer page = 1;
	private Integer sortNum = 0;
	
	private Integer amount = 20;
	private String query;
} // end class
