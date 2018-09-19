/*
 * Copyright (c)  Created by Cody.yi on 2016/8/31.
 */

package com.cody.handler.business.viewmodel;

import android.databinding.ObservableField;

import com.cody.handler.framework.viewmodel.UpdateViewModel;

/**
 * Created by chy on 2016/8/31.
 * 加载界面的viewModel
 */
public class SplashViewModel extends UpdateViewModel {
    private ObservableField<String> countTime = new ObservableField<>("3s");

    public ObservableField<String> getCountTime() {
        return countTime;
    }

}
