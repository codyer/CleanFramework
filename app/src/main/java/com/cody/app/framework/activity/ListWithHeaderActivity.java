/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.app.framework.activity;

import android.os.Bundle;

import com.cody.app.R;
import com.cody.app.databinding.ListWithHeaderBinding;
import com.cody.handler.framework.presenter.ListWithHeaderPresenter;
import com.cody.handler.framework.viewmodel.ListWithHeaderViewModel;
import com.cody.handler.framework.viewmodel.XItemViewModel;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * Created by cody.yi on 2016/8/26.
 * 可以加载更多的Activity基类,包含头部
 * <P,itemVM>
 */
public abstract class ListWithHeaderActivity<P extends ListWithHeaderPresenter<ItemViewModel>,
        ItemViewModel extends XItemViewModel>
        extends ListWithHeaderBaseActivity<P, ListWithHeaderViewModel<ItemViewModel>, ItemViewModel,
        ListWithHeaderBinding> {

    @Override
    protected ListWithHeaderViewModel<ItemViewModel> buildViewModel(Bundle savedInstanceState) {
        ListWithHeaderViewModel<ItemViewModel> listWithHeaderViewModel = new ListWithHeaderViewModel<>();
        initHeader(listWithHeaderViewModel.getHeaderViewModel());
        return listWithHeaderViewModel;
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fw_list_with_header;
    }
}
