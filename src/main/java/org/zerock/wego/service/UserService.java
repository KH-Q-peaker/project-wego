package org.zerock.wego.service;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;




@Log4j2
@RequiredArgsConstructor

@Service
public class UserService {
	
    private final UserMapper userMapper;
    
	
    public UserVO getById(Integer userId) {
    	log.trace("getTargetUserInfo({}) invoked.", userId);
    	
    	try {
    		
    		return userMapper.selectByUserId(userId);
    		
    	} catch(Exception e) {
    		throw new ServiceException(e);
    	}// try-catch
    	
    }// kakaoLogin
    
    
}// end class
