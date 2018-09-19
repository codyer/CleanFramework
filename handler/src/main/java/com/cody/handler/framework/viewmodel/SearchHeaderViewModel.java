/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.handler.framework.viewmodel;

import android.databinding.ObservableField;

/**
 * Created by cody.yi on 2016/8/27.
 * 包含头部的ViewModel
 */
public class SearchHeaderViewModel extends ViewModel {
    private ObservableField<String> mHintText = new ObservableField<>(""); // 提示语
    private ObservableField<String> mCity = new ObservableField<>(""); // 定位城市

    public static SearchHeaderViewModel createDefaultHeader() {
        return new SearchHeaderViewModel();
    }

    public ObservableField getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity.set(city);
    }

    public ObservableField getHintText() {
        return mHintText;
    }

    public void setHintText(String hintText) {
        mHintText.set(hintText);
    }

}