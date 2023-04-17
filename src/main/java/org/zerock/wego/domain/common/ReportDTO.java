package org.zerock.wego.domain.common;

import lombok.Builder;
import lombok.Data;

@Data
public class ReportDTO {
	
	private Integer userId;
	private String targetGb;
	private Integer targetCd;
//	private Timestamp createdDt;
	private String reportGb;
	
	@Builder
	public ReportDTO(Integer userId, String targetGb, Integer targetCd, String reportGb) {
		
		this.userId = userId;
		this.targetGb = targetGb;
		this.targetCd = targetCd;
		this.reportGb = reportGb;
	}// constructor

} // end class
