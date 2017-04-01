package com.cody.app.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by daya on 2016/12/19.
 */

public class TabIndicator extends LinearLayout {
    private OnClickListener onClickListener;

    public TabIndicator(Context context) {
        this(context, null);
    }

    public TabIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.onClickListener = l;
        super.setOnClickListener(l);
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }
}
