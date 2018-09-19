/*
 * Copyright (c)  Created by Cody.yi on 2016/9/20.
 */

package com.cody.xf.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.cody.xf.XFoundation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cody.yi on 2016/7/29.
 * 从raw目录读取json文件工具类
 */
public class FetchRawUtil {
    private static FetchRawUtil INSTANCE;
    private Map<Integer, Object> mMap;
    private Gson mJsonUtil;

    private FetchRawUtil() {
        mMap = new HashMap<>();
        mJsonUtil = new Gson();
    }

    public static FetchRawUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FetchRawUtil();
        }
        return INSTANCE;
    }

    /**
     * 第一期从raw文件读取，后期可以改成从服务器获取，并做缓存
     *
     * @param resName json对应的资源文件名
     * @param clazz   解析的对象类
     * @return 返回对应泛型的Bean
     */
    public <T> T fetchBean(String resName, Class<T> clazz) throws IOException {
        int resId = getResource(resName);
        return fetchBean(resId, clazz);
    }

    /**
     * 第一期从raw文件读取，后期可以改成从服务器获取，并做缓存
     *
     * @param resName json对应的资源文件名
     * @param clazz   解析的对象类
     * @return 返回对应泛型的List
     */
    public <T> List<T> fetchListBean(String resName, Class<T> clazz) throws IOException {
        int resId = getResource(resName);
        return fetchListBean(resId, clazz);
    }

    /**
     * 第一期从raw文件读取，后期可以改成从服务器获取，并做缓存
     *
     * @param resId json对应的资源id
     * @param clazz 解析的对象类
     * @return 返回对应泛型的Bean
     */
    public <T> T fetchBean(Integer resId, Class<T> clazz) throws ClassCastException, IOException {
        if (!mMap.containsKey(resId)) {
            T config = readJson(resId, clazz);
            mMap.put(resId, config);
            return config;
        }
        return (T) mMap.get(resId);
    }

    /**
     * 第一期从raw文件读取，后期可以改成从服务器获取，并做缓存
     *
     * @param resId json对应的资源id
     * @param clazz 解析的对象类
     * @return 返回对应泛型的List
     */
    public <T> List<T> fetchListBean(Integer resId, Class<T> clazz) throws ClassCastException, IOException {
        if (!mMap.containsKey(resId)) {
            List<T> config = readJsonList(resId, clazz);
            mMap.put(resId, config);
            return config;
        }
        return (List<T>) mMap.get(resId);
    }

    /**
     * 从raw文件读取，并做缓存
     *
     * @param resId json对应的资源id
     * @param clazz 解析的对象类
     * @return 返回对应泛型的List
     */
    private <T> T readJson(Integer resId, Class<T> clazz) throws IOException {
        StringBuilder jsonStr = new StringBuilder();
        InputStream in = XFoundation.getContext().getResources().openRawResource(resId);
        BufferedReader br;
        String rs;
        br = new BufferedReader(new InputStreamReader(in, "utf-8"));
        while ((rs = br.readLine()) != null) {
            jsonStr.append(rs);
        }
        br.close();
        return mJsonUtil.fromJson(jsonStr.toString(), clazz);
    }

    /**
     * 从raw文件读取，并做缓存
     *
     * @param resId json对应的资源id
     * @param clazz 解析的对象类
     * @return 返回对应泛型的List
     */
    private <T> List<T> readJsonList(Integer resId, Class<T> clazz) throws IOException {
        StringBuilder jsonStr = new StringBuilder();
        InputStream in = XFoundation.getContext().getResources().openRawResource(resId);
        BufferedReader br;
        String rs;
        br = new BufferedReader(new InputStreamReader(in, "utf-8"));
        while ((rs = br.readLine()) != null) {
            jsonStr.append(rs);
        }
        br.close();
        Type type = CommonUtil.getType(List.class, clazz);
        return mJsonUtil.fromJson(jsonStr.toString(), type);
    }

    /**
     * 通过文件名获取资源Id
     *
     * @param imageName 文件名，不需要后缀
     * @return 资源id
     */
    private int getResource(String imageName) {
        Context ctx = XFoundation.getContext();
        return ctx.getResources().getIdentifier(imageName, "raw", ctx.getPackageName());
    }
}
