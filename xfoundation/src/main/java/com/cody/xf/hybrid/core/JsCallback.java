package com.cody.xf.hybrid.core;

import android.webkit.WebView;

import com.cody.xf.hybrid.core.async.AsyncTaskExecutor;
import com.cody.xf.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.ref.WeakReference;
import java.util.Locale;

/**
 * native结果数据返回格式:
 *
 * var resultJs = {
 * code: '200',//200成功，400失败
 * message: '请求超时',//失败时候的提示，成功可为空
 * data: {}//数据
 * };
 *
 * Created by Cody.yi on 17/4/12.
 */
public class JsCallback {
    private static final String CALLBACK_JS_FORMAT = "javascript:JsBridge.onComplete(%s,%s);";

    private WeakReference<WebView> mWebViewWeakRef;
    private String mPort;
    private Gson mJsonUtil;

    private JsCallback(WebView webView, String port) {
        this.mWebViewWeakRef = new WeakReference<>(webView);
        this.mPort = port;
        mJsonUtil = new Gson();
    }

    public static JsCallback newInstance(WebView webView, String port) {
        return new JsCallback(webView, port);
    }

    /**
     * 返回结果给Js
     */
    public void callJs(JsResult result) {
        final WebView webView = mWebViewWeakRef.get();
        if (webView == null) {
            LogUtil.d("JsCallback", "The WebView related to the JsCallback has been recycled!");
        } else {
            final String callbackJs = getCallBackUrl(result);
            if (AsyncTaskExecutor.isMainThread()) {
                webView.loadUrl(callbackJs);
            } else {
                AsyncTaskExecutor.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl(callbackJs);
                    }
                });
            }
        }
    }

    /**
     * 直接返回失败消息，不需要包含data部分
     */
    public void failure(String message){
        JsResult<Object> result = new JsResult<>(JsCode.FAILURE, message, null);
        callJs(result);
        LogUtil.d("JsCallback", message);
    }

    /**
     * 直接返回成功消息，不需要包含data部分
     */
    public void success(String message){
        JsResult<Object> result = new JsResult<>(JsCode.SUCCESS, message, null);
        callJs(result);
    }

    /**
     * 直接返回成功消息，需要包含data部分
     */
    public void success(String message, JsonObject data){
        JsResult<JsonObject> result = new JsResult<>(JsCode.SUCCESS, message, data);
        callJs(result);
    }

    /**
     * 直接返回成功消息，需要包含data部分
     */
    public void success(JsonObject data){
        JsResult<JsonObject> result = new JsResult<>(JsCode.SUCCESS, null, data);
        callJs(result);
    }

    private String getCallBackUrl(JsResult result) {
        return String.format(Locale.getDefault(), CALLBACK_JS_FORMAT, mPort, mJsonUtil.toJson(result));
    }

}
