/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.handler.framework.viewmodel;

/**
 * Created by cody.yi on 2016/8/27.
 * 包含头部的ViewModel
 */
public class WithHeaderViewModel extends ViewModel {
    public final HeaderViewModel headerViewModel = HeaderViewModel.createDefaultHeader();//list view的头部
}