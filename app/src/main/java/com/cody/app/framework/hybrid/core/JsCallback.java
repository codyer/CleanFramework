package com.cody.app.framework.hybrid.core;

import android.text.TextUtils;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.cody.app.framework.hybrid.core.async.AsyncTaskExecutor;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.http.Result;

import java.lang.ref.WeakReference;
import java.util.Locale;

/**
 * native结果数据返回格式:
 * <p>
 * var resultJs = {
 * code: '200',//200成功，400失败
 * message: '请求超时',//失败时候的提示，成功可为空
 * data: {}//数据
 * };
 * <p>
 * Created by Cody.yi on 17/4/12.
 */
public class JsCallback {
    private static final String CALLBACK_JS_FORMAT = "javascript:JsBridge.onComplete(%s,%s);";
    private static final String CALLBACK_JS_OLD_FORMAT = "javascript:_app_callback('%s','%s');";
    private static final String CALLBACK_JS_PROTOCOL_PREFIX = "app_call_prefix";

    private WeakReference<WebView> mWebViewWeakRef;
    private String mPort;
    private Gson mJsonUtil;
    private String mCallBackFormat = CALLBACK_JS_FORMAT;

    private JsCallback(WebView webView, String port) {
        this.mWebViewWeakRef = new WeakReference<>(webView);
        mJsonUtil = new Gson();
        if (TextUtils.isEmpty(port))return;
        if (port.contains(CALLBACK_JS_PROTOCOL_PREFIX)){
            mCallBackFormat = CALLBACK_JS_OLD_FORMAT;
        }else {
            mCallBackFormat = CALLBACK_JS_FORMAT;
        }
        this.mPort = port.replace(CALLBACK_JS_PROTOCOL_PREFIX,"");
    }

    public static JsCallback newInstance(WebView webView, String port) {
        return new JsCallback(webView, port);
    }

    /**
     * 返回结果给Js
     */
    private void callJs(final String callbackJs) {
        final WebView webView = mWebViewWeakRef.get();
        if (webView == null) {
            LogUtil.d("JsCallback", "The WebView related to the JsCallback has been recycled!");
        } else {
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
     * 直接返回消息，不需要包含code,message 结构化，兼容老定义
     */
    public void oldFormat(JsonObject result) {
        if (result == null)return;
        callJs(getCallBackUrl(result));
        LogUtil.d("JsCallback", result.toString());
    }

    /**
     * 直接返回失败消息，不需要包含data部分
     */
    public void failure(String message) {
        Result<Object> result = new Result<>(JsCode.FAILURE, message, null);
        callJs(getCallBackUrl(result));
        LogUtil.d("JsCallback", message);
    }

    /**
     * 直接返回成功消息，不需要包含data部分
     */
    public void success(String message) {
        Result<Object> result = new Result<>(JsCode.SUCCESS, message, null);
        callJs(getCallBackUrl(result));
    }

    /**
     * 直接返回成功消息，需要包含data部分
     */
    public void success(String message, JsonObject data) {
        Result<JsonObject> result = new Result<>(JsCode.SUCCESS, message, data);
        callJs(getCallBackUrl(result));
    }

    /**
     * 直接返回成功消息，需要包含data部分
     */
    public void success(JsonObject data) {
        Result<JsonObject> result = new Result<>(JsCode.SUCCESS, null, data);
        callJs(getCallBackUrl(result));
    }

    /**
     * 直接返回成功消息，需要包含data部分
     */
    public void callback(JsonObject result) {
        callJs(getCallBackUrl(result));
    }

    private String getCallBackUrl(Result result) {
        return String.format(Locale.getDefault(), mCallBackFormat, mPort, mJsonUtil.toJson(result));
    }

    private String getCallBackUrl(JsonObject result) {
        return String.format(Locale.getDefault(), mCallBackFormat, mPort, result);
    }
}
