package org.zerock.wego.service.mypage;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.mypage.FileDTO;
import org.zerock.wego.domain.mypage.WegoUserTbDTO;
import org.zerock.wego.mapper.mypage.ProfileFileMapper;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Service
public class ProfileUploadServiceImpl implements ProfileUploadService {
	
	@Inject
	private ProfileFileMapper mapper;
	

	@Override
	public boolean fileSave(FileDTO dto) {
		log.trace("fileSave({}) invoked", dto);
		return mapper.fileInsert(dto) == 1;
	}
	
	@Override
	public boolean updateProfile(WegoUserTbDTO dto) {
		log.trace("updateProfile({}) invoked",dto);
		return mapper.updateProfile(dto) == 1;
	}

	@Override
	public String selectProfilePath(String user_id) {
		log.trace("selectProfilePath({}) invoked", user_id);
		return this.mapper.selectProfile(user_id);
	}

}//end class
