package com.cody.xf.common;

/**
 * Created by cody.yi on 2016/7/26.
 * 异常类
 */
public class NormalException extends RuntimeException {
    public NormalException() {
        super();
    }

    public NormalException(String detailMessage) {
        super(detailMessage);
    }
}
