package com.cody.app.framework.hybrid.core;

import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.cody.app.framework.hybrid.JsBridge;
import com.cody.handler.framework.viewmodel.HtmlViewModel;
import com.cody.xf.utils.LogUtil;

/**
 * Created by Cody.yi on 17/4/12.
 * JsWebChromeClient
 */
public class JsWebChromeClient extends WebChromeClient {
    private HtmlViewModel mViewModel;

    public JsWebChromeClient(HtmlViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public final boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult
            result) {
        result.confirm();
        if ((UrlUtil.isInnerLink(url)) && JsBridge.callNative(view, message)) {
            LogUtil.d(message);
            return true;
        }
        return super.onJsPrompt(view, url, message, defaultValue, result);
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
        mViewModel.setProgress(newProgress);
    }
}
