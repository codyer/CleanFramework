package com.cody.xf.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

/**
 * Created by cody.yi on 2016.8.15
 * Activity Navigator
 */
public class ActivityUtil {

    private static ActivityUtil sInstance;
    private Reference<Activity> mCurrentActivity;

    public static void install() {
        sInstance = new ActivityUtil();
    }

    public static void uninstall() {
        getInstance().mCurrentActivity.clear();
        getInstance().mCurrentActivity = null;
        sInstance = null;
    }

    private static ActivityUtil getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("You should call ActivityUtil.install() in you application first!");
        } else {
            return sInstance;
        }
    }

    public static Activity getCurrentActivity() {
        if (getInstance().mCurrentActivity == null) {
            throw new NullPointerException("You should setCurrentActivity first!");
        }
        return getInstance().mCurrentActivity.get();
    }

    public static void setCurrentActivity(@NonNull Activity mCurrentActivity) {
        getInstance().mCurrentActivity = new SoftReference<>(mCurrentActivity);
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    public static void navigateTo(Class<? extends Activity> clazz) {
        Activity activity = getCurrentActivity();
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    public static void navigateTo(Class<? extends Activity> clazz, Bundle bundle) {
        Activity activity = getCurrentActivity();
        Intent intent = new Intent(activity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    public static void navigateToThenKill(Class<? extends Activity> clazz) {
        Activity activity = getCurrentActivity();
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    public static void navigateToThenKill(Class<? extends Activity> clazz, Bundle bundle) {
        Activity activity = getCurrentActivity();
        Intent intent = new Intent(activity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    public static void navigateToForResult(Class<? extends Activity> clazz, int requestCode) {
        Activity activity = getCurrentActivity();
        Intent intent = new Intent(activity, clazz);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    public static void navigateToForResult(Class<? extends Activity> clazz, int requestCode, Bundle bundle) {
        Activity activity = getCurrentActivity();
        Intent intent = new Intent(activity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void finish() {
        getCurrentActivity().finish();
    }
}
