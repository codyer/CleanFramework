package com.cody.xf;

import android.support.multidex.MultiDexApplication;

import com.cody.xf.common.LoginBroadcastReceiver;
import com.cody.xf.utils.http.ILoginStatusListener;
import com.cody.xf.widget.swipebacklayout.BGASwipeBackHelper;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by cody.yi on 2016/7/12.
 * Application 基类
 */
public abstract class XFApplication extends MultiDexApplication implements ILoginStatusListener {
    private LoginBroadcastReceiver mReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        XFoundation.install(this);
        LeakCanary.install(this);
        mReceiver = LoginBroadcastReceiver.register();

        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        XFoundation.uninstall();
        LoginBroadcastReceiver.unRegister(mReceiver);
    }
}
