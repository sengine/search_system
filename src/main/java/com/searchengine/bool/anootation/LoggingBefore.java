package com.searchengine.bool.anootation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: avvolkov
 * Date: 05.03.13
 * Time: 17:34
 * To change this template use File | Settings | File Templates.
 */

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoggingBefore {
    java.lang.String value() default "";
}

