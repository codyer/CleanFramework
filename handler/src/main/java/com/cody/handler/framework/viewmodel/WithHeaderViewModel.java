/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.handler.framework.viewmodel;

import android.databinding.ObservableField;

import com.cody.handler.framework.viewmodel.common.HeaderViewModel;

/**
 * Created by cody.yi on 2016/8/27.
 * 包含头部的ViewModel
 */
public class WithHeaderViewModel extends BaseViewModel {
    private final ObservableField<HeaderViewModel> mHeaderViewModel = new ObservableField<>(HeaderViewModel.createDefaultHeader());//list view的头部

    public HeaderViewModel getHeaderViewModel() {
        return mHeaderViewModel.get();
    }
}
