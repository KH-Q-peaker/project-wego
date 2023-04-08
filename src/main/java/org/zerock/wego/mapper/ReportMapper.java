package org.zerock.wego.mapper;

import org.zerock.wego.domain.ReportDTO;

public interface ReportMapper {

	
	// 신고 접수 
	public abstract Integer insert(ReportDTO dto);
	
	// 타겟 신고 횟수 조회 
	public abstract Integer selectCountByTarget(ReportDTO dto);
	
	// 신고 삭제
}// end class
