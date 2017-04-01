/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.app.framework.activity;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.cody.handler.framework.viewmodel.WithHeaderViewModel;
import com.cody.handler.framework.viewmodel.common.HeaderViewModel;
import com.cody.xf.binding.handler.Presenter;

/**
 * Created by cody.yi on 2016/8/27.
 * 包含头部的Activity
 */
public abstract class WithHeaderActivity<P extends Presenter<VM>, VM extends WithHeaderViewModel, B extends
        ViewDataBinding> extends BaseActivity<P, VM, B> {

    /**
     * 创建标题
     * 返回空或者默认的HeaderViewModel不会显示头部，必须设置头部的visible
     *
     * @see HeaderViewModel#setVisible
     */
    protected abstract void initHeader(HeaderViewModel header);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeader(getViewModel().getHeaderViewModel());
    }
}
