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
import com.cody.app.databinding.ListWithHeaderBinding;
import com.cody.handler.framework.presenter.ListWithHeaderPresenter;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.handler.framework.viewmodel.ListWithHeaderViewModel;
import com.cody.handler.framework.viewmodel.XItemViewModel;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * 子fragment，最里层的包含recycle view的fragment，支持下拉上拉刷新，页码控制
 * 如果设置headerItem 可以显示头部
 */
public abstract class ListWithHeaderFragment<P extends ListWithHeaderPresenter<ItemViewModel>,
        ItemViewModel extends XItemViewModel>
        extends AbsListFragment<P,
        ListWithHeaderViewModel<ItemViewModel>,
        ItemViewModel,
        ListWithHeaderBinding> {
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
        return getBinding().fwList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initHeader(getViewModel().getHeaderViewModel());
        return view;
    }

    @Override
    protected int getEmptyViewId() {
        return R.layout.fw_empty_view;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fw_list_with_header;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headerText:
                scrollToTop();
                break;
        }
    }
}
