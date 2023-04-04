package org.zerock.wego.service;

import java.util.Set;

import org.zerock.wego.domain.MountainInfoViewVO;
import org.zerock.wego.domain.RecruitmentViewVO;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.exception.ServiceException;

public interface SearchService {

	// 통합검색결과 조회(산 => 산이름, 상세 / 모집, 후기 => 제목, 내용)
	public abstract Set<MountainInfoViewVO> selectSearchMountainInfo(String search) throws ServiceException;
	public abstract Set<RecruitmentViewVO> selectSearchRecruitment(String search) throws ServiceException;
	public abstract Set<ReviewViewVO> selectSearchReview(String search) throws ServiceException;

} // end interface
