package org.zerock.wego.mapper;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.common.ReportDTO;

public interface ReportMapper {

	// 존재 여부 
	public abstract Integer find(ReportDTO dto);
	
	// 신고 접수 
	public abstract Integer insert(ReportDTO dto);
	
	// 타겟 신고 횟수 조회 
	public abstract int selectCountByTarget(@Param("targetGb")String targetGb, @Param("targetCd")Integer targetCd);
	
	// 신고 삭제 
	public abstract Integer deleteAllByTarget(@Param("targetGb")String targetGb, @Param("targetCd")Integer targetCd);
}// end class
