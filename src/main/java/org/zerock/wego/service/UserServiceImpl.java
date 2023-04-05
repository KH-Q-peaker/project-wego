package org.zerock.wego.service;

import java.io.InputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.UserDTO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.UserMapper;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;




@Log4j2
@NoArgsConstructor

@Service
public class UserServiceImpl implements UserService{
	
    @Autowired
    private UserMapper mapper;
    
//    @Autowired @Qualifier("BCryptPasswordEncoder")
//    private PasswordEncoder encoder;
    
	/* 실ㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹ험ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ*/
	@Autowired
	private ApplicationContext appContext;
	

    // 카카오 가입
    @Override
    public boolean socialJoin(UserDTO dto) throws ServiceException{
    	log.trace("kakaoJoin({}) invoked.", dto);
    	
		try {

			return this.mapper.insertUser(dto) == 1;
//			mapper.authorize(dto);// 머하는놈이야?????????????????
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
    }// kakaoJoin

    
    
    @Override
    public UserVO socialLogin(String email) throws ServiceException{
    	log.trace("kakaoLogin({}) invoked.", email);
    	
    	try {
    		
    		return mapper.selectUserByUserId(email);
    		
    	}catch(Exception e) {
    		throw new ServiceException(e);
    	}// try-catch
    }

    
    // 유저 프로필 사진 조회 
	@Override
	public String getUserPic(String userPic) throws ServiceException {

		try {

			Resource img;

			if (userPic == null) {

				img = appContext.getResource("/resources/img/default-profile2.png");
			} else {

				img = appContext.getResource(userPic);
			} // if-else
			/* 이거 근데 파일 못찾을 경우도 걸러야됨 */

			@Cleanup
			InputStream inputStream = img.getInputStream();
			byte[] imgBytes = inputStream.readAllBytes();

			String base64 = Base64.getEncoder().encodeToString(imgBytes);

			return base64;

		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch

	}



	
	
}// end class
