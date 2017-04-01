/*
 * Copyright (c)  Created by Cody.yi on 2016/9/20.
 */

package com.cody.xf.utils;

/**
 * Created by cody.yi on 2016/7/18.
 * 网络请求工具
 */

import android.os.Handler;
import android.os.Looper;

import com.cody.xf.R;
import com.cody.xf.binding.ICallback;
import com.cody.xf.common.Constant;
import com.cody.xf.common.SimpleBean;

import java.io.IOException;
import java.util.List;

/**
 * 管理所有数据，对应用提供一致的数据接口，隐藏数据来源
 * 内存、SD卡、网络
 */
public class LocalDataUtil {

    private LocalDataUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("HttpUtil cannot be instantiated");
    }

    public static <T> void getData(final int url, final Class<T> clazz, final ICallback<T> callback) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(FetchRawUtil.getInstance().fetchBean(url, clazz));
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailure(new SimpleBean(Constant.HttpCode.REQUEST_ERROR, e.getMessage()));
                }
            }
        }, 500);
    }

    public static <T> void getListData(final int url, final Class<T> clazz, final ICallback<List<T>> callback) {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(FetchRawUtil.getInstance().fetchListBean(url, clazz));
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailure(new SimpleBean(Constant.HttpCode.REQUEST_ERROR, e.getMessage()));
                }
            }
        }, 500);
    }

    public static <T> void getData(final String url, final Class<T> clazz, final ICallback<T> callback) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(FetchRawUtil.getInstance().fetchBean(url, clazz));
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailure(new SimpleBean(Constant.HttpCode.REQUEST_ERROR, e.getMessage()));
                }
            }
        }, 500);
    }

    public static <T> void getListData(final String url, final Class<T> clazz, final ICallback<List<T>> callback) {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(FetchRawUtil.getInstance().fetchListBean(url, clazz));
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailure(new SimpleBean(Constant.HttpCode.REQUEST_ERROR, e.getMessage()));
                }
            }
        }, 500);
    }

    public static void getResult(final ICallback<SimpleBean> callback) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(FetchRawUtil.getInstance().fetchBean(R.raw.simple_bean, SimpleBean.class));
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailure(new SimpleBean(Constant.HttpCode.REQUEST_ERROR, e.getMessage()));
                }
            }
        }, 500);
    }
}
