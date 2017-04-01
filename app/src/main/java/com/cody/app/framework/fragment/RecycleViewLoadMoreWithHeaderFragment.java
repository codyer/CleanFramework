/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.app.framework.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cody.app.R;
import com.cody.app.databinding.RecycleLoadMoreWithHeaderBinding;
import com.cody.handler.framework.presenter.RecycleViewPresenter;
import com.cody.handler.framework.viewmodel.BaseViewModel;
import com.cody.handler.framework.viewmodel.common.HeaderViewModel;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * 子fragment，最里层的包含recycle view的fragment，支持下拉上拉刷新，页码控制
 * 如果设置headerItem 可以显示头部
 */
public abstract class RecycleViewLoadMoreWithHeaderFragment<P extends RecycleViewPresenter<ItemViewModel>,
        ItemViewModel extends BaseViewModel> extends AbstractRecycleViewFragment<P, ItemViewModel,
        RecycleLoadMoreWithHeaderBinding> {
    /**
     * 创建标题
     * 返回空或者默认的HeaderViewModel不会显示头部，必须设置头部的visible
     *
     * @see HeaderViewModel#setVisible
     */
    protected abstract void initHeader(HeaderViewModel header);

    /**
     * 通过binding 返回 pullLoadMoreRecyclerView
     * getBinding().pullLoadMoreRecyclerView;
     */
    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().pullLoadMoreRecyclerView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initHeader(getViewModel().getHeaderViewModel());
        return view;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.recycle_load_more_with_header_list;
    }

}
