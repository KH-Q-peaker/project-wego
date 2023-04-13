package org.zerock.wego.mapper.mypage;

import org.zerock.wego.domain.mypage.WegoUserTbDTO;

public interface MyinfoMapper {
	
	//update userTbDTO
	public abstract Integer updateMyInfo(WegoUserTbDTO dto);
	
	//delete user
	public abstract Integer deleteMyAccount(String user_id);

}//end interface
