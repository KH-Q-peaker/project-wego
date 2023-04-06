package org.zerock.wego.service;

import java.util.List;

import org.zerock.wego.domain.FileDTO;
import org.zerock.wego.domain.FileVO;
import org.zerock.wego.exception.ServiceException;

public interface FileService {

	// 특정 파일 조회(READ) => 소속 게시판 및 게시글 번호 필요
	public abstract List<FileVO> getList(String targetGb, Integer targetCd) throws ServiceException;
	// 새로운 파일 등록(CREATE)
	public abstract boolean register(FileDTO dto) throws ServiceException;
	// 기존 파일 수정(UPDATE)
	public abstract boolean modify(String targetGb, Integer targetCd, Integer fileId, String fileName) throws ServiceException;
	// 기존 파일 삭제(DELETE)
	public abstract boolean remove(String targetGb, Integer targetCd) throws ServiceException;

} // end interface
