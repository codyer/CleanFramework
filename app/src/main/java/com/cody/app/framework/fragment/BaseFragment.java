/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.app.framework.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cody.app.R;
import com.cody.handler.framework.IView;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.StringUtil;
import com.cody.xf.utils.ToastUtil;

public abstract class BaseFragment extends Fragment implements DialogInterface.OnCancelListener, IView {

    public String TAG = null;
    private ProgressDialog mLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initLoading();
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
            mLoading.setMessage(getString(R.string.xf_load_more_text));
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
        LogUtil.d(TAG, "BaseFragment ++ onUpdate");
    }

    @CallSuper
    @Override
    public void onCancel(DialogInterface dialog) {
        this.hideLoading();
        LogUtil.d(TAG, "BaseFragment ++ showError");
    }

    private void initLoading() {
        if (mLoading == null) {
            mLoading = new ProgressDialog(getActivity());
            mLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mLoading.setCanceledOnTouchOutside(false);
            mLoading.setCancelable(true);
            mLoading.setMessage(getString(R.string.xf_load_more_text));
            mLoading.setOnCancelListener(this);
        }
    }
}
