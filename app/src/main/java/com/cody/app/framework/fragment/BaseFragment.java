/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.app.framework.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cody.app.BR;
import com.cody.app.R;
import com.cody.handler.framework.viewmodel.BaseViewModel;
import com.cody.xf.binding.fragment.BaseBindingFragment;
import com.cody.xf.binding.handler.Presenter;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.StringUtil;
import com.cody.xf.utils.ToastUtil;

/**
 * MVVM架构的基类，将ViewModel的属性和行为进行拆分，行为交由P处理，属性由VM持有
 *
 * @param <P>  处理逻辑的类，从ViewModel拆出来的行为
 * @param <VM> 所有ViewModel中原来的属性；
 * @param <B>  和V（XML）进行绑定的自动生成的类，可以通过data节点添加class自定义binding的类名
 */
public abstract class BaseFragment<P extends Presenter<VM>, VM extends BaseViewModel, B extends ViewDataBinding> extends
        BaseBindingFragment<P, VM, B> implements View.OnClickListener, DialogInterface.OnCancelListener {

    private ProgressDialog mLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mLoading = new ProgressDialog(getActivity());
        mLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mLoading.setCanceledOnTouchOutside(false);
        mLoading.setCancelable(true);
        mLoading.setMessage(getString(R.string.r_load_more_text));
        mLoading.setOnCancelListener(this);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @CallSuper
    @Override
    public void showLoading(String msg) {
        LogUtil.d(TAG, "BaseFragment ++ showLoading");
        if (StringUtil.isEmpty(msg)) {
            mLoading.setMessage(getString(R.string.r_load_more_text));
        } else {
            mLoading.setMessage(msg);
        }
        if (!mLoading.isShowing()) {
            mLoading.show();
        }
    }

    @CallSuper
    @Override
    public void hideLoading() {
        LogUtil.d(TAG, "BaseFragment ++ hideLoading");
        if (mLoading.isShowing()) {
            mLoading.dismiss();
        }
    }

    @CallSuper
    @Override
    public void showFailure(String msg) {
        this.hideLoading();
        ToastUtil.showToast(msg);
        LogUtil.d(TAG, "BaseFragment ++ showFailure msg = " + msg);
    }

    @CallSuper
    @Override
    public void showError(String msg) {
        this.hideLoading();
        LogUtil.d(TAG, "BaseFragment ++ showError msg = " + msg);
    }

    @CallSuper
    @Override
    public void onProgress(long count, long current) {
        LogUtil.d(TAG, "BaseFragment ++ onProgress");
    }

    @CallSuper
    @Override
    public void onUpdate(Object... args) {
        this.hideLoading();
        getBinding().setVariable(BR.viewModel, getViewModel());
        LogUtil.d(TAG, "BaseFragment ++ onUpdate");
    }

    @CallSuper
    @Override
    public void onCancel(DialogInterface dialog) {
        this.hideLoading();
        LogUtil.d(TAG, "BaseFragment ++ showError");
        getPresenter().cancel(TAG);
    }
}
