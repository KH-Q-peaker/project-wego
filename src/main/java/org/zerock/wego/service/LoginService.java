package org.zerock.wego.service;

import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.UserDTO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.UserMapper;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;




@Log4j2
@NoArgsConstructor

@Service
public class LoginService 
	implements InitializingBean {
	
	@Autowired
    private UserMapper userMapper;
	public void afterPropertiesSet() throws Exception {
		log.trace("afterPropertiesSet() invoked");
		
		try {
			Objects.requireNonNull(this.userMapper);
		} 
		catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // afterPropertiesSet

	
	public UserVO login(UserDTO userDTO) throws ServiceException{
    	log.trace("login({}) invoked.", userDTO);
    	
    	try {
    		String targetUserSocialId = userDTO.getSocialId();
    		
    		UserVO userVO = this.socailLogin(targetUserSocialId);
    		
    		boolean isAlreadySignUp = userVO != null;
    		
    		if(isAlreadySignUp) {
    			
    			return userVO;
    		}
    		else {
    			
    			return null;
			}
    		
    	}catch(Exception e) {
    		throw new ServiceException(e);
    	}// try-catch
    }// socialLogin
	

    // 회원가입 가입
    public boolean signUp(UserDTO dto) throws ServiceException{
    	log.trace("signUp({}) invoked.", dto);
    	
		try {

			return this.userMapper.insertUser(dto) == 1;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
    }// signUp

    
    // 중복 닉네임 확인
    public boolean isExistNickname(String nickname) throws ServiceException{
    	log.trace("isExistNickname({}) invoked.", nickname);
    	
		try {

			return this.userMapper.selectByNickname(nickname) != null;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
    }// isExistNickname
    
    
    // 소셜 아이디로 로그인
    public UserVO socailLogin(String socialId) throws ServiceException{
    	log.trace("loginBySocialId({}) invoked.", socialId);
    	
    	try {
    		
    		return userMapper.selectBySocialId(socialId);
    		
    	}catch(Exception e) {
    		throw new ServiceException(e);
    	}// try-catch
    }// loginBySocialId
    
}// end class
