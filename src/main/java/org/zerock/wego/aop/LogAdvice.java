package org.zerock.wego.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Aspect
@Component
public class LogAdvice {

	@Around("execution(* org.zerock.wego.service.*Service.*(..))")
	public Object logging(ProceedingJoinPoint pjp) throws Throwable{
		String className = pjp.getTarget().getClass().getCanonicalName();
		Signature methodSignature = pjp.getSignature();
		String args = Arrays.toString(pjp.getArgs());
		
		
		log.trace("^^^^^^^^^^^^^^^^^^^^^^^^^ invoked ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//		log.trace(":: {}::{}({}) invoked.", className, methodName, args);
		log.trace(":: CLASS = {}", className);
		log.trace(":: METHOD = {}",methodSignature.toShortString());
		log.trace(":: ARGS = {}", args);
		log.trace("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		
		Object result = pjp.proceed();
		log.info(":: result = {}", result);
		
		return result;
	}// around
	
}//end class

