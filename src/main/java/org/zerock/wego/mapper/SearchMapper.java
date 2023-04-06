package org.zerock.wego.mapper;

import java.util.Set;

import org.zerock.wego.domain.SanInfoViewVO;
import org.zerock.wego.domain.PartyViewVO;
import org.zerock.wego.domain.ReviewViewVO;


public interface SearchMapper {
	
	public abstract Set<SanInfoViewVO> selectSearchMountainInfo(String search);
	public abstract Set<PartyViewVO> selectSearchRecruitment(String search);
	public abstract Set<ReviewViewVO> selectSearchReview(String search);
	
} // end interface
