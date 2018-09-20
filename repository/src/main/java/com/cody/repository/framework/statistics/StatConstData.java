/*
 * @Title StatConstData.java
 * @Copyright Copyright 2016 Cody All Rights Reserved.
 * @author Cody
 * @date 2016-4-13 下午1:28:17
 * @version 1.0
 */
package com.cody.repository.framework.statistics;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * @author Cody
 * @Description 可通过android SDK直接获得的常量信息
 * @date 2016-4-13 下午1:28:17
 */
public class StatConstData {
    /**
     * app版本号
     */
    public static String appVersionName;
    public static String osVersion;
    /**
     * 设备名称
     */
    public static String deviceName;
    /**
     * 设备品牌
     */
    public static String deviceBrand;
    /**
     * device pix: HxW
     */
    public static String devicePix;
    /**
     * device imei
     */
    public static String deviceImei;

    public static void initData(Context context) {
        //app版本号
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            appVersionName = pi.versionName;
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                deviceImei = tm.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设备名称
        deviceName = Build.MODEL;
        deviceBrand = Build.BRAND;
        osVersion = String.valueOf(Build.VERSION.RELEASE);
        //屏幕信息
//		DisplayMetrics displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
//		devicePix = displayMetrics.widthPixels + "x" + displayMetrics.heightPixels;
    }
}
