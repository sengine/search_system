package com.searchengine.bool.web.config;

import com.searchengine.bool.anootation.Logging;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: avvolkov
 * Date: 05.03.13
 * Time: 18:45
 * To change this template use File | Settings | File Templates.
 */

@Component("applicationContext")
public class ApplicationServiceImpl implements ApplicationContextAware, ApplicationService {

    private static Logger logger = Logger.getLogger(ApplicationServiceImpl.class);

    private static ApplicationContext ctx           = null;
    private static MessageSource      messageSource = null;

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    public static void setAppContext(ApplicationContext ctx) throws BeansException {
        ApplicationServiceImpl.ctx = ctx;
    }

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }

    public static Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

    public Object getBean(Class beanClass) {
        return ctx.getBean(beanClass);
    }

    public String getMessage(String name, Object[] objects) throws Exception {

        throw new FatalBeanException("sdsdsdsdsd");

//        if (messageSource == null) {
//            messageSource = (MessageSource) ctx.getBean("messageSource");
//        }

//        return messageSource.getMessage("name",
//                objects, Locale.getDefault());
    }
}

