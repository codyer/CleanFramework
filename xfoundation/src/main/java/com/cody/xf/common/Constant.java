package com.cody.xf.common;

import android.Manifest;

/**
 * Created by cody.yi on 2016/7/13.
 * 常量
 */
public class Constant {
    public static final String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };//应用需要的权限
    public static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };//应用需要的权限
    public static final int LOGIN_REQUEST_CODE = 0x401;//超时登出
    public static final boolean DEBUG = true;

    /**
     * 运行模式 DEBUG = 1; INFO = 2; WARN = 3; ERROR = 4; 优先级DEBUG < INFO < WARN <
     * ERROR
     * RUNNING_MODE大于4时Log关闭
     */
    public static final int RUNNING_MODE = 1;
}
