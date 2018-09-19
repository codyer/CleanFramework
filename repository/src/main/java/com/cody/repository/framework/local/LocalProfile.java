/*
 * Copyright (c)  Created by Cody.yi on 2016/9/13.
 */

package com.cody.repository.framework.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.cody.xf.utils.CommonUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cody.yi on 2016/9/13.
 * 存放全局应用级需要本地化的变量LocalProfile
 */
public class LocalProfile {

    private final static String FILENAME = "XFProfile";
    private final SharedPreferences mSharedPreferences;
    private final Gson mParseUtil = new Gson();

    /**
     * 构造函数，进行初始化
     */
    public LocalProfile(Context context) {
        mSharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
    }

    /**
     * 获取本地化数据模型
     *
     * @param key 键
     * @param cls 指定返回类型
     * @param <T> 返回任意类型
     * @return T -> cls
     */
    public final <T> T getViewModel(String key,Class cls) {
        String mapStr = getValue(key, null);
        Type type = CommonUtil.getType(cls);
        return mParseUtil.fromJson(mapStr, type);
    }

    /**
     * 保存本地化数据模型
     *
     * @param key       键
     * @param viewModel 要保存的数据
     * @param <T>       数据类型
     */
    public final <T> void setViewModel(String key, T viewModel) {
        setValue(key, mParseUtil.toJson(viewModel));
    }

    public final Map<String, String> getMap(String key) {
        String mapStr = getValue(key, null);
        Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();
        return mParseUtil.fromJson(mapStr, type);
    }

    public final void setMap(String key, Map<String, String> map) {
        setValue(key, mParseUtil.toJson(map));
    }

    /**
     * 保存本地化键值对
     *
     * @param key   键
     * @param value 值
     */
    public final void setValue(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    /**
     * 获取本地化键值对
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public final String getValue(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    /**
     * 保存本地化键值对
     *
     * @param key   键
     * @param value 值
     */
    public final void setValue(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    /**
     * 获取本地化键值对
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public final boolean getValue(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * 保存本地化键值对
     *
     * @param key   键
     * @param value 值
     */
    public final void setValue(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    /**
     * 获取本地化键值对
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public final int getValue(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    /**
     * 保存本地化键值对
     *
     * @param key   键
     * @param value 值
     */
    public final void setValue(String key, float value) {
        mSharedPreferences.edit().putFloat(key, value).apply();
    }

    /**
     * 获取本地化键值对
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public final float getValue(String key, float defaultValue) {
        return mSharedPreferences.getFloat(key, defaultValue);
    }

    /**
     * 移除值
     *
     * @param key 键
     */
    public final void remove(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }

    public final void registerCallback(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        if (listener == null) return;
        mSharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }
    public final List<String> getList(String key) {
        String listStr = getValue(key, null);
        Type type = new TypeToken<List<String>>() {
        }.getType();
        return mParseUtil.fromJson(listStr, type);
    }

    public final void setList(String key, List<String> list) {
        setValue(key, mParseUtil.toJson(list));
    }
}
