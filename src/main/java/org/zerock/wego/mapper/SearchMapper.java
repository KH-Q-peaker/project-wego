package org.zerock.wego.mapper;

import java.util.Set;

import org.zerock.wego.domain.SanInfoViewVO;
import org.zerock.wego.domain.PartyViewVO;
import org.zerock.wego.domain.ReviewViewVO;


public interface SearchMapper {
	
	public abstract Set<SanInfoViewVO> selectSearchSanInfo3(String search);
	public abstract Set<PartyViewVO> selectSearchParty3(String search);
	public abstract Set<ReviewViewVO> selectSearchReview3(String search);
	
} // end interface
