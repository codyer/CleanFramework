/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.app.framework.activity;

import android.os.Bundle;

import com.cody.app.R;
import com.cody.app.databinding.ListWithSearchBinding;
import com.cody.handler.framework.presenter.ListWithSearchPresenter;
import com.cody.handler.framework.viewmodel.ListWithSearchViewModel;
import com.cody.handler.framework.viewmodel.XItemViewModel;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * Created by cody.yi on 2016/8/26.
 * 可以加载更多的Activity基类,不包含头部
 */
public abstract class ListWithSearchActivity<
        P extends ListWithSearchPresenter<ItemViewModel>,
        ItemViewModel extends XItemViewModel>
        extends AbsListActivity<P,
        ListWithSearchViewModel<ItemViewModel>,
        ItemViewModel,
        ListWithSearchBinding> {

    @Override
    protected ListWithSearchViewModel<ItemViewModel> buildViewModel(Bundle savedInstanceState) {
        return new ListWithSearchViewModel<>();
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fw_list_with_search;
    }

    /**
     * 定制empty view
     */
    protected int getEmptyViewId() {
        return R.layout.fw_empty_view;
    }
}
