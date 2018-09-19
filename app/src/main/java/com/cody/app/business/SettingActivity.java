/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.app.business;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;

import com.cody.app.R;
import com.cody.handler.business.presenter.SettingPresenter;
import com.cody.handler.business.viewmodel.SettingViewModel;
import com.cody.app.business.scan.ScanActivity;
import com.cody.app.databinding.ActivitySettingBinding;
import com.cody.app.framework.activity.HtmlActivity;
import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.app.utils.FileUtil;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.business.interaction.constant.Constant;
import com.cody.repository.business.interaction.constant.H5Url;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.ToastUtil;
import com.cody.xf.widget.dialog.AlertDialog;

/**
 * Created by cody.yi on 2016/8/9.
 * 个人中的设置界面
 */
public class SettingActivity extends WithHeaderActivity<SettingPresenter, SettingViewModel,
        ActivitySettingBinding> {

    private String userRole;
    public static final String TAG = "SettingActivity";

    @NonNull
    @Override
    protected SettingViewModel buildViewModel(Bundle savedInstanceState) {
        SettingViewModel viewModel = new SettingViewModel();
        viewModel.setId(1);
        return viewModel;
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle(getString(R.string.setting));
        header.setLeft(true);
    }

    @NonNull
    @Override
    protected SettingPresenter buildPresenter() {
        return new SettingPresenter();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanCache();
        userRole = Repository.getLocalValue(LocalKey.USER_ROLE);
        getViewModel().userRole.set(userRole);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
            case R.id.feedBackItem:
                HtmlActivity.startHtml("意见反馈", H5Url.FEEDBACK);
                break;
            case R.id.miniApp:
                BuryingPointUtils.build(SettingActivity.class,4223).submitF();
                ActivityUtil.navigateTo(MiniAppActivity.class);
                break;
            case R.id.clearCache:
                clearCache();
                break;
            case R.id.settingScan:
                ScanActivity.startScanAllActivity();
                break;
            case R.id.hybridDemo:
                HtmlActivity.startHtml("Hybrid test", "file:///android_asset/xf_hybrid_demo.html");
                break;
            case R.id.logout:
                new AlertDialog(SettingActivity.this).builder()
                        .setTitle(getString(R.string.are_you_sure_quit_out))
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getPresenter().online(TAG, false);
                                getPresenter().tapLogout(TAG);
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
                break;
            case R.id.quickReplySet:
                Intent intent = new Intent(SettingActivity.this, QuickReplySettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && requestCode == ScanActivity.SCAN_REQUEST_CODE) {
            String result = intent.getStringExtra(ScanActivity.SCAN_RESULT);
            if (!TextUtils.isEmpty(result)) {
                LogUtil.d("扫码结果：" + result);
                HtmlActivity.startHtml(null, result);
            }
        }
    }

    @Override
    public void onUpdate(Object... args) {
        super.onUpdate(args);
        if (args != null && args.length > 0) {
            switch ((int) args[0]) {
                case Constant.RequestType.TYPE_1:
                    LoginNewActivity.logOutByUser();
                    break;
                case Constant.RequestType.TYPE_7:
                case Constant.RequestType.TYPE_8:
                case Constant.RequestType.TYPE_9:
                    getPresenter().tapLogout(TAG);
                    break;
            }
        }
    }

    private void scanCache() {
        new Thread() {
            @Override
            public void run() {
                long size = FileUtil.getFolderSize(getCacheDir());
                final String caChe = Formatter.formatFileSize(SettingActivity.this, size);
                getViewModel().cache.set(caChe);
            }
        }.run();
    }

    private void clearCache() {
        AlertDialog dialog = new AlertDialog(this);
        dialog.builder()
                .setTitle("确认清除缓存吗？")
                .setNegativeButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLoading("清除中...");
                        new Thread() {
                            @Override
                            public void run() {
                                final boolean isOk = FileUtil.deleteDir(getCacheDir());
                                scanCache();
                                new Handler(getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideLoading();
                                        ToastUtil.showToast(isOk ? "清除成功" : "未知异常");
                                    }
                                });
                            }
                        }.run();
                    }
                }).show();
    }
}
