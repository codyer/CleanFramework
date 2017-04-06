/*
 * Copyright (c)  Created by Cody.yi on 2016/9/11.
 */

package com.cody.app.framework.activity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ListWithHeaderAndSearchBinding;
import com.cody.app.framework.widget.SearchEditView;
import com.cody.handler.framework.presenter.ListWithHeaderAndSearchPresenter;
import com.cody.handler.framework.viewmodel.ListWithHeaderAndSearchViewModel;
import com.cody.handler.framework.viewmodel.ViewModel;
import com.cody.xf.utils.CommonUtil;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * Created by chy on 2016/9/11.
 * 可以加载更多的Activity基类,包含头部及搜索框
 */
public abstract class ListWithHeaderAndSearchActivity<P extends ListWithHeaderAndSearchPresenter<ItemViewModel>,
        ItemViewModel extends ViewModel>
        extends AbsListActivity<P,
        ListWithHeaderAndSearchViewModel<ItemViewModel>,
        ItemViewModel,
        ListWithHeaderAndSearchBinding>
        implements SearchEditView.OnSearchClickListener {

    protected SearchEditView mSearchEditView;

    @Override
    protected ListWithHeaderAndSearchViewModel<ItemViewModel> buildViewModel(Bundle savedInstanceState) {
        return new ListWithHeaderAndSearchViewModel<>();
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSearchEditView = getBinding().fwSearch.searchView;
        mSearchEditView.setOnSearchClickListener(this);
        InputFilter[] filters = {CommonUtil.getEmojFilter()};
        mSearchEditView.setFilters(filters);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fw_list_with_header_and_search;
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