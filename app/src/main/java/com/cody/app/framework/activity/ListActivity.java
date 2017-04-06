/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.app.framework.activity;

import android.os.Bundle;

import com.cody.app.R;
import com.cody.app.databinding.ListBinding;
import com.cody.handler.framework.presenter.ListPresenter;
import com.cody.handler.framework.viewmodel.ListViewModel;
import com.cody.handler.framework.viewmodel.ViewModel;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * Created by cody.yi on 2016/8/26.
 * 可以加载更多的Activity基类,不包含头部
 */
public abstract class ListActivity<
        P extends ListPresenter<ItemViewModel>,
        ItemViewModel extends ViewModel>
        extends AbsListActivity<
        P,
        ListViewModel<ItemViewModel>,
        ItemViewModel,
        ListBinding> {

    @Override
    protected ListViewModel<ItemViewModel> buildViewModel(Bundle savedInstanceState) {
        return new ListViewModel<>();
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fw_list;
    }

    /**
     * 定制empty view
     */
    protected int getEmptyViewId() {
        return R.layout.empty_view;
    }
}
