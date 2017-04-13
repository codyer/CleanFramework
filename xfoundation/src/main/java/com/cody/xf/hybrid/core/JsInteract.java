package com.cody.xf.hybrid.core;

import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.cody.xf.hybrid.JsBridge;

import java.lang.reflect.Method;

/**
 * Created by Cody.yi on 17/4/12.
 * JsInteract
 */
public class JsInteract {
    private static final String JS_BRIDGE_PROTOCOL_SCHEMA = "js_bridge";
    private String mHandlerName;
    private String mMethodName;
    private String mPort;
    private JsonObject mParams;
    private JsCallback mJsCallback;

    private JsInteract() {
    }

    public static JsInteract newInstance() {
        return new JsInteract();
    }

    /**
     * @param webView WebView
     * @param message js_bridge://class:port/method?params
     */
    public void callNative(WebView webView, String message) {
        if (webView == null || TextUtils.isEmpty(message))
            return;
        parseMessage(webView, message);
        invokeNativeMethod(webView);
    }

    private void parseMessage(WebView webView, String message) {
        if (!message.startsWith(JS_BRIDGE_PROTOCOL_SCHEMA))
            return;
        Uri uri = Uri.parse(message);
        mHandlerName = uri.getHost();
        String path = uri.getPath();
        if (!TextUtils.isEmpty(path)) {
            mMethodName = path.replace("/", "");
        } else {
            mMethodName = "";
        }
        mPort = String.valueOf(uri.getPort());
        mJsCallback = JsBridge.getJsCallback(webView, mPort);
        try {
            mParams = new JsonParser().parse(uri.getQuery()).getAsJsonObject();
        } catch (JsonParseException e) {
            e.printStackTrace();
            String msg = "Query parameter is invalid json :" + e.getCause().toString();
            mJsCallback.failure(msg);
        }
    }

    private void invokeNativeMethod(WebView webView) {
        Method method = JsBridge.findMethod(mHandlerName, mMethodName);

        if (method == null) {
            String msg = "Method (" + mMethodName + ") in this class (" + mHandlerName + ") not found!";
            mJsCallback.failure(msg);
            return;
        }

        Object[] args = new Object[3];
        args[0] = webView;
        args[1] = mParams;
        args[2] = mJsCallback;

        try {
            method.invoke(null, args);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getCause().toString();
            mJsCallback.failure(msg);
        }
    }
}
