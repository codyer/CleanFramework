package com.cody.handler.framework.viewmodel;

import android.databinding.ObservableField;

/**
 * Created by cody.yi on 2017/5/8.
 * 更新
 */
public class UpdateViewModel extends WithHeaderViewModel {
    private final ObservableField<String> mCountTime = new ObservableField<>("3s");
    private boolean mVersionChecked = false;
    private boolean mForceUpdate = false;
    private boolean mOptionalUpdate = false;
    private String mApkUrl = "";
    private String mUpdateInfo = "";
    private String mApkName = "";

    public boolean isVersionChecked() {
        return mVersionChecked;
    }

    public void setVersionChecked(boolean versionChecked) {
        mVersionChecked = versionChecked;
    }

    public boolean isForceUpdate() {
        return mForceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        mForceUpdate = forceUpdate;
    }

    public boolean isOptionalUpdate() {
        return mOptionalUpdate;
    }

    public void setOptionalUpdate(boolean optionalUpdate) {
        mOptionalUpdate = optionalUpdate;
    }

    public String getApkUrl() {
        return mApkUrl;
    }

    public void setApkUrl(String apkUrl) {
        mApkUrl = apkUrl;
    }

    public String getApkName() {
        return mApkName;
    }

    public void setApkName(String apkName) {
        mApkName = apkName;
    }

    public String getUpdateInfo() {
        return mUpdateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        mUpdateInfo = updateInfo;
    }

    public ObservableField<String> getCountTime() {
        return mCountTime;
    }
}
