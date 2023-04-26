package org.zerock.wego.aop;

import org.aspectj.lang.ProceedingJoinPoint;
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

	@Around("execution(* org.zerock.wego.service.**.*Service.*(..))")
	public Object logging(ProceedingJoinPoint pjp) throws Throwable{

		String className = pjp.getSignature().getDeclaringTypeName();
		String methodName = pjp.getSignature().getName();
		
		log.trace(">>>> {}.{} invoked...", className, methodName);
		
		long start = System.currentTimeMillis();
		
		Object result = pjp.proceed();
		
		long end = System.currentTimeMillis();
		
		log.info(">>>> {}.{} >>>> runtime = {} ms", className, methodName,  (end - start));
		
		return result;
	}// logging
}//end class

