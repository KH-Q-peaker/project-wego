package org.zerock.wego.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.exception.AccessBlindException;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.exception.OperationFailException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {NullPointerException.class})
    public ModelAndView handleNullPointerException(Exception e) {
    	log.trace("NullPointerException *********************");
    	
        ModelAndView mav = new ModelAndView();
        
        mav.addObject("errorMessage", e.getMessage());
        mav.setViewName("error/error");
        
        return mav;
    }// handleNullPointerException
    
    @ExceptionHandler(value = {NotFoundPageException.class})
    public ModelAndView handleNotFoundPageException(Exception e) {
    	log.trace("NotFoundPageException *********************");
    	
        ModelAndView mav = new ModelAndView();
        
        mav.addObject("errorMessage", "해당 글을 찾을 수 없습니다 &#128546;");
        mav.setViewName("error/error");
        
        return mav;
    }// handleNullPointerException

    @ExceptionHandler(value = {AccessBlindException.class})
    public ModelAndView handleAccessBlindException(Exception e) {
    	log.trace("AccessBlindException *********************");
    	
        ModelAndView mav = new ModelAndView();
        
        mav.addObject("errorMessage", "&#9888; 신고로 블라인드 처리된 글입니다 &#9888;");
        mav.setViewName("error/error");
        
        return mav;
    }// handleNullPointerException
    
    @ExceptionHandler(value = {OperationFailException.class})
    public ModelAndView handleOperationFailException(Exception e) {
    	log.trace("OperationFailException *********************");
    	
        ModelAndView mav = new ModelAndView();
        
        mav.addObject("errorMessage", "&#9888; 작업중 문제가 발생어쩌고 다시시도 부탁  &#9888;");
        mav.setViewName("error/error");
        
        return mav;
    }// handleNullPointerException
    
    @ExceptionHandler(value = {Exception.class})
    public ModelAndView handleException(Exception e) {
    	
        ModelAndView mav = new ModelAndView();
        
        mav.addObject("errorMessage", e.getMessage());
        mav.setViewName("error/error");
        
        return mav;
    }// handleException
} // end class