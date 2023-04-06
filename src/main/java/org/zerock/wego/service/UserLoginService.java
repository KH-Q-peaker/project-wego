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
public class UserLoginService 
	implements InitializingBean {
	
	@Autowired
    private UserMapper mapper;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.trace("afterPropertiesSet() invoked");
		try {
			Objects.requireNonNull(this.mapper);
		} 
		catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // afterPropertiesSet
	

    // 소셜로그인으로 가입
    public boolean signUp(UserDTO dto) throws ServiceException{
    	log.trace("socialJoin({}) invoked.", dto);
    	
		try {

			return this.mapper.insertUser(dto) == 1;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
    }// signUp

    // 로그인
    public UserVO getUserVOBySocialId(String socialId) throws ServiceException{
    	log.trace("socialLogin({}) invoked.", socialId);
    	
    	try {
    		
    		return mapper.selectUserBySocialId(socialId);
    		
    	}catch(Exception e) {
    		throw new ServiceException(e);
    	}// try-catch
    }// socialLogin
    
}// end class
