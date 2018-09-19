/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.app.framework.activity;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.cody.app.R;
import com.cody.handler.framework.presenter.ListWithHeaderBasePresenter;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.handler.framework.viewmodel.ListWithHeaderViewModel;
import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Created by cody.yi on 2016/8/26.
 * 可以加载更多的Activity基类,包含头部
 * <P,itemVM>
 */
public abstract class ListWithHeaderBaseActivity<P extends ListWithHeaderBasePresenter<ListHeaderViewModel, ItemViewModel>,
        ListHeaderViewModel extends ListWithHeaderViewModel<ItemViewModel>,
        ItemViewModel extends XItemViewModel,
        B extends ViewDataBinding>
        extends AbsListActivity<P, ListHeaderViewModel, ItemViewModel, B> {

    /**
     * 创建标题
     * 返回空或者默认的HeaderViewModel不会显示头部，必须设置头部的visible
     *
     * @see HeaderViewModel#setVisible
     */
    protected abstract void initHeader(HeaderViewModel header);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeader(getViewModel().getHeaderViewModel());
    }

    /**
     * 定制empty view
     */
    protected int getEmptyViewId() {
        return R.layout.fw_empty_view;
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
