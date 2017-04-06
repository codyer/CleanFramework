/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.app.framework.fragment;

import com.cody.app.R;
import com.cody.app.databinding.ListWithHeaderAndSearchBinding;
import com.cody.app.framework.widget.SearchEditView;
import com.cody.handler.framework.presenter.ListWithHeaderAndSearchPresenter;
import com.cody.handler.framework.viewmodel.ListWithHeaderAndSearchViewModel;
import com.cody.handler.framework.viewmodel.ViewModel;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * 子fragment，最里层的包含recycle view的fragment，支持下拉上拉刷新，页码控制
 * 包含搜索框
 */
public abstract class ListWithHeaderAndSearchFragment<
        P extends ListWithHeaderAndSearchPresenter<ItemViewModel>,
        ItemViewModel extends ViewModel>
        extends AbsListFragment<P,
        ListWithHeaderAndSearchViewModel<ItemViewModel>,
        ItemViewModel,
        ListWithHeaderAndSearchBinding>
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
    protected int getLayoutID() {
        return R.layout.fw_list_with_header_and_search;
    }

}
