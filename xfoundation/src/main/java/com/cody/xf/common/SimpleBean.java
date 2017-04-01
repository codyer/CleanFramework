/*
 * Copyright (c)  Created by Cody.yi on 2016/9/6.
 */

package com.cody.xf.common;

/**
 * Created by cody.yi on 2016/7/12.
 * 网络只返回简单的code和message的bean
 */
@NotProguard
public class SimpleBean extends Result<Object> {

    public SimpleBean(String code, String message) {
        super(code, message, null);
    }
}
