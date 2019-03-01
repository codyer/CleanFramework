package com.cody.xf.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * SoftKeyboardUtil.java
 */
public class SoftKeyboardUtil {

    /**
     * 显示软键盘
     *
     * @param context
     */
    public static void showSoftInput(Activity context) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(context.getCurrentFocus(), 0);
    }

    /**
     * 显示软键盘 并绑定到指定View
     *
     * @param context
     * @param view
     */
    public static void showSoftInput(Activity context, View view) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

    /**
     * 通过绑定的View 隐藏软键盘
     *
     * @param context
     */
    public static void hiddenSoftInput(Activity context, View view) {
        if (view.getWindowToken() != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public static void hiddenSoftInput(Activity context) {
        if (null != context) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null != context.getCurrentFocus() && null != context.getCurrentFocus().getWindowToken())
                inputManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示或隐藏软键盘
     *
     * @param context
     */
    public static void showOrHiddenSoftInput(Activity context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
