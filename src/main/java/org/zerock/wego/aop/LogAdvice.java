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

	@Around("execution(* org.zerock.wego.service.*Service.*(..))")
	public Object logging(ProceedingJoinPoint pjp) throws Throwable{

		String ClassName = pjp.getSignature().getDeclaringTypeName();
		String MethodName = pjp.getSignature().getName();
		
		log.trace(">> {}.{} invoked...", ClassName, MethodName);
		
//		long start = System.nanoTime();
		long start = System.currentTimeMillis();
		
		Object result = pjp.proceed();
		
//		long end = System.nanoTime();
		long end = System.currentTimeMillis();
		
//		log.info(">> runtime = {} ns", (end - start));
		log.info(">>>>>> runtime = {} ms", (end - start));
//		log.info(":: result = {}", result);
		
		return result;
	}// logging
}//end class

