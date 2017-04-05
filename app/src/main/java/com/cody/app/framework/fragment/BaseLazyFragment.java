/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.app.framework.fragment;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cody.handler.framework.viewmodel.BaseViewModel;
import com.cody.handler.framework.presenter.Presenter;

/**
 * MVVM架构的基类，将ViewModel的属性和行为进行拆分，行为交由P处理，属性由VM持有
 *
 * @param <P>  处理逻辑的类，从ViewModel拆出来的行为
 * @param <VM> 所有ViewModel中原来的属性；
 * @param <B>  和V（XML）进行绑定的自动生成的类，可以通过data节点添加class自定义binding的类名
 */
public abstract class BaseLazyFragment<P extends Presenter<VM>, VM extends BaseViewModel, B extends ViewDataBinding>
        extends BaseBindingFragment<P, VM, B> {

    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
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

    /**
     * when fragment is invisible for the first time
     */
//    private void onFirstUserInvisible() {
//        // here we do not recommend do something
//    }

    /**
     * this method like the fragment's lifecycle method onPause()
     */
//    protected void onUserInvisible() {
//    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (isFirstResume) {
//            isFirstResume = false;
//            return;
//        }
//        if (getUserVisibleHint()) {
//            onUserVisible();
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        if (getUserVisibleHint()) {
//            onUserInvisible();
//        }
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
//        else {
//            if (isFirstInvisible) {
//                isFirstInvisible = false;
//                onFirstUserInvisible();
//            } else {
//                onUserInvisible();
//            }
//        }
//        if (isVisibleToUser) {
//            if (isFirstVisible) {
//                initPrepare();
//            } else {
//                onUserVisible();
//            }
//        } else {
//            if (isFirstInvisible) {
//                isFirstInvisible = false;
//                onFirstUserInvisible();
//            } else {
//                onUserInvisible();
//            }
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        isPrepared = true;
        return view;
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
//        if (isPrepared) {
//            if (isFirstVisible){
//                onFirstUserVisible();
//                isFirstVisible = false;
//            }
//        } else {
//            isPrepared = true;
//        }
    }
}
