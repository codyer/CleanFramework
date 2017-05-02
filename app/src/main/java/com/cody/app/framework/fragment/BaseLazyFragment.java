/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.app.framework.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 懒加载
 */
public abstract class BaseLazyFragment extends BaseFragment {

    private boolean isFirstVisible = true;
    private boolean isPrepared = false;

    /**
     * when fragment is visible for the first time, here we can do some initialized work or refresh data only once
     */
    protected void onFirstUserVisible() {
    }

    /**
     * this method like the fragment's lifecycle method onResume()
     */
    protected void onUserVisible() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        isPrepared = true;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPrepared) {
            if (isFirstVisible) {
                isFirstVisible = false;
                onFirstUserVisible();
            } else {
                onUserVisible();
            }
        }
    }

    private void initPrepare() {
        if (getUserVisibleHint()) {
            if (isFirstVisible) {
                isFirstVisible = false;
                onFirstUserVisible();
            } else {
                onUserVisible();
            }
        }
    }
}
