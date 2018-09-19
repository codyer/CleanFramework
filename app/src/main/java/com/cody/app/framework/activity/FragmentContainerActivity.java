package com.cody.app.framework.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.cody.app.R;
import com.cody.app.databinding.FwActivityFragmentCotainerBinding;
import com.cody.handler.framework.presenter.DefaultWithHeaderPresenter;
import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

/**
 * Create by Cody.yi  on 2018/8/21.
 * description:fragment 容器
 */
public abstract class FragmentContainerActivity extends WithHeaderActivity<DefaultWithHeaderPresenter, WithHeaderViewModel, FwActivityFragmentCotainerBinding> {
    public abstract Fragment buildFragment();

    @Override
    protected int getLayoutID() {
        return R.layout.fw_activity_fragment_cotainer;
    }

    @Override
    protected DefaultWithHeaderPresenter buildPresenter() {
        return new DefaultWithHeaderPresenter();
    }

    @Override
    protected WithHeaderViewModel buildViewModel(Bundle savedInstanceState) {
        return new WithHeaderViewModel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = buildFragment();
        if (fragment == null) {
            finish();
            return;
        }
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}
