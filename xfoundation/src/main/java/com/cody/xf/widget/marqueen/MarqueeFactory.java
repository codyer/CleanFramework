package com.cody.xf.widget.marqueen;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cody.yi on 2017/8/1.
 * some useful information
 */
public abstract class MarqueeFactory<E> {
    protected Context mContext;
    private MarqueeView mMarqueeView;

    public MarqueeFactory(Context context, MarqueeView marqueeView) {
        mContext = context;
        mMarqueeView = marqueeView;
    }

    public abstract View createItemView(int type, E data);

    public abstract int getViewType(int position);

    //适用于多次（含一次）更新数据源
    public void setData(final List<E> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        List<View> views = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            E data = list.get(i);
            View view = createItemView(getViewType(i), data);
            if (view == null) continue;
            ViewGroup group = (ViewGroup) view.getParent();
            if (group != null) {
                group.removeAllViews();
            }
            views.add(view);
        }

        if (mMarqueeView != null) {
            mMarqueeView.setMarqueeViews(views);
        }
    }

    public MarqueeView getMarqueeView() {
        return mMarqueeView;
    }
}
