package org.zerock.wego.domain.common;

import lombok.Data;

@Data
public class PageInfo {
	
	private String targetGb;
	private Integer targetCd;
	private Integer currPage = 1;  
	private Integer amount = 5;
	
}// end class
