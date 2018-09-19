/*
 * Copyright (c)  Created by Cody.yi on 2016/9/11.
 */

package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.cody.handler.BuildConfig;
import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

/**
 * Created by cody.yi on 2016/8/19.
 * 设计师 个人信息
 */
public class SettingViewModel extends WithHeaderViewModel {
    public final ObservableField<String> account = new ObservableField<>("");
    public final ObservableField<String> serviceEnd = new ObservableField<>("");
    public final ObservableField<String> cache = new ObservableField<>("");
    public final ObservableField<String> userRole = new ObservableField<>("");
    private final ObservableBoolean mIsDebug = new ObservableBoolean(BuildConfig.DEBUG);

    public ObservableBoolean getIsDebug() {
        return mIsDebug;
    }
}
