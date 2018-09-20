package com.cody.app.framework.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.FwActivityHtmlBinding;
import com.cody.app.framework.hybrid.JsBridge;
import com.cody.app.framework.hybrid.core.UrlUtil;
import com.cody.app.framework.hybrid.handler.JsDefaultHandlerImpl;
import com.cody.app.framework.hybrid.handler.JsHandlerCommonImpl;
import com.cody.handler.framework.presenter.HtmlPresenter;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.handler.framework.viewmodel.HtmlViewModel;
import com.cody.repository.framework.Repository;
import com.cody.repository.framework.local.BaseLocalKey;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.SystemBarUtil;
import com.cody.xf.utils.ToastUtil;
import com.umeng.socialize.UMShareAPI;
public class HtmlActivity extends WithHeaderActivity<HtmlPresenter, HtmlViewModel, FwActivityHtmlBinding> {

    /**
     * 跳转html页面统一使用此函数
     *
     * @param title title为空意味着不要原生的头部
     * @param url   地址
     */
    public static void startHtml(String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        LogUtil.d("startHtml---------------"+url+"-------------------");
        ActivityUtil.navigateTo(HtmlActivity.class, bundle);
    }
    /**
     * 创建标题
     * 返回空或者默认的HeaderViewModel不会显示头部，必须设置头部的visible
     *
     * @see HeaderViewModel#setVisible
     */
    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setLeft(true);
        header.setVisible(!getViewModel().isHtmlWithHeader());
    }

    /**
     * 子类提供有binding的资源ID
     */
    @Override
    protected int getLayoutID() {
        return R.layout.fw_activity_html;
    }

    /**
     * 每个view保证只有一个Presenter
     */
    @Override
    protected HtmlPresenter buildPresenter() {
        return new HtmlPresenter();
    }

    /**
     * 每个view保证只有一个ViewModel，当包含其他ViewModel时使用根ViewModel包含子ViewModel
     * ViewModel可以在创建的时候进行初始化，也可以在正在进行绑定（#setBinding）的时候初始化
     */
    @Override
    protected HtmlViewModel buildViewModel(Bundle savedInstanceState) {
        String url;
        String title;
        HtmlViewModel viewModel = new HtmlViewModel();
        if (getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString("url");
            title = getIntent().getExtras().getString("title");
            if (!TextUtils.isEmpty(url)) {
                if (UrlUtil.isInnerLink(url)) {//内部链接原生可能不需要显示头部
                    if (!TextUtils.isEmpty(title)) {
                        viewModel.getHeaderViewModel().setTitle(title);
                    } else {
                        // title为空意味着html页面自己处理头部，原生不需要显示头部
                        viewModel.setHtmlWithHeader(true);
                        SystemBarUtil.tintStatusBar(this, Color.TRANSPARENT);
                        SystemBarUtil.immersiveStatusBar(this, 0.0f);
                    }
                } else {//外链显示头部
                    viewModel.setHtmlWithHeader(false);
                    if (!TextUtils.isEmpty(title)) {
                        viewModel.getHeaderViewModel().setTitle(title);
                    }
                }
                viewModel.setUrl(url);
            } else {
                ToastUtil.showToast(getString(R.string.fw_html_url_null));
            }
        }
        return viewModel;
    }

    @Override
    protected void onImmersiveReady() {
        SystemBarUtil.setStatusBarDarkMode(this, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JsBridge.getInstance()
                .addJsHandler(JsDefaultHandlerImpl.class.getSimpleName(), JsDefaultHandlerImpl.class)
                .addJsHandler(JsHandlerCommonImpl.class.getSimpleName(), JsHandlerCommonImpl.class)
                .syncCookie(this, getViewModel().getUrl(), Repository.getLocalMap(BaseLocalKey.X_TOKEN))
                .build(getBinding().fwWebView, getViewModel());
        LogUtil.i("----------------session----------------" + Repository.getLocalMap(BaseLocalKey.X_TOKEN));
        if (null != savedInstanceState) {
            getBinding().fwWebView.restoreState(savedInstanceState);
        } else {
            getBinding().fwWebView.loadUrl(getViewModel().getUrl());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (getIntent().getExtras() != null) {
            String url = getIntent().getExtras().getString("url");
            String title = getIntent().getExtras().getString("title");
            getViewModel().getHeaderViewModel().setTitle(title);
            getViewModel().setUrl(url);
            getBinding().fwWebView.loadUrl(url);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        JsBridge.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JsBridge.onPause();
    }

    @Override
    protected void onDestroy() {
        JsBridge.onDestroy();
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        JsBridge.onActivityResult(requestCode, resultCode, data);

        /*
          QQ与新浪不需要添加Activity，但需要在使用QQ分享或者授权的Activity中，添加onActivityResult
          注意onActivityResult不可在fragment中实现，如果在fragment中调用登录或分享，需要在fragment依赖的Activity中实现
         */
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getBinding().fwWebView.saveState(outState);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeftBtn:
                if (getBinding().fwWebView.canGoBack()) {
                    getBinding().fwWebView.goBack();
                } else {
                    finish();
                }
                break;
            case R.id.headerText:
                getBinding().fwWebView.scrollTo(0, 0);
                break;
            case R.id.refresh:
                getViewModel().setIsError(false);
                getBinding().fwWebView.loadUrl(getViewModel().getUrl());
                break;
            case R.id.ignore:
                getViewModel().setIgnoreError(true);
                break;
        }
    }

    /**
     * 重写物理返回方法。如果html有上一页则跳转到html上一页，否则返回native
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getBinding().fwWebView.canGoBack()) {
                getBinding().fwWebView.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        JsBridge.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
