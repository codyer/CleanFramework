/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.app.framework.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by cody.yi on 2016/8/17.
 * 内部tab adapter
 * 使用recycle view 的list
 */
public class ChildTabPageFragmentAdapter extends FragmentPagerAdapter {

    public static final String TAB_TAG = "@tag@";
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public ChildTabPageFragmentAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    public ChildTabPageFragmentAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
        super(fm);
        mTitles = titles;
        setFragments(fragments);
    }

    /**
     * 需要添加的fragment初始化
     */
    public void setFragments(List<Fragment> fragments) {
        mFragments = fragments;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        String[] title = mTitles.get(position).split(TAB_TAG);
        Fragment fragment = mFragments.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).split(TAB_TAG)[0];
    }
}