package com.searchengine.bool.aspect;

import com.searchengine.bool.anootation.LoggingAfter;
import com.searchengine.bool.anootation.LoggingBefore;
import com.searchengine.bool.web.config.ApplicationService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.lang.System.out;

/**
 * Created with IntelliJ IDEA.
 * User: avvolkov
 * Date: 05.03.13
 * Time: 19:16
 * To change this template use File | Settings | File Templates.
 */

@Aspect
public class LoggingInterceptor {

    private static DateFormat dateFormat;
//    private static Calendar calendar;

    static {
        dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
//        calendar   = Calendar.getInstance();
    }

    @Pointcut("execution(* com.searchengine.bool..*(..))")
    public void logging() {}

    @Before("logging() && @annotation(loggingbefore)")
    public void beforeLoggingHandler(JoinPoint jp, LoggingBefore loggingbefore) {
        out.println("[" + dateFormat.format(Calendar.getInstance().getTime()) + "]: " +
            loggingbefore.value()); // + jp.getSignature().getDeclaringType());
    }

    @After("logging() && @annotation(loggingafter)")
    public void afterLoggingHandler(JoinPoint jp, LoggingAfter loggingafter) {
        out.println("[" + dateFormat.format(Calendar.getInstance().getTime()) + "]: " +
                loggingafter.value()); // + jp.getSignature().getDeclaringType());
    }

    @Before("within(com.searchengine.bool.web.job.*) && target(com.searchengine.bool.web.config.ApplicationService)")
    public void lgginngHandler(JoinPoint jp) {
        System.out.println("lgginngHandler |-+>");
    }

    @AfterThrowing(
            pointcut="execution(public * com.searchengine.bool.web.config.ApplicationServiceImpl.get*(..))",
            throwing="ex")
    public void throwingExceptionHandler(JoinPoint jp, Exception ex) {
        System.out.println("throwingExceptionHandler |-+>");
//        e.printStackTrace();
//        logger.error("Error at retrieving message from messageSource: ", e.getCause());
    }
}
