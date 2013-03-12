package com.searchengine.bool.anootation;

/**
 * Created with IntelliJ IDEA.
 * User: avvolkov
 * Date: 05.03.13
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */

@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Logging {
    java.lang.String value() default "";
}
