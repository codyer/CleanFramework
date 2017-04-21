package com.cody.xf.hybrid.core;

import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.cody.xf.hybrid.JsBridge;

/**
 * Created by Cody.yi on 17/4/12.
 * JsWebChromeClient
 */
public class JsWebChromeClient extends WebChromeClient {
    private JsBridge.OnProgressListener mOnProgressListener;

    public JsWebChromeClient(JsBridge.OnProgressListener onProgressListener) {
        mOnProgressListener = onProgressListener;
    }

    @Override
    public final boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        result.confirm();
        JsBridge.callNative(view,message);
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (mOnProgressListener != null) {
            mOnProgressListener.onProgress(newProgress);
        }
    }
}
