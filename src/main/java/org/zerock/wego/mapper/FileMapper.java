package org.zerock.wego.mapper;

import java.util.List;

import org.zerock.wego.domain.FileDTO;
import org.zerock.wego.domain.FileVO;


public interface FileMapper {
	
	public abstract List<FileVO> selectByTargetGbAndTargetCd(String targetGb, Integer targetCd); 
	public abstract Integer insert(FileDTO file); 
	public abstract Integer update(String targetGb, Integer targetCd, Integer fileId, String fileName);
	public abstract Integer deleteAll(String targetGb, Integer targetCd); 
	public abstract Integer deleteByTargetGbAndTargetCdAndUuid(String targetGb, Integer targetCd, String uuid);
	
} // end interface
