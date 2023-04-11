package org.zerock.wego.mapper;

import java.util.List;

import org.zerock.wego.domain.FileDTO;
import org.zerock.wego.domain.FileVO;


public interface FileMapper {
	
	public abstract List<FileVO> select(String targetGb, Integer targetCd); // 파일 조회
	public abstract Integer insert(FileDTO file); // 신규 파일 등록
	public abstract Integer update(String targetGb, Integer targetCd, Integer fileId, String fileName); // 기존 파일 수정
	public abstract Integer deleteAll(String targetGb, Integer targetCd); // 파일 삭제
	public abstract Integer delete(String targetGb, Integer targetCd, String uuid); // 특정 파일 삭제
	
} // end interface
