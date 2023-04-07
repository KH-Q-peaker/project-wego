package org.zerock.wego.mapper;

import org.zerock.wego.domain.ReportDTO;

public interface ReportMapper {

	
	// 신고 접수 
	public abstract Integer insert(ReportDTO dto);
	
	// 신고 삭제
}// end class
