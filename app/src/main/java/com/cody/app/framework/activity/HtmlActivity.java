package com.cody.app.framework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.cody.app.R;
import com.cody.app.business.hybrid.JsHandlerCommonImpl;
import com.cody.app.databinding.FwActivityHtmlBinding;
import com.cody.handler.framework.presenter.HtmlPresenter;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.handler.framework.viewmodel.HtmlViewModel;
import com.cody.repository.framework.Repository;
import com.cody.repository.framework.local.BaseLocalKey;
import com.cody.xf.hybrid.JsBridge;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.LogUtil;

public class HtmlActivity extends WithHeaderActivity<HtmlPresenter, HtmlViewModel, FwActivityHtmlBinding> {

    public static void startHtml(String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
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
        header.setTitle("html");
        header.setLeft(true);
        header.setVisible(true);
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
        String url = "http://mkl.uat1.rs.com/specialCollections?id=7";
        HtmlViewModel viewModel = new HtmlViewModel();
        viewModel.setUrl(url);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            String url = getIntent().getExtras().getString("url");
            String title = getIntent().getExtras().getString("title");
            getViewModel().getHeaderViewModel().setTitle(title);
            getViewModel().setUrl(url);
        }
        JsBridge.getInstance()
                .addJsHandler(JsHandlerCommonImpl.class)
                .syncCookie(this, getViewModel().getUrl(), Repository.getLocalMap(BaseLocalKey.HEADERS))
                .build(getBinding().fwWebView, new JsBridge.OnProgressListener() {
                    @Override
                    public void onProgress(int progress) {
                        LogUtil.d("progress=" + progress);
                        getViewModel().setProgress(progress);
                    }
                });

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
    protected void onDestroy() {
        JsBridge.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        JsBridge.onActivityResult(requestCode, resultCode, data);
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
                finish();
                break;
            case R.id.headerText:
                getBinding().fwWebView.scrollTo(0,0);
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
}
