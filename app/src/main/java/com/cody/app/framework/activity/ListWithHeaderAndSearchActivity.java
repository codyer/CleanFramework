/*
 * Copyright (c)  Created by Cody.yi on 2016/9/11.
 */

package com.cody.app.framework.activity;

import android.os.Bundle;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ListWithHeaderAndSearchBinding;
import com.cody.handler.framework.presenter.ListWithHeaderAndSearchPresenter;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.handler.framework.viewmodel.ListWithHeaderAndSearchViewModel;
import com.cody.handler.framework.viewmodel.XItemViewModel;
import com.cody.xf.widget.SearchEditView;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * Created by chy on 2016/9/11.
 * 可以加载更多的Activity基类,包含头部及搜索框
 */
public abstract class ListWithHeaderAndSearchActivity<P extends ListWithHeaderAndSearchPresenter<ItemViewModel>,
        ItemViewModel extends XItemViewModel>
        extends AbsListActivity<P,
        ListWithHeaderAndSearchViewModel<ItemViewModel>,
        ItemViewModel,
        ListWithHeaderAndSearchBinding>
        implements SearchEditView.OnSearchClickListener {

    protected SearchEditView mSearchEditView;

    /**
     * 创建标题
     * 返回空或者默认的HeaderViewModel不会显示头部，必须设置头部的visible
     *
     * @see HeaderViewModel#setVisible
     */
    protected abstract void initHeader(HeaderViewModel header);

    @Override
    protected ListWithHeaderAndSearchViewModel<ItemViewModel> buildViewModel(Bundle savedInstanceState) {
        ListWithHeaderAndSearchViewModel<ItemViewModel> listWithHeaderViewModel = new ListWithHeaderAndSearchViewModel<>();
        initHeader(listWithHeaderViewModel.getHeaderViewModel());
        return listWithHeaderViewModel;
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
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fw_list_with_header_and_search;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
            case R.id.headerText:
                scrollToTop();
                break;
        }
    }
}