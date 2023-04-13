package org.zerock.wego.service.mypage;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.mypage.ACriteria;
import org.zerock.wego.domain.mypage.PCriteria;
import org.zerock.wego.domain.mypage.TsanPartyVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.mypage.MyClimbMapper;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Service
public class MyClimbServiceImpl implements MyClimbService {
	
	@Inject
	private MyClimbMapper mapper;

	@Override
	public List<TsanPartyVO> availableParty(Integer userId, ACriteria acri) throws ServiceException {
		log.trace("availableParty({}) invoked",userId);
		try {
			return this.mapper.availablePartySelect((userId),acri.getACurrPage(),acri.getAAmount());
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}//availableParty

	@Override
	public List<TsanPartyVO> pastParty(Integer userId,PCriteria pcri) throws ServiceException {
		log.trace("pastParty({}) invoked",userId);
		try {
			return this.mapper.pastPartySelect(userId,pcri.getPCurrPage(),pcri.getPAmount());
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Integer getTotalAmountByMyAvailableParty(Integer userId) throws ServiceException {
		log.trace("getTotalAmountByMyAvailableParty({}) invoked.",userId);
		try {
			int totalCount = this.mapper.selectTotalCountByMyAvailableParty(userId);
			return totalCount;
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}

	@Override
	public Integer getTotalAmountByMyPastParty(Integer userId) throws ServiceException {
		log.trace("getTotalAmountByMyPastParty({}) invoked.",userId);
		try {
			int totalCount = this.mapper.selectTotalCountByMyPastParty(userId);
			return totalCount;
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}//pastParty

}//end class
