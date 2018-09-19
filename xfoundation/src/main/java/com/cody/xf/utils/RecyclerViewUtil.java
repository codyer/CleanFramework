package com.cody.xf.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by dong.wang
 * Date: 2017/9/15
 * Time: 17:22
 * Description:
 */
public class RecyclerViewUtil {
    private final static int MAGIC_POSITION = 10;//正常情况下，10个item会超过两屏效果比较正常，因此魔术数字定为10

    public static void smoothScrollToTop(RecyclerView recyclerView) {
        if (recyclerView == null || recyclerView.getChildCount() == 0) return;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            int position = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            if (position > MAGIC_POSITION) {
                recyclerView.scrollToPosition(MAGIC_POSITION);
            }
        }
        recyclerView.smoothScrollToPosition(0);
    }
}
