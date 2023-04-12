package org.zerock.wego.mapper;

import java.util.Set;

import org.zerock.wego.domain.SanInfoViewVO;
import org.zerock.wego.domain.PartyViewVO;
import org.zerock.wego.domain.ReviewViewVO;


public interface SearchMapper {
	
	public abstract Set<SanInfoViewVO> selectSearchSanInfo3ByQuery(String query);
	public abstract Set<PartyViewVO> selectSearchParty3ByQuery(String query);
	public abstract Set<ReviewViewVO> selectSearchReview3ByQuery(String query);
	
} // end interface
