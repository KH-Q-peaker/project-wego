package org.zerock.wego.service.mypage;

import java.util.List;

import org.zerock.wego.domain.mypage.ACriteria;
import org.zerock.wego.domain.mypage.PCriteria;
import org.zerock.wego.domain.mypage.TsanPartyVO;
import org.zerock.wego.exception.ServiceException;

public interface MyClimbService {
	
	public abstract List<TsanPartyVO> availableParty(Integer userId, ACriteria acri) throws ServiceException;
	
	public abstract List<TsanPartyVO> pastParty(Integer userId,PCriteria pcri) throws ServiceException;
	
	public abstract Integer getTotalAmountByMyAvailableParty(Integer userId) throws ServiceException;
	
	public abstract Integer getTotalAmountByMyPastParty(Integer userId) throws ServiceException;
}//end interface

