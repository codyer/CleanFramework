package com.cody.app.business.scan;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import com.lzy.imagepicker.bean.ImageItem;
import com.cody.app.BuildConfig;
import com.cody.app.R;
import com.cody.app.databinding.ActivityScanBinding;
import com.cody.app.framework.activity.BaseBindingActivity;
import com.cody.app.framework.activity.HtmlActivity;
import com.cody.app.framework.hybrid.core.UrlUtil;
import com.cody.app.framework.widget.image.ImageViewDelegate;
import com.cody.app.framework.widget.image.OnImageViewListener;
import com.cody.handler.framework.presenter.DefaultWithHeaderPresenter;
import com.cody.handler.framework.viewmodel.WithHeaderViewModel;
import com.cody.xf.utils.SystemBarUtil;
import com.cody.xf.utils.ToastUtil;

import java.util.List;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 二维码扫描（测试用）
 */
public class ScanActivity extends BaseBindingActivity<DefaultWithHeaderPresenter
        , WithHeaderViewModel
        , ActivityScanBinding> implements QRCodeView.Delegate, OnImageViewListener {

    private static final int REQUEST_CODE_CAMERA = 1000;
    public static final String SCAN_RESULT = "SCAN_RESULT";
    private ImageViewDelegate mImageViewDelegate;

    @Override
    protected DefaultWithHeaderPresenter buildPresenter() {
        return new DefaultWithHeaderPresenter();
    }

    @Override
    protected WithHeaderViewModel buildViewModel(Bundle savedInstanceState) {
        return new WithHeaderViewModel();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_scan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BGAQRCodeUtil.setDebug(BuildConfig.DEBUG);
        getBinding().qrCodeView.setDelegate(this);

        mImageViewDelegate = new ImageViewDelegate(this);
        mImageViewDelegate.setCanDelete(false);
    }

    @Override
    protected void onImmersiveReady() {
        SystemBarUtil.setStatusBarDarkMode(this, false);
        SystemBarUtil.tintStatusBar(this, Color.TRANSPARENT);
        SystemBarUtil.immersiveStatusBar(this, 0.0f);
        SystemBarUtil.setPadding(this, getBinding().header);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            initScan();
        } else {
            EasyPermissions.requestPermissions(this
                    , "请打开相机权限"
                    , REQUEST_CODE_CAMERA
                    , Manifest.permission.CAMERA);
        }
    }

    @Override
    protected void onStop() {
        getBinding().qrCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        getBinding().qrCodeView.onDestroy();
        mImageViewDelegate = null;
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        BGAQRCodeUtil.d("扫描结果：" + result);
        vibrate();
        if (UrlUtil.isHttpUrl(result)) {
            finish();
            HtmlActivity.startHtml(null, result);
        } else {
            ToastUtil.showToast("扫描结果：" + result);
            getBinding().qrCodeView.startSpotDelay(1000); // 延迟1秒后开始识别
        }
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        super.onPermissionsGranted(requestCode, perms);
        initScan();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.tv_photo:
                if (null != mImageViewDelegate) {
                    mImageViewDelegate.withId(R.id.tv_photo).selectImage(1, true);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != mImageViewDelegate) {
            mImageViewDelegate.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onPreview(int id, List<ImageItem> images) {
    }

    @Override
    public void onPickImage(int id, List<ImageItem> images) {
        if (images == null || images.size() == 0) return;
        switch (id) {
            case R.id.tv_photo:
                getBinding().qrCodeView.decodeQRCode(images.get(0).path);
                break;
        }
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(200);
        }
    }

    private void initScan() {
        getBinding().qrCodeView.startCamera();
        getBinding().qrCodeView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
    }
}
