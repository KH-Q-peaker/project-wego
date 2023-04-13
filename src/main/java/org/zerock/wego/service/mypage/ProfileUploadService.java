package org.zerock.wego.service.mypage;

import org.zerock.wego.domain.mypage.FileDTO;
import org.zerock.wego.domain.mypage.WegoUserTbDTO;

public interface ProfileUploadService {
	
	//파일이 업로드되면 프로필 db 업데이트
	public boolean updateProfile(WegoUserTbDTO dto);
	
	//파일이 업로드되면 파일db에 인서트
	public boolean fileSave(FileDTO dto);
	
	//유저아이디로 저장된 프로필path 구하기
	public String selectProfilePath(String user_id);
}//end interface
