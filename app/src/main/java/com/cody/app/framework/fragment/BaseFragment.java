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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mobstat.StatService;
import com.cody.app.R;
import com.cody.handler.framework.IView;
import com.cody.repository.framework.statistics.HxStat;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.ResourceUtil;
import com.cody.xf.utils.http.SimpleBean;

public abstract class BaseFragment extends Fragment implements DialogInterface.OnCancelListener, IView {

    public String TAG = null;
    private ProgressDialog mLoading;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initLoading();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
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

    @Override
    public void onResume() {
        super.onResume();
        HxStat.onResume(this);
        StatService.onPageStart(getContext(), TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        HxStat.onPause(this);
        StatService.onPageEnd(getContext(), TAG);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        HxStat.setUserVisibleHint(this, isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        hideLoading();
        super.onDestroy();
    }

    @CallSuper
    @Override
    public void showLoading(String msg) {
        LogUtil.d(TAG, "BaseFragment ++ showLoading");
        if (TextUtils.isEmpty(msg)) {
            mLoading.setMessage(getString(R.string.fw_html_loading));
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
    public void showFailure(SimpleBean msg) {
        this.hideLoading();
        LogUtil.d(TAG, "BaseFragment ++ showFailure msg = " + msg);
    }

    @CallSuper
    @Override
    public void showError(SimpleBean msg) {
        this.hideLoading();
        LogUtil.d(TAG, "BaseFragment ++ showError msg = " + msg);
    }

    @CallSuper
    @Override
    public void onProgress(int progress, int max) {
        LogUtil.d(TAG, "BaseFragment ++ onProgress");
        mProgressDialog.setMax(max);
        mProgressDialog.setProgress(progress);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
        if (progress == max && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
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
            mLoading.setCanceledOnTouchOutside(false);
            mLoading.setCancelable(true);
            mLoading.setMessage(getString(R.string.fw_html_loading));
            mLoading.setOnCancelListener(this);
        }
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle(ResourceUtil.getString(R.string.image_upload));
            mProgressDialog.setProgressNumberFormat(null);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setOnCancelListener(this);
        }
    }
}
