/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.app.framework.activity;

import android.os.Bundle;

import com.cody.app.R;
import com.cody.app.databinding.RecycleLoadMoreWithHeaderBinding;
import com.cody.handler.framework.presenter.RecycleViewPresenter;
import com.cody.handler.framework.viewmodel.BaseViewModel;
import com.cody.handler.framework.viewmodel.common.ListViewModel;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * Created by cody.yi on 2016/8/26.
 * 可以加载更多的Activity基类,包含头部
 */
public abstract class RecycleViewLoadMoreWithHeaderActivity<P extends RecycleViewPresenter<ItemViewModel>,
        ItemViewModel extends BaseViewModel> extends AbstractRecycleViewWithHeaderActivity<P,
        ItemViewModel, ListViewModel<ItemViewModel>,
        RecycleLoadMoreWithHeaderBinding> {

    @Override
    protected ListViewModel<ItemViewModel> buildViewModel(Bundle savedInstanceState) {
        return new ListViewModel<>();
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().pullLoadMoreRecyclerView;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.recycle_load_more_with_header_list;
    }

    /**
     * 定制empty view
     */
    protected int getEmptyViewId() {
        return R.layout.empty_view;
    }

}
