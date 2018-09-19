package com.cody.app.business.easeui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by  qiaoping.xiao on 2017/9/15.
 */

public class VpAdapter extends PagerAdapter {


    private List<String> titles;
    private List<View> mListViews;

    public VpAdapter(List<View> mListViews,List<String> titles) {
        this.mListViews = mListViews;
        this.titles = titles;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        container.removeView(mListViews.get(position));
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mListViews.get(position), 0);
        return mListViews.get(position);
    }

    @Override
    public int getCount() {
        return mListViews == null ? 0 : mListViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
