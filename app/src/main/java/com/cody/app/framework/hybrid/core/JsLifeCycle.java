package com.cody.app.framework.hybrid.core;

import android.webkit.WebView;

/**
 * Created by cody.yi on 2018/7/11.
 * js生命周期
 */
public class JsLifeCycle {
    interface callback {
        String onStart = "javascript:try{if(window.__native_init){__native_init()}}catch(e){};";
        String onResume = "javascript:try{if(window.didAppear){didAppear(2)}}catch(e){};";
        //    String onResume = "javascript:try{if(window.didAppear){didAppear(1)}}catch(e){};";
        String onPause = "javascript:try{if(window.onPause){onPause()}}catch(e){};";
        String onDestroy = "javascript:try{if(window.onDestroy){onDestroy()}}catch(e){};";
    }

    public static void onStart(WebView webView) {
        if (webView == null)return;
        webView.loadUrl(callback.onStart);
    }

    public static void onResume(WebView webView) {
        if (webView == null)return;
        webView.loadUrl(callback.onResume);
    }

    public static void onPause(WebView webView) {
        if (webView == null)return;
        webView.loadUrl(callback.onPause);
    }

    public static void onDestroy(WebView webView) {
        if (webView == null)return;
        webView.loadUrl(callback.onDestroy);
        webView.stopLoading();
        webView.removeAllViews();
        webView.setTag(null);
        webView.clearHistory();
        webView.removeAllViews();
        webView.destroy();
    }
}
