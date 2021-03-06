/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.app.framework.activity;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.cody.app.R;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.handler.framework.viewmodel.IWithHeaderViewModel;

/**
 * Created by cody.yi on 2016/8/27.
 * 包含头部的Activity
 * <P,VM,B>
 */
public abstract class WithHeaderActivity<P extends Presenter<VM>, VM extends IWithHeaderViewModel, B extends
        ViewDataBinding> extends BaseBindingActivity<P, VM, B> {

    /**
     * 创建标题
     * 返回空或者默认的HeaderViewModel不会显示头部，必须设置头部的visible
     *
     * @see HeaderViewModel#setVisible
     */
    protected abstract void initHeader(HeaderViewModel header);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeader(getViewModel().getHeaderViewModel());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
        }
    }
}
