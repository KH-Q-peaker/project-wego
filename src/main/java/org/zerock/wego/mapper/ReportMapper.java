package org.zerock.wego.mapper;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.Criteria;

public interface ReportMapper {


	
	// 신고 접수 
	public abstract Integer insertReport(@Param("userId")Integer userId, @Param("cri")Criteria cri, @Param("reportGb")String reportGb);
}// end class
