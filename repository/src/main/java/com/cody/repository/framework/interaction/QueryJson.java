package com.cody.repository.framework.interaction;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by cody.yi on 2017/3/28.
 * 方法查询json格式的参数
 * @see com.google.gson.JsonObject
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface QueryJson {
}
