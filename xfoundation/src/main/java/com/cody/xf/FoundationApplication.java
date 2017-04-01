package com.cody.xf;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.cody.xf.utils.http.LoginStatusListener;

import java.lang.ref.WeakReference;

/**
 * Created by cody.yi on 2016/7/12.
 * Application 基类
 */
public abstract class FoundationApplication extends MultiDexApplication implements LoginStatusListener {
    protected static FoundationApplication INSTANCE;

    private WeakReference<Activity> mCurrentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        com.cody.xf.Repositoryq.install(this);
    }

    /**
     * 获取系统上下文
     *
     * @return 系统上下文
     */
    public static Context getContext() {
        return INSTANCE.getApplicationContext();
    }

    /**
     * 获取Application实例
     *
     * @return 实例
     */
    public static FoundationApplication getInstance() {
        return INSTANCE;
    }

    public Activity getCurrentActivity() {
        if (mCurrentActivity == null) {
            throw new NullPointerException("You should setCurrentActivity first!");
        }
        return mCurrentActivity.get();
    }

    public void setCurrentActivity(@NonNull Activity mCurrentActivity) {
        this.mCurrentActivity = new WeakReference<>(mCurrentActivity);
    }
}
