/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.app.framework.fragment;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cody.handler.framework.viewmodel.WithHeaderViewModel;
import com.cody.handler.framework.viewmodel.common.HeaderViewModel;
import com.cody.xf.binding.handler.Presenter;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class WithHeaderFragment<P extends Presenter<VM>, VM extends WithHeaderViewModel, B extends
        ViewDataBinding> extends BaseLazyFragment<P, VM, B> {

    /**
     * 创建标题
     * 返回空或者默认的HeaderViewModel不会显示头部，必须设置头部的visible
     *
     * @see HeaderViewModel#setVisible
     */
    protected abstract void initHeader(HeaderViewModel header);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initHeader(getViewModel().getHeaderViewModel());
        return view;
    }
}
