package com.cody.app.business.binding;

import android.os.Bundle;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.CaseActivityBinding;
import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.handler.business.presenter.CasePresenter;
import com.cody.handler.business.viewmodel.CaseViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;

public class CaseActivity extends WithHeaderActivity<CasePresenter, CaseViewModel, CaseActivityBinding> {

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
        return R.layout.activity_case;
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
        return new CaseViewModel();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        getPresenter().getCaseList(TAG);
    }
}
