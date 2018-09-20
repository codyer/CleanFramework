package com.cody.app.business;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.cody.app.R;
import com.cody.app.business.scan.ScanActivity;
import com.cody.app.databinding.ActivityMainBinding;
import com.cody.app.framework.activity.BaseBindingActivity;
import com.cody.app.framework.activity.HtmlActivity;
import com.cody.handler.business.presenter.MainPresenter;
import com.cody.handler.business.viewmodel.MainViewModel;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.SystemBarUtil;
import com.cody.xf.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 首页
 */
public class MainActivity extends BaseBindingActivity<MainPresenter, MainViewModel, ActivityMainBinding> {
    private static Boolean isExit = false;

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onImmersiveReady() {
        SystemBarUtil.setStatusBarDarkMode(this, false);
        SystemBarUtil.tintStatusBar(this, Color.TRANSPARENT);
        SystemBarUtil.immersiveStatusBar(this, 0.0f);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter buildPresenter() {
        return new MainPresenter();
    }

    @Override
    protected MainViewModel buildViewModel(Bundle savedInstanceState) {
        MainViewModel viewModel = new MainViewModel();
        return viewModel;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan:
                ScanActivity.startScanActivity();
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
                HtmlActivity.startHtml("扫码结果", result);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByDoubleClick(); //调用双击退出函数
        }
        return false;
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
}
