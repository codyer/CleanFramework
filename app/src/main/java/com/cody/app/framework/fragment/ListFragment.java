/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.app.framework.fragment;


import android.os.Bundle;

import com.cody.app.R;
import com.cody.app.databinding.ListBinding;
import com.cody.handler.framework.presenter.ListPresenter;
import com.cody.handler.framework.viewmodel.ListViewModel;
import com.cody.handler.framework.viewmodel.XItemViewModel;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * 子fragment，最里层的包含recycle view的fragment，支持下拉上拉刷新，页码控制
 * 不包含头部
 */
public abstract class ListFragment<P extends ListPresenter<ItemViewModel>, ItemViewModel extends
        XItemViewModel> extends AbsListFragment<P, ListViewModel<ItemViewModel>, ItemViewModel, ListBinding> {

    @Override
    protected ListViewModel<ItemViewModel> buildViewModel(Bundle savedInstanceState) {
        return new ListViewModel<>();
    }

    /**
     * 通过binding 返回 pullLoadMoreRecyclerView
     * getBinding().pullLoadMoreRecyclerView;
     */
    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    protected int getEmptyViewId() {
        return R.layout.fw_empty_view;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fw_list;
    }
}

