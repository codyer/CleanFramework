/*
 * ************************************************************
 * 文件：EventScope.java  模块：core  项目：CleanFramework
 * 当前修改时间：2019年03月31日 18:21:19
 * 上次修改时间：2019年03月31日 18:11:29
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.live.event.bus.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Cody.yi on 2019/3/31.
 * 定义事件范围，注释在枚举上，可以给范围取一个名字
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventScope {
    /**
     * 默认范围描述
     */
    String value() default "";

    /**
     * 同value作用一样，为了匹配实际意义添加
     */
    String name() default "";

    /**
     * 是否激活,可以根据需要配置是否激活事件分发，eg：debug开启，release关闭
     */
    boolean active() default true;
}
