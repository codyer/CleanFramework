/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.handler.framework.viewmodel;

/**
 * Created by cody.yi on 2016/8/27.
 * 包含头部的ViewModel
 */
public class WithSearchHeaderViewModel extends ViewModel implements IWithSearchHeaderViewModel {
    private final SearchHeaderViewModel mHeaderViewModel = SearchHeaderViewModel.createDefaultHeader();//list view的头部

    @Override
    public SearchHeaderViewModel getSearchHeaderViewModel() {
        return mHeaderViewModel;
    }
}