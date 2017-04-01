package com.cody.xf.interaction.framework;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by cody.yi on 2017/3/28.
 * 查询参数
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface QueryString {
    String value() default "";
}
