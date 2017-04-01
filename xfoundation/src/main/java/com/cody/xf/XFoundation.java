package com.cody.xf;

import android.app.Application;
import android.content.Context;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

/**
 * Created by cody.yi on 2017/4/1.
 * 基础框架
 */
public class XFoundation {
    private static XFoundation sInstance;
    private Reference<Application> mAppRef;

    private static XFoundation getInstance() {
        if (sInstance == null){
            throw new NullPointerException("You should call Foundation.install() in you application first!");
        }else {
            return sInstance;
        }
    }

    private XFoundation(Application application) {
        mAppRef = new SoftReference<>(application);
    }

    public Application getApp() {
        if (mAppRef == null || mAppRef.get() == null) {
            throw new NullPointerException("You should call Foundation.install() in you application first!");
        } else {
            return mAppRef.get();
        }
    }

    public static void install(Application application) {
        sInstance = new XFoundation(application);
    }

    public static void uninstall() {
        sInstance = null;
    }

    /**
     * 获取系统上下文
     *
     * @return 系统上下文
     */
    public static Context getContext() {
        return getInstance().getApp().getApplicationContext();
    }

}
