/*
 * Copyright (c)  Created by Cody.yi on 2016/9/1.
 */
package com.cody.xf.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.cody.xf.XFoundation;

/**
 * Created by cody.yi on 2016/9/1.
 * 资源获取工具类
 */
public class ResourceUtil {
    /**
     * 根据ID获取字符串
     *
     * @param id 资源id
     * @return 资源id对应的字符串
     */
    public static String getString(@StringRes int id) {
        return XFoundation.getContext().getString(id);
    }

    /**
     * 根据ID获取字符串（format方式）
     *
     * @param resId      资源id
     * @param formatArgs 拼接对象
     * @return
     */
    public static String getString(@StringRes int resId, Object... formatArgs) {
        return XFoundation.getContext().getString(resId, formatArgs);
    }

    /**
     * 根据ID获取颜色
     *
     * @param id 资源id
     * @return 资源id对应的颜色
     */
    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(XFoundation.getContext(), id);
    }

    /**
     * 根据ID获取字符串数组
     *
     * @param id 资源id
     * @return 资源id对应的字符串数组
     */
    public static String[] getStringArray(int id) {
        return XFoundation.getContext().getResources().getStringArray(id);
    }

    /**
     * 根据ID获取字符串数组
     *
     * @param id 资源id
     * @return 资源id对应的字符串数组
     */
    public static int getDimension(@DimenRes int id) {
        return XFoundation.getContext().getResources().getDimensionPixelSize(id);
    }

    /**
     * 根据ID获取drawable
     *
     * @param id
     * @return
     */
    public static Drawable getDrawable(@DrawableRes int id) {
        return XFoundation.getContext().getResources().getDrawable(id);
    }
}
