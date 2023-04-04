package org.zerock.wego.mapper;

import java.util.Set;

import org.zerock.wego.domain.MountainInfoViewVO;
import org.zerock.wego.domain.RecruitmentViewVO;
import org.zerock.wego.domain.ReviewViewVO;


public interface SearchMapper {
	
	public abstract Set<MountainInfoViewVO> selectSearchMountainInfo(String search);
	public abstract Set<RecruitmentViewVO> selectSearchRecruitment(String search);
	public abstract Set<ReviewViewVO> selectSearchReview(String search);
	
} // end interface
