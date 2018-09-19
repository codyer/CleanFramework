/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.app.framework.fragment;

import com.cody.app.R;
import com.cody.app.databinding.ListWithSearchBinding;
import com.cody.handler.framework.presenter.ListWithSearchPresenter;
import com.cody.handler.framework.viewmodel.ListWithSearchViewModel;
import com.cody.handler.framework.viewmodel.XItemViewModel;
import com.cody.xf.widget.SearchEditView;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * 子fragment，最里层的包含recycle view的fragment，支持下拉上拉刷新，页码控制
 * 包含搜索框
 */
public abstract class ListWithSearchFragment<P extends ListWithSearchPresenter<ItemViewModel>,
        ItemViewModel extends XItemViewModel>
        extends AbsListFragment<P,
        ListWithSearchViewModel<ItemViewModel>,
        ItemViewModel,
        ListWithSearchBinding>
        implements SearchEditView.OnSearchClickListener {

    /**
     * 通过binding 返回 pullLoadMoreRecyclerView
     * getBinding().pullLoadMoreRecyclerView;
     */
    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        getBinding().fwSearch.searchView.setOnSearchClickListener(this);
        return getBinding().fwList;
    }

    @Override
    protected int getEmptyViewId() {
        return R.layout.fw_empty_view;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fw_list_with_search;
    }

}
