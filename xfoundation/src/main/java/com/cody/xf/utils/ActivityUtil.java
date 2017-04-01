package com.cody.xf.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cody.xf.FoundationApplication;

/**
 * Created by cody.yi on 2016.8.15
 * Activity Navigator
 */
public class ActivityUtil {

    /**
     * startActivity
     *
     * @param clazz
     */
    public static void navigateTo(Class<? extends Activity> clazz) {
        Activity activity = FoundationApplication.getInstance().getCurrentActivity();
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
        Activity activity = FoundationApplication.getInstance().getCurrentActivity();
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
        Activity activity = FoundationApplication.getInstance().getCurrentActivity();
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
        Activity activity = FoundationApplication.getInstance().getCurrentActivity();
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
        Activity activity = FoundationApplication.getInstance().getCurrentActivity();
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
        Activity activity = FoundationApplication.getInstance().getCurrentActivity();
        Intent intent = new Intent(activity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void finish() {
        FoundationApplication.getInstance().getCurrentActivity().finish();
    }
}
