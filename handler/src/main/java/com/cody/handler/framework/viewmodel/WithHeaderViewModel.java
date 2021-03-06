/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.handler.framework.viewmodel;

/**
 * Created by cody.yi on 2016/8/27.
 * 包含头部的ViewModel
 */
public class WithHeaderViewModel extends ViewModel implements IWithHeaderViewModel {
    private final HeaderViewModel mHeaderViewModel = HeaderViewModel.createDefaultHeader();//list view的头部

    @Override
    public HeaderViewModel getHeaderViewModel() {
        return mHeaderViewModel;
    }
}