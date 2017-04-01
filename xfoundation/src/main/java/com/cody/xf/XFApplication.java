package com.cody.xf;

import android.support.multidex.MultiDexApplication;

import com.cody.xf.utils.ActivityUtil;

/**
 * Created by cody.yi on 2016/7/12.
 * Application 基类
 */
public abstract class XFApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        XFoundation.install(this);
        ActivityUtil.install();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        XFoundation.uninstall();
        ActivityUtil.uninstall();
    }
}
