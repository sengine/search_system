package com.searchengine.bool.aspect;

import com.searchengine.bool.web.config.ApplicationServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: avvolkov
 * Date: 06.03.13
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */
@Aspect
public class ApplicationServiceInterceptor {

    @Pointcut("execution(public void com.searchengine.bool.web.config.ApplicationServiceImpl.setAppContext(*)) " +
            "&& args(ctx, ..)")
    public void initialize(ApplicationContext ctx) {}

    @Around("initialize(ctx)")
    public void initHandler(ProceedingJoinPoint pjp, ApplicationContext ctx) {
        pjp.getThis();
    }


}
