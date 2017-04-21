package com.cody.xf.hybrid.core;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Cody.yi on 17/4/12.
 * JsWebViewClient
 */
public class JsWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
