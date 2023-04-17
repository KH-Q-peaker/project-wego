package org.zerock.wego.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.common.FileDTO;
import org.zerock.wego.domain.common.FileVO;


public interface FileMapper {
	

	// 타겟의 첨부파일 개수 조회 
	public abstract Integer selectTotalCountByTarget(@Param("targetGb")String targetGb, @Param("targetCd")Integer targetCd);
	public abstract List<FileVO> selectByTargetGbAndTargetCd(@Param("targetGb")String targetGb, @Param("targetCd")Integer targetCd); 
	public abstract Integer insert(FileDTO file); 
	public abstract Integer update(String targetGb, Integer targetCd, Integer fileId, String fileName);
	public abstract Integer deleteByTarget(@Param("targetGb")String targetGb, @Param("targetCd")Integer targetCd); // 파일 삭제
	public abstract Integer deleteByTargetGbAndTargetCdAndUuid(String targetGb, Integer targetCd, String uuid);

} // end interface
