package com.cody.app.framework.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

import com.lzy.imagepicker.bean.ImageItem;
import com.cody.app.R;
import com.cody.app.databinding.FwActivityHtmlBinding;
import com.cody.app.framework.hybrid.JsBridge;
import com.cody.app.framework.hybrid.core.JsWebChromeClient;
import com.cody.app.framework.hybrid.core.UrlUtil;
import com.cody.app.framework.hybrid.handler.JsDefaultHandlerImpl;
import com.cody.app.framework.hybrid.handler.JsHandlerCommonImpl;
import com.cody.app.framework.widget.image.ImageViewDelegate;
import com.cody.app.framework.widget.image.OnImageViewListener;
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

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HtmlActivity extends WithHeaderActivity<HtmlPresenter, HtmlViewModel, FwActivityHtmlBinding>
        implements OnImageViewListener, JsWebChromeClient.OpenFileChooserCallBack {
    private final int TAKE_VIDEO_CODE = 0x2005;
    private final int TAKE_OTHER_FILE_CODE = 0x2006;
    private static Boolean isExit = false;
    private boolean mIsRoot = false;
    private ImageViewDelegate mImageViewDelegate;
    private ValueCallback<Uri[]> mFilePathCallback;
    private ValueCallback<Uri> mUploadMsg;

    /**
     * 跳转html页面统一使用此函数
     *
     * @param title title为空意味着不要原生的头部
     * @param url   地址
     * @param root  根页面
     */
    public static void startHtml(String title, String url, boolean root) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        bundle.putBoolean("root", root);
        LogUtil.d("startHtml---------------" + url + "-------------------");
        ActivityUtil.navigateTo(HtmlActivity.class, bundle);
    }

    /**
     * 跳转html页面统一使用此函数
     *
     * @param title title为空意味着不要原生的头部
     * @param url   地址
     */
    public static void startHtml(String title, String url) {
        startHtml(title, url, false);
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
            mIsRoot = getIntent().getExtras().getBoolean("root");
            if (!TextUtils.isEmpty(url)) {
                if (UrlUtil.isInnerLink(url)) {//内部链接原生可能不需要显示头部
                    if (!TextUtils.isEmpty(title)) {
                        viewModel.getHeaderViewModel().setTitle(title);
                    } else {
                        // title为空意味着html页面自己处理头部，原生不需要显示头部
                        viewModel.setHtmlWithHeader(true);
                        SystemBarUtil.tintStatusBar(this, Color.TRANSPARENT);
                        SystemBarUtil.immersiveStatusBar(this, 0.0f);
                        SystemBarUtil.setPadding(this, getBinding().fwHeader);
                    }
                } else {//外链显示头部
                    if (!TextUtils.isEmpty(title)) {
                        viewModel.getHeaderViewModel().setTitle(title);
                    }
                    viewModel.setHtmlWithHeader(false);
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
        mImageViewDelegate = new ImageViewDelegate(this);
        JsBridge.getInstance()
                .addJsHandler(JsDefaultHandlerImpl.class.getSimpleName(), JsDefaultHandlerImpl.class)
                .addJsHandler(JsHandlerCommonImpl.class.getSimpleName(), JsHandlerCommonImpl.class)
                .syncCookie(this, getViewModel().getUrl(), Repository.getLocalMap(BaseLocalKey.X_TOKEN))
                .setFileChooseCallBack(this)
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
    public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
        LogUtil.d("OpenFileChooserCallBack acceptType=" + acceptType);
        mUploadMsg = uploadMsg;
        openChooser(acceptType);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void showFileChooserCallBack(final ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        LogUtil.d("OpenFileChooserCallBack fileChooserParams=" + fileChooserParams);
        mFilePathCallback = filePathCallback;
        if (fileChooserParams != null && fileChooserParams.getAcceptTypes() != null) {
            for (String acceptType : fileChooserParams.getAcceptTypes()) {
                openChooser(acceptType);
            }
        }
    }

    @Override
    public void onPreview(int id, List<ImageItem> images) {
    }

    @Override
    public void onPickImage(int id, List<ImageItem> images) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (mUploadMsg == null || images == null || images.size() < 1) {
                return;
            }
            String url = images.get(0).path;
            if (TextUtils.isEmpty(url) || !new File(url).exists()) {
                return;
            }
            Uri uri = Uri.fromFile(new File(url));
            mUploadMsg.onReceiveValue(uri);
            mUploadMsg = null;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // for android 5.0+
            if (mFilePathCallback == null || images == null || images.size() < 1) {
                return;
            }

            Uri[] uris = new Uri[images.size()];
            for (int i = 0; i < images.size(); i++) {
                if (TextUtils.isEmpty(images.get(i).path) || !new File(images.get(i).path).exists()) {
                    continue;
                }
                uris[i] = Uri.fromFile(new File(images.get(i).path));
            }
            mFilePathCallback.onReceiveValue(uris);
            mFilePathCallback = null;
        }
    }

    @Override
    protected void onDestroy() {
        JsBridge.onDestroy();
        releaseFileChoose();
        mImageViewDelegate = null;
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        releaseFileChoose();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        JsBridge.onActivityResult(requestCode, resultCode, data);
        if (mImageViewDelegate != null) {
            mImageViewDelegate.onActivityResult(requestCode, resultCode, data);
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_VIDEO_CODE:
                    Uri result = data == null ? null : data.getData();
                    if (mFilePathCallback != null) {
                        mFilePathCallback.onReceiveValue(new Uri[]{result});
                        mFilePathCallback = null;
                    } else if (mUploadMsg != null) {
                        mUploadMsg.onReceiveValue(result);
                        mUploadMsg = null;
                    }
                    break;
                case TAKE_OTHER_FILE_CODE:
                    break;
            }
        }
        releaseFileChoose();
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
                finish();
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
                return true;
            } else if (mIsRoot) {
                exitByDoubleClick(); //调用双击退出函数
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        JsBridge.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void openChooser(String acceptType) {
        if (acceptType != null) {
            if (acceptType.contains("image/") && mImageViewDelegate != null) {
                mImageViewDelegate.pickImage(1, false);
            } else if (acceptType.contains("video/")) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                //限制时长
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
                //开启摄像机
                startActivityForResult(intent, TAKE_VIDEO_CODE);
            } else {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType(acceptType);
                this.startActivityForResult(Intent.createChooser(i, "File Chooser"), TAKE_OTHER_FILE_CODE);
            }
        }
    }

    private void exitByDoubleClick() {
        Timer tExit;
        if (!isExit) {
            isExit = true;
            // 准备退出
            ToastUtil.showToast(getString(R.string.click_back_two_times));
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000);
            // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
        }
    }

    private void releaseFileChoose() {
        if (mUploadMsg != null) {
            mUploadMsg.onReceiveValue(Uri.EMPTY);
            mUploadMsg = null;
        }

        if (mFilePathCallback != null) {         // for android 5.0+
            mFilePathCallback.onReceiveValue(new Uri[]{});
            mFilePathCallback = null;
        }
    }
}
