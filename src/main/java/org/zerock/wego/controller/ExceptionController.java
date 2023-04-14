package org.zerock.wego.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.exception.NotFoundPageException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {NullPointerException.class})
    public ModelAndView handleNullPointerException(Exception e) {
    	
        ModelAndView mav = new ModelAndView();
        
        mav.addObject("errorMessage", "NullPointerException");
        mav.setViewName("error/null");
        
        return mav;
    }// handleNullPointerException
    
    
    @ExceptionHandler(value = {NotFoundPageException.class})
    public ModelAndView handleNotFoundPageException(Exception e) {
    	
        ModelAndView mav = new ModelAndView();
        
        mav.addObject("errorMessage", "NotFoundPageException");
        mav.setViewName("error/notFoundPage");
        
        return mav;
    }// handleNullPointerException

    
    @ExceptionHandler(value = {Exception.class})
    public ModelAndView handleException(Exception e) {
    	
        ModelAndView mav = new ModelAndView();
        
        mav.addObject("errorMessage", "Unknown exception");
        mav.setViewName("error");
        
        return mav;
    }// handleException
} // end class