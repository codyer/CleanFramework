package com.cody.repository.business.database;

/**
 * Created by cody.yi on 2018/7/20.
 * 数据库操作回调
 */
public abstract class DataCallBack<T> {
    public void onSuccess(T t) {
    }

    public void onError(String error) {
    }
}
