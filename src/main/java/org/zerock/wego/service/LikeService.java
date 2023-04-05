package org.zerock.wego.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.Target;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.LikeMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;



/*
 * 좋아요를 누른 유저다
 * - 하트에 클래스 추가한다
 * - 눌렀을 때 좋아요 취소가 되어야 한다
 * 
 * 좋아요를 눌렀는지를 어떻게 확인하나?
 * - 좋아요 테이블을 조회해야지 뭐...
 * - 글을 띄워주기 전에 확인해야한다 
 * 
 * 그리고 좋아요를 눌렀을 때 숫자를 비동기로 반영해야 한다
 * - 좋아요 눌렀을때 / 취소했을 때 비동기로 보내고
 *   그 결과를 해당 글 좋아요 개수로 받아서
 *   옆에 띄워줘야 한다
 *   
 *   1. 글 조회할때 확인 한번 =  해당 유저가 좋아요 한앤지 
 *   2. 좋아요 누를 때 
 * */
@Log4j2
@NoArgsConstructor

@Service
public class LikeService {
	
    @Setter(onMethod_= {@Autowired})
    private LikeMapper mapper;
    
    
    
    // 타겟 좋아요 수 조회
    public Integer getLikeCnt(Target cri) throws ServiceException{
    	
    	try {
    		Integer likeCnt = this.mapper.selectLikeCountByTarget(cri);
    		
    		return likeCnt;
    	} catch(Exception e) {
    		throw new ServiceException(e);
    	}// try-catch
    }// countLike
    
    
    
    // 유저 좋아요 조회
    public boolean isUserLike(Target cri, Long userId) throws ServiceException{
    	
    	try {
    		
    		return this.mapper.selectLikedTargetByUserId(cri, userId) != null;
    		
    	} catch(Exception e) {
    		throw new ServiceException(e);
    	}// try-catch
    }// isUserLike
    
    
    
	// 좋아요 토글 
    public boolean isToggleLike(Target cri, Long userId) throws ServiceException{
    	
    	try {
    		
    		if(this.mapper.selectLikedTargetByUserId(cri, userId) != null) {
    			
    			return this.mapper.deleteLike(cri, userId) == 1;
    			
    		}else {
    			
    			return this.mapper.insertLike(cri, userId) == 1;
    		
    		}// if-else
    	} catch(Exception e) {
    		throw new ServiceException(e);
    	}// try-catch
    }// isLike

	
	
}// end class
