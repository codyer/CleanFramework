package com.cody.app.framework.activity;

import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.FwActivityHeaderWithTabBinding;
import com.cody.app.framework.adapter.ChildTabPageFragmentAdapter;
import com.cody.app.framework.fragment.AbsListFragment;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.handler.framework.viewmodel.WithHeaderViewModel;
import com.cody.xf.widget.Scrollable;

import java.util.Arrays;
import java.util.List;

/**
 * Create by Cody.yi  on 2018/8/14.
 * description:包含头部和tab的activity
 */
public abstract class TabWithHeaderActivity<P extends Presenter<WithHeaderViewModel>> extends
        WithHeaderActivity<P, WithHeaderViewModel, FwActivityHeaderWithTabBinding> {
    private ChildTabPageFragmentAdapter mChildTabPageFragmentAdapter;

    @ArrayRes
    protected abstract int getChildTabTitles();

    protected abstract List<Fragment> getFragments();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取标签数据
        String[] titles = getResources().getStringArray(getChildTabTitles());
        //创建一个viewpager的adapter
        mChildTabPageFragmentAdapter = new ChildTabPageFragmentAdapter(getSupportFragmentManager(), Arrays
                .asList(titles));
        mChildTabPageFragmentAdapter.setFragments(getFragments());
        getBinding().viewPager.setAdapter(mChildTabPageFragmentAdapter);
        //将TabLayout和ViewPager关联起来
        getBinding().tabLayout.setViewPager(getBinding().viewPager);
        getBinding().viewPager.setOffscreenPageLimit(titles.length);
    }

    @Override
    protected WithHeaderViewModel buildViewModel(Bundle savedInstanceState) {
        return new WithHeaderViewModel();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fw_activity_header_with_tab;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.headerText:
                Fragment fragment = mChildTabPageFragmentAdapter.getItem(getBinding().viewPager.getCurrentItem());
                if (fragment != null && fragment instanceof Scrollable) {
                    ((Scrollable) fragment).scrollToTop();
                }
                break;
        }
    }
}
