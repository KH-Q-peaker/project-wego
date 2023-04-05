package org.zerock.wego.mapper;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.Target;

public interface ReportMapper {


	
	// 신고 접수 
	public abstract int insertReportByTargetUserId(@Param("cri")Target cri, @Param("userId")Long userId, @Param("reportGb")String reportGb);
}// end class
