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
public class UserServiceImpl 
	implements UserService, InitializingBean {
	
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
	

    // 카카오 가입
    @Override
    public boolean socialSignUp(UserDTO dto) throws ServiceException{
    	log.trace("socialJoin({}) invoked.", dto);
    	
		try {

			return this.userMapper.insertUser(dto) == 1;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
    }// socialJoin

    @Override
    public UserVO sociaLogin(String email) throws ServiceException{
    	log.trace("socialLogin({}) invoked.", email);
    	
    	try {
    		
    		return userMapper.selectUserBySocialId(email);
    		
    	}catch(Exception e) {
    		throw new ServiceException(e);
    	}// try-catch
    }// socialLogin
    
    @Override
    public UserVO getTargetUserInfo(Long targetUserId) {
    	log.trace("getTargetUserInfo({}) invoked.", targetUserId);
    	
    	try {
    		
    		return userMapper.selectUserByUserId(targetUserId);
    		
    	}catch(Exception e) {
    		throw new ServiceException(e);
    	}// try-catch
    }// kakaoLogin
    
    
//
//    @Override
//    public String findAuthBy(String userId) {
//        log.info("userid:: " + userId);
//        return mapper.findAuthBy(userId);
//    }
//
//    @Override
//    public String findUserIdBy2(String snsId) {
//        log.info("snsId:: " + snsId);
//        return mapper.findUserIdBy2(snsId);
//    }
//
//    @Override
//    public MemberVO findByUserId(String userid) {
//        // TODO Auto-generated method stub
//        return mapper.read(userid);
//    }
}// end class
