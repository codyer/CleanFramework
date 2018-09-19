package com.cody.xf.widget.pullloadmorerecyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by WuXiaolong
 * on 2015/7/7.
 */
public class RecyclerViewOnScroll extends RecyclerView.OnScrollListener {
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

    public RecyclerViewOnScroll(PullLoadMoreRecyclerView pullLoadMoreRecyclerView) {
        this.mPullLoadMoreRecyclerView = pullLoadMoreRecyclerView;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastItem = 0;
        int firstItem = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = ((GridLayoutManager) layoutManager);
            firstItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
            if (firstItem == RecyclerView.NO_POSITION) {
                if (gridLayoutManager.getChildAt(0) != null && Math.abs(gridLayoutManager.getChildAt(0).getY()) <= 0.001f) {
                    firstItem = 0;
                }
            }
            //Position to find the final item of the current LayoutManager
            lastItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();
            if (lastItem == RecyclerView.NO_POSITION)
                lastItem = gridLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) layoutManager);
            firstItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            if (firstItem == RecyclerView.NO_POSITION) {
                if (linearLayoutManager.getChildAt(0) != null && Math.abs(linearLayoutManager.getChildAt(0).getY()) <= 0.001f) {
                    firstItem = 0;
                }
            }
            lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            if (lastItem == RecyclerView.NO_POSITION)
                lastItem = linearLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) layoutManager);
            // since may lead to the final item has more than one StaggeredGridLayoutManager the particularity of the so here that is an array
            // this array into an array of position and then take the maximum value that is the last show the position value
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
            lastItem = findMax(lastPositions);
            firstItem = staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions)[0];
        }
        if (firstItem == 0) {
            if (mPullLoadMoreRecyclerView.getPullRefreshEnable()) {
                mPullLoadMoreRecyclerView.setSwipeRefreshEnable(true);
            }
        } else {
            if (mPullLoadMoreRecyclerView.getPullRefreshEnable()) {
                mPullLoadMoreRecyclerView.setSwipeRefreshEnable(totalItemCount == 0);
            }
        }
        if (mPullLoadMoreRecyclerView.getPushRefreshEnable()
                && !mPullLoadMoreRecyclerView.isRefresh()
                && mPullLoadMoreRecyclerView.isHasMore()
                && (lastItem == totalItemCount - 1)
                && !mPullLoadMoreRecyclerView.isLoadMore()
                && (dx > 0 || dy > 0)) {
            mPullLoadMoreRecyclerView.setIsLoadMore(true);
            mPullLoadMoreRecyclerView.loadMore();
        }

    }
    //To find the maximum value in the array

    private int findMax(int[] lastPositions) {

        int max = lastPositions[0];
        for (int value : lastPositions) {
            //       int max    = Math.max(lastPositions,value);
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
