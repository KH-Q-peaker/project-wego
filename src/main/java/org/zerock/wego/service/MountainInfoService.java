package org.zerock.wego.service;

import java.util.List;
import java.util.Set;

import org.zerock.wego.domain.MountainInfoViewVO;
import org.zerock.wego.exception.ServiceException;

public interface MountainInfoService {
	
	// 게시물 전체목록 조회(LIST)
	public abstract List<MountainInfoViewVO> getList() throws ServiceException;
	// 10개의 게시물 랜덤 조회(LIST) - 메인페이지용
	public abstract Set<MountainInfoViewVO> getRandom10List() throws ServiceException;
	// 특정 게시물 상세조회(READ)
	public abstract MountainInfoViewVO get(Integer sanPartyId) throws ServiceException;
	// 산이름으로 산ID 조회
	public abstract Integer selectSanName(String sanName) throws ServiceException;
	
} // end interface
