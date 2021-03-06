/*
 * Copyright (c)  Created by Cody.yi on 2016/9/20.
 */

package com.cody.repository.framework;

/**
 * Created by cody.yi on 2016/7/18.
 * 模拟网络请求工具
 */

import android.os.Handler;
import android.os.Looper;

import com.cody.repository.R;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.xf.utils.FetchRawUtil;
import com.cody.xf.utils.http.HttpCode;
import com.cody.xf.utils.http.SimpleBean;

import java.io.IOException;
import java.util.List;

/**
 * 管理所有数据，对应用提供一致的数据接口，隐藏数据来源
 * 内存、SD卡
 */
public class MockRepository {

    private MockRepository() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("HttpUtil cannot be instantiated");
    }

    public static <T> void getData(final int resId, final Class<T> clazz, final ICallback<T> callback) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(FetchRawUtil.getInstance().fetchBean(resId, clazz));
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailure(new SimpleBean(HttpCode.REQUEST_ERROR, e.getMessage()));
                }
            }
        }, 500);
    }

    public static <T> void getListData(final int resId, final Class<T> clazz, final ICallback<List<T>> callback) {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(FetchRawUtil.getInstance().fetchListBean(resId, clazz));
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailure(new SimpleBean(HttpCode.REQUEST_ERROR, e.getMessage()));
                }
            }
        }, 500);
    }

    public static <T> void getData(final String resId, final Class<T> clazz, final ICallback<T> callback) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(FetchRawUtil.getInstance().fetchBean(resId, clazz));
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailure(new SimpleBean(HttpCode.REQUEST_ERROR, e.getMessage()));
                }
            }
        }, 500);
    }

    public static <T> void getListData(final String resId, final Class<T> clazz, final ICallback<List<T>> callback) {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(FetchRawUtil.getInstance().fetchListBean(resId, clazz));
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailure(new SimpleBean(HttpCode.REQUEST_ERROR, e.getMessage()));
                }
            }
        }, 500);
    }

    public static void getResult(final ICallback<SimpleBean> callback) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(FetchRawUtil.getInstance().fetchBean(R.raw.xf_simple_bean, SimpleBean.class));
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailure(new SimpleBean(HttpCode.REQUEST_ERROR, e.getMessage()));
                }
            }
        }, 500);
    }
}
