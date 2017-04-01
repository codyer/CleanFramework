package com.cody.repository.framework.interaction;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by cody.yi on 2017/3/28.
 * 定义在Interaction的接口上，设置请求相对路径
 * 请求方式
 * 返回类结构
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface RequestMapping {
    /**
     * 请求相对路径
     */
    String value() default "";

    /**
     * 请求方式，默认get请求
     */
    RequestMethod method() default RequestMethod.GET;

    /**
     * 返回类型是普通类型还是列表，或者原始数据
     */
    ResultType type() default ResultType.BEAN;

}
