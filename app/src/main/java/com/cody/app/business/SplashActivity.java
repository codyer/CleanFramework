/*
 * Copyright (c)  Created by Cody.yi on 2016/8/31.
 */

package com.cody.app.business;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.cody.app.R;
import com.cody.handler.business.presenter.SplashPresenter;
import com.cody.handler.business.viewmodel.SplashViewModel;
import com.cody.app.framework.upgrade.UpdateDelegate;
import com.cody.app.databinding.ActivitySplashBinding;
import com.cody.app.business.easeui.ImManager;
import com.cody.app.framework.activity.BaseBindingActivity;
import com.hyphenate.chat.EMClient;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.xf.common.Constant;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.LogUtil;

import pub.devrel.easypermissions.EasyPermissions;

//import com.cody.app.app.push.CoreService;


/**
 * create by dong.wang on 2016/9/10
 * 进入B端App的加载页面
 */
public class SplashActivity extends BaseBindingActivity<SplashPresenter, SplashViewModel, ActivitySplashBinding> implements UpdateDelegate.OnUpdateListener {

    private static final long LOADTIME = 3;
    private static final int REQUEST_PERMISSION = 0X1;
    private CountDownTimer timer;
    /* 最近联系人数据是否同步完成 */
    private boolean isFinished;
    /* 是否需要更新 */
    private static boolean isUpgrade;

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected SplashViewModel buildViewModel(Bundle savedInstanceState) {
        SplashViewModel splashViewModel = new SplashViewModel();
        splashViewModel.setId(1);
        return splashViewModel;
    }

    @Override
    protected SplashPresenter buildPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCountDownTime(LOADTIME);
        timer.start();// 开始计时
        syncConversationData();
        if (!EasyPermissions.hasPermissions(this, Constant.PERMISSIONS)) {
            ActivityCompat.requestPermissions(this,Constant.PERMISSIONS,REQUEST_PERMISSION);
        }else{
            checkVersion();
        }

//        startService(new Intent(this, CoreService.class));
    }

    private void checkVersion() {
        if (!getViewModel().isVersionChecked()) {
            getPresenter().checkVersion(TAG, new OnActionListener() {
                @Override
                public void onSuccess() {
                    hideLoading();
                    if (getViewModel().isVersionChecked()) {
                        UpdateDelegate.delegate(SplashActivity.this, getViewModel(), SplashActivity.this);
                    }
                }
            });
        }
    }
    @Override
    public void onUpdateFinish() {
        isUpgrade =true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            boolean readRight = false,writeRight = false;
           for (int i =0;i<permissions.length;i++){
               //检查读写权限是否被授予
               String perm = permissions[i];
               if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(perm)){
                   if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                       readRight = true;
                   }else{
                       readRight = false;
                   }
               }else if(Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(perm)){
                   if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                       writeRight = true;
                   }else{
                       writeRight = false;
                   }
               }
           }
           //读写权限都有
           if (readRight && writeRight){
               checkVersion();
           }else{
               isUpgrade = true;
           }
        }
    }

    /**
     * 从环信服务器同步最近联系人数据
     */
    private void syncConversationData() {
        new Thread(new Runnable() {
            public void run() {
                if (ImManager.getInstance().isLoggedIn()) {
                    // auto login mode, make sure all group and conversation is loaed before enter the main screen
                    long start = System.currentTimeMillis();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    long costTime = System.currentTimeMillis() - start;
                    LogUtil.e("dong.wang = " + costTime);
                    //wait
                    if (LOADTIME * 1000 - costTime > 0) {
                        isFinished = true;
                    } else {
                        if (isUpgrade)
                            enterNextPage();
                    }
                } else {
                    isFinished = true;
                }
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {

    }

    private void initCountDownTime(long time) {
        /**
         * 最简单的倒计时类，实现了官方的CountDownTimer类（没有特殊要求的话可以使用）
         * 即使退出activity，倒计时还能进行，因为是创建了后台的线程。
         * 有onTick，onFinsh、cancel和start方法
         */
        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法
                getViewModel().getCountTime().set(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            if (isFinished && isUpgrade) {
                                enterNextPage();
                                return;
                            } else {
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }).start();
            }
        };
    }

    /**
     * 进入下个页面
     */
    private static void enterNextPage() {
        ActivityUtil.navigateToThenKill(LoginNewActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
