package org.zerock.wego.service.mypage;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.mypage.WegoUserTbDTO;
import org.zerock.wego.mapper.mypage.MyinfoMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2

@Service
public class MyinfoServiceImpl implements MyInfoService {

	private MyinfoMapper mapper;
	
	@Override
	public boolean setMyinfo(WegoUserTbDTO dto) {
		
		return this.mapper.updateMyInfo(dto) == 1;
	}

	@Override
	public boolean withDrawMyAccount(String user_id) {
		return this.mapper.deleteMyAccount(user_id) ==1;
	}

}//end class
