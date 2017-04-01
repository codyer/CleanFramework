/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.app.framework.fragment;

import com.cody.app.R;
import com.cody.app.databinding.RecycleLoadMoreWithSearchBinding;
import com.cody.app.framework.widget.SearchEditView;
import com.cody.handler.framework.presenter.RecycleViewPresenter;
import com.cody.handler.framework.viewmodel.BaseViewModel;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * 子fragment，最里层的包含recycle view的fragment，支持下拉上拉刷新，页码控制
 * 包含搜索框
 */
public abstract class RecycleViewLoadMoreWithSearchFragment<P extends RecycleViewPresenter<ItemViewModel>,
        ItemViewModel extends BaseViewModel> extends AbstractRecycleViewFragment<P, ItemViewModel,
        RecycleLoadMoreWithSearchBinding> implements SearchEditView.OnSearchClickListener {

    /**
     * 通过binding 返回 pullLoadMoreRecyclerView
     * getBinding().pullLoadMoreRecyclerView;
     */
    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        getBinding().searchView.setOnSearchClickListener(this);
        return getBinding().pullLoadMoreRecyclerView;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.recycle_load_more_with_search_list;
    }

}
