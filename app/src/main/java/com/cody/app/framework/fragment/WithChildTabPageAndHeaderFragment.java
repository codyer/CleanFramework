/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.app.framework.fragment;

import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cody.app.R;
import com.cody.app.databinding.FwFragmentTabViewBinding;
import com.cody.app.framework.adapter.ChildTabPageFragmentAdapter;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cody.yi on 2016/8/23.
 * 子tab中包含多页面 的viewpager 容器抽象
 * 只包含一个头部和一个viewpager
 */
public abstract class WithChildTabPageAndHeaderFragment<P extends Presenter<WithHeaderViewModel>>
        extends WithHeaderFragment<P, WithHeaderViewModel, FwFragmentTabViewBinding> {
    private ChildTabPageFragmentAdapter adapter;
    private String[] titles;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取标签数据
        titles = getResources().getStringArray(getChildTabTitles());
        //创建一个viewpager的adapter
        adapter = new ChildTabPageFragmentAdapter(getChildFragmentManager(), Arrays
                .asList(titles));
        adapter.setFragments(getFragments());
    }

    @NonNull
    @Override
    protected WithHeaderViewModel buildViewModel(Bundle savedInstanceState) {
        return new WithHeaderViewModel();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fw_fragment_tab_view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initData();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headerText:
                Fragment fragment = adapter.getItem(getBinding().viewPager.getCurrentItem());
                if (fragment instanceof AbsListFragment) {
                    ((AbsListFragment) fragment).scrollToTop();
                }
                break;
        }
    }

    private void initData() {
        getBinding().viewPager.setAdapter(adapter);
        getBinding().tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //将TabLayout和ViewPager关联起来
        getBinding().tabLayout.setupWithViewPager(getBinding().viewPager);
        getBinding().viewPager.setOffscreenPageLimit(titles.length);
    }

    @ArrayRes
    protected abstract int getChildTabTitles();

    protected abstract List<Fragment> getFragments();

}
