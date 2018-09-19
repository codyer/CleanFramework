package com.cody.app.framework.hybrid.core;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cody.app.R;
import com.cody.app.framework.activity.HtmlActivity;
import com.cody.handler.framework.viewmodel.HtmlViewModel;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.ResourceUtil;

/**
 * Created by Cody.yi on 17/4/12.
 * JsWebViewClient
 */
public class JsWebViewClient extends WebViewClient {
    private HtmlViewModel mViewModel;

    public JsWebViewClient(HtmlViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (TextUtils.isEmpty(url)) return true;
        if (url.contains("tel:") || url.contains("phone:")) {
            url = url.replace("phone:", "").replace("tel:", "");
            ActivityUtil.openDialPage(url);
            return true;
        }
        //从内部链接跳到外部链接打开新的html页面
        if (UrlUtil.isInnerLink(mViewModel.getUrl())
                && !UrlUtil.isInnerLink(url)) {
            HtmlActivity.startHtml(ResourceUtil.getString(R.string.app_name), url);
        } else {
            mViewModel.setUrl(url);
            view.loadUrl(url);
        }

        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        JsLifeCycle.onStart(view);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        super.onPageCommitVisible(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return super.shouldInterceptRequest(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        super.onTooManyRedirects(view, cancelMsg, continueMsg);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        mViewModel.setError(failingUrl + ":" + description);
        if (!mViewModel.getIgnoreError().get()) {
            mViewModel.setIsError(true);
        }
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        super.onFormResubmission(view, dontResend, resend);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        mViewModel.setError(error.toString());
        if (!mViewModel.getIgnoreError().get()) {
            mViewModel.setIsError(true);
        }
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        super.onReceivedClientCertRequest(view, request);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return super.shouldOverrideKeyEvent(view, event);
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        super.onUnhandledKeyEvent(view, event);
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        super.onReceivedLoginRequest(view, realm, account, args);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        if (TextUtils.isEmpty(url)) return true;
        if (url.contains("tel:") || url.contains("phone:")) {
            url = url.replace("phone:", "").replace("tel:", "");
            ActivityUtil.openDialPage(url);
            return true;
        }
        //从内部链接跳到外部链接打开新的html页面
        if (UrlUtil.isInnerLink(mViewModel.getUrl()) && !UrlUtil.isInnerLink(url)) {
            HtmlActivity.startHtml(ResourceUtil.getString(R.string.app_name), url);
        } else {
            mViewModel.setUrl(url);
            view.loadUrl(url);
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        mViewModel.setError(error.getDescription().toString());
        if (!mViewModel.getIgnoreError().get()) {
            mViewModel.setIsError(true);
        }
    }
}
