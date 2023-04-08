package org.zerock.wego.service;

import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.UserDTO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.domain.oauth.kakao.KakaoUserInfoDTO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.UserMapper;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;




@Log4j2
@NoArgsConstructor

@Service
public class UserService
	implements InitializingBean {
	
	@Autowired
    private UserMapper userMapper;
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.trace("afterPropertiesSet() invoked");
		try {
			Objects.requireNonNull(this.userMapper);
		} 
		catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // afterPropertiesSet

	
    public UserVO getById(Integer userId) {
    	log.trace("getTargetUserInfo({}) invoked.", userId);
    	
    	try {
    		
    		return userMapper.selectByUserId(userId);
    		
    	}catch(Exception e) {
    		throw new ServiceException(e);
    	}// try-catch
    	
    }// kakaoLogin
    
    
    public UserDTO parseUserDTO(KakaoUserInfoDTO userInfoDTO) {
    	
    	UserDTO userDTO = new UserDTO();
    	
    	String nickname = userInfoDTO.getProperties().getNickname();
		String socialId = userInfoDTO.getKakao_account().getEmail();
		
		userDTO.setNickname(nickname);
		userDTO.setSocialId(socialId);
 
		return userDTO;
		
    } // parseUserDTO

    
}// end class
