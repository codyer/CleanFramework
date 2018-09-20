/*
 * Copyright (c) 2017.   Cody.yi Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cody.app.business.binding;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ConstraintBinding;
import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.handler.business.presenter.CasePresenter;
import com.cody.handler.business.viewmodel.CaseViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;

public class ConstraintDemoActivity extends WithHeaderActivity<CasePresenter, CaseViewModel, ConstraintBinding> {

    private ConstraintSet mConstraintSet1;
    private ConstraintSet mConstraintSet2;
    private ConstraintLayout mConstraintLayout;
    private boolean mExpended = false;

    /**
     * 创建标题
     * 返回空或者默认的HeaderViewModel不会显示头部，必须设置头部的visible
     *
     * @param header
     * @see HeaderViewModel#setVisible
     */
    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle("Case test");
    }

    /**
     * 子类提供有binding的资源ID
     */
    @Override
    protected int getLayoutID() {
        return R.layout.activity_constraint_demo;
    }

    /**
     * 每个view保证只有一个Presenter
     */
    @Override
    protected CasePresenter buildPresenter() {
        return new CasePresenter();
    }

    /**
     * 每个view保证只有一个ViewModel，当包含其他ViewModel时使用根ViewModel包含子ViewModel
     * ViewModel可以在创建的时候进行初始化，也可以在正在进行绑定（#setBinding）的时候初始化
     *
     * @param savedInstanceState
     */
    @Override
    protected CaseViewModel buildViewModel(Bundle savedInstanceState) {
        mConstraintSet1 = new ConstraintSet();
        mConstraintSet2 = new ConstraintSet();
        mConstraintLayout = getBinding().constraintLayout;
        mConstraintSet1.clone(mConstraintLayout);
        mConstraintSet2.clone(this, R.layout.activity_constraint_demo_expend);
        return new CaseViewModel();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView2:
                mExpended = !mExpended;
                if (mExpended) {
                    getViewModel().getHeaderViewModel().setTitle("Info+++++");
                    TransitionManager.beginDelayedTransition(mConstraintLayout);
                    mConstraintSet2.applyTo(mConstraintLayout);
                    getBinding().executePendingBindings();
                } else {
                    getViewModel().getHeaderViewModel().setTitle("Name+++++");
                    TransitionManager.beginDelayedTransition(mConstraintLayout);
                    mConstraintSet1.applyTo(mConstraintLayout);
                    getBinding().executePendingBindings();
                }
                break;
            case R.id.editText:
                getViewModel().getHeaderViewModel().setTitle("Title+++++"+System.currentTimeMillis());
                break;
        }

    }
}
