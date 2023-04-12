package org.zerock.wego.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.FileDTO;
import org.zerock.wego.domain.FileVO;


public interface FileMapper {
	
	public abstract List<FileVO> select(@Param("targetGb")String targetGb, @Param("targetCd")Integer targetCd); // 파일 조회
	public abstract Integer insert(FileDTO file); // 신규 파일 등록
	public abstract Integer update(String targetGb, Integer targetCd, Integer fileId, String fileName); // 기존 파일 수정
	
	// 타겟의 첨부파일 개수 조회 
	public abstract Integer selectTotalCountByTarget(@Param("targetGb")String targetGb, @Param("targetCd")Integer targetCd);
	public abstract Integer deleteByTarget(@Param("targetGb")String targetGb, @Param("targetCd")Integer targetCd); // 파일 삭제
	
} // end interface
