package com.searchengine.bool.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import javax.annotation.Resource;

@Resource
@Aspect
public class ControllerInterceptor {

//	private String message;
//

    @Pointcut("execution(* com.searchengine.bool.web..*(..))")
	public void interceptController() {}

	@Around("interceptController()")
	public Object advice(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Interception!" + pjp.getSignature().getDeclaringTypeName());
		return pjp.proceed();
	}
}
