package org.zerock.wego.mapper;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.ReportDTO;

public interface ReportMapper {

	
	// 신고 접수 
	public abstract Integer insert(ReportDTO dto);
	
	// 타겟 신고 횟수 조회 
	public abstract Integer selectCountByTarget(@Param("targetGb")String targetGb, @Param("targetCd")Integer targetCd);
	
	// 신고 삭제 
	public abstract Integer deleteByTarget(@Param("targetGb")String targetGb, @Param("targetCd")Integer targetCd);
}// end class
