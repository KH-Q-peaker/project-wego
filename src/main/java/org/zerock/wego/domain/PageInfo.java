package org.zerock.wego.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageInfo {
	
	private String targetGb;
	private Integer targetCd;
	@Builder.Default 
	private Integer currPage = 1;  
	@Builder.Default 
	private Integer amount = 5;
	
}// end class
