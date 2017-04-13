package com.cody.xf.hybrid.core;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cody.yi on 2017/4/12.
 * Js请求包裹类JsResult
 */
public class JsResult<T> {

    /**
     * String 操作状态码
     */
    private String code;

    /**
     * String 操作提示信息
     */
    private String message;

    /**
     * data	返回数据
     */
    @SerializedName(value = "data", alternate = {"dataMap", "dataReturn"})
    private T data;

    public JsResult() {
    }

    public JsResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        if (code == null) {
            return JsCode.FAILURE;
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        if (message == null) {
            return "no message!";
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
