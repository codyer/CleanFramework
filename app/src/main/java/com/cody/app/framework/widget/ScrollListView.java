/*
 * Copyright (c)  Created by Cody.yi on 2016/8/29.
 */

package com.cody.app.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 滑动布局中的listView
 */

public class ScrollListView extends ListView {

    public ScrollListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public ScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
