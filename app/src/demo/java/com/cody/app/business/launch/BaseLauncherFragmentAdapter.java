package com.cody.app.business.launch;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by cody.yi on 2017/9/4.
 * 适配器
 */
public class BaseLauncherFragmentAdapter extends FragmentStatePagerAdapter {
    private List<LauncherBaseFragment> mFragments;

    public BaseLauncherFragmentAdapter(FragmentManager fm, List<LauncherBaseFragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    public BaseLauncherFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public LauncherBaseFragment getItem(int arg0) {
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}