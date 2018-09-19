package com.cody.xf.widget.pullloadmorerecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by shijia on 2017/8/16.
 */

public class TouchEventPullLoadMoreRecyclerView extends PullLoadMoreRecyclerView {
    private onReceiveTouchListener listener;
    public TouchEventPullLoadMoreRecyclerView(Context context) {
        super(context);
    }

    public TouchEventPullLoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnReceiveTouchListener(onReceiveTouchListener listener){
        this.listener=listener;

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (listener!=null)
            listener.onReceiveTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    public  interface  onReceiveTouchListener{
        void onReceiveTouchEvent(MotionEvent ev);
    }
}
