/*
 * Copyright (c)  Created by Cody.yi on 2016/9/11.
 */

package com.cody.app.framework.activity;

import android.os.Bundle;
import android.text.InputFilter;
import com.cody.app.framework.util.CommonUtil;

import com.cody.app.R;
import com.cody.app.databinding.RecycleLoadMoreWithHeaderSearchBinding;
import com.cody.app.framework.widget.SearchEditView;
import com.cody.handler.framework.presenter.RecycleViewPresenter;
import com.cody.handler.framework.viewmodel.BaseViewModel;
import com.cody.handler.framework.viewmodel.common.ListViewModel;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * Created by chy on 2016/9/11.
 * 可以加载更多的Activity基类,包含头部及搜索框
 */
public abstract class RecycleViewLoadMoreWithHeaderAndSearchActivity<P extends RecycleViewPresenter<ItemViewModel>,
        ItemViewModel extends BaseViewModel> extends AbstractRecycleViewWithHeaderActivity<P, ItemViewModel,
        ListViewModel<ItemViewModel>, RecycleLoadMoreWithHeaderSearchBinding> implements SearchEditView
        .OnSearchClickListener {

    protected SearchEditView mSearchEditView;

    @Override
    protected ListViewModel<ItemViewModel> buildViewModel(Bundle savedInstanceState) {
        return new ListViewModel<>();
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().pullLoadMoreRecyclerView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSearchEditView = getBinding().searchView;
        mSearchEditView.setOnSearchClickListener(this);
        InputFilter[] filters = {CommonUtil.getEmojFilter()};
        mSearchEditView.setFilters(filters);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.recycle_load_more_with_header_search_list;
    }
}