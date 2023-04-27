package org.zerock.wego.domain.common;

import lombok.Data;

@Data
public class BoardDTO {
	private String orderBy = "abc";
	private Integer lastItemId = 0;
	
	private Integer page = 1;
	private Integer amount = 50;
	
} // end class
