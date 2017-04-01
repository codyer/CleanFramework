package com.cody.xf.utils.http;

/**
 * Created by cody.yi on 2016/7/21.
 * http请求异常
 */
public class HttpConnectException extends RuntimeException {
    private String mCode;  //异常对应的返回码
    private String mMessage;  //异常对应的描述信息

    public HttpConnectException() {
        super();
    }

    public HttpConnectException(String message) {
        super(message);
        mMessage = message;
    }

    public HttpConnectException(String code, String message) {
        super(message);
        this.mCode = code;
        this.mMessage = message;
    }

    public String getCode() {
        return mCode;
    }

    public String getMessage() {
        return mMessage;
    }
}
