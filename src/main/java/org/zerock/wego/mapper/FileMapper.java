package org.zerock.wego.mapper;

import java.util.List;

import org.zerock.wego.domain.FileDTO;
import org.zerock.wego.domain.FileVO;


public interface FileMapper {
	
	public abstract List<FileVO> selectByTargetGbAndTargetCd(String targetGb, Integer targetCd); 
	public abstract Integer insert(FileDTO file); 
	public abstract Integer update(FileDTO file);
	public abstract Integer deleteAll(FileDTO file); 
	public abstract Integer delete(FileDTO file);
	
} // end interface
