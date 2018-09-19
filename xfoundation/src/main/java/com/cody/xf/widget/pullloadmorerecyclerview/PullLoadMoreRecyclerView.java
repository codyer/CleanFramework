package com.cody.xf.widget.pullloadmorerecyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cody.xf.R;

/**
 * Created by WuXiaolong on 2015/7/2.
 * github:https://github.com/WuXiaolong/PullLoadMoreRecyclerView
 * weibo:http://weibo.com/u/2175011601
 * 微信公众号：AndroidProgrammer
 * 博客：http://wuxiaolong.me/
 */
@SuppressWarnings("unused")
public class PullLoadMoreRecyclerView extends LinearLayout {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PullLoadMoreListener mPullLoadMoreListener;
    private boolean isRegister = false;//是否注册了mEmptyDataObserver
    private boolean hasMore = true;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private boolean pullRefreshEnable = true;
    private boolean pushRefreshEnable = true;
    private View mFooterView;
    private FrameLayout mEmptyViewContainer;
    private FrameLayout mDefaultViewContainer;
    private Context mContext;
    private TextView loadMoreText;
    private LinearLayout loadMoreLayout;
    private PullLoadMoreRecyclerView.AdapterDataObserver mEmptyDataObserver;

    public PullLoadMoreRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public PullLoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.xf_pull_loadmore_layout, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayoutOnRefresh(this));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setVerticalScrollBarEnabled(true);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerViewOnScroll(this));

        mRecyclerView.setOnTouchListener(new onTouchRecyclerView());

        mFooterView = view.findViewById(R.id.footerView);
        mEmptyViewContainer = (FrameLayout) view.findViewById(R.id.emptyView);
        mDefaultViewContainer = (FrameLayout) view.findViewById(R.id.defaultView);

        loadMoreLayout = (LinearLayout) view.findViewById(R.id.loadMoreLayout);
        loadMoreText = (TextView) view.findViewById(R.id.loadMoreText);

        mFooterView.setVisibility(View.GONE);
        mEmptyViewContainer.setVisibility(View.GONE);
        mDefaultViewContainer.setVisibility(View.GONE);

        this.addView(view);

    }

    public FrameLayout getEmptyViewContainer() {
        return mEmptyViewContainer;
    }

    /**
     * LinearLayoutManager
     */
    public void setLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * GridLayoutManager
     */

    public void setGridLayout(int spanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, spanCount);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }


    /**
     * StaggeredGridLayoutManager
     */

    public void setStaggeredGridLayout(int spanCount) {
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(spanCount, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mRecyclerView.getLayoutManager();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor, int index) {
        mRecyclerView.addItemDecoration(decor, index);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        mRecyclerView.addItemDecoration(decor);
    }

    public void scrollToTop() {
        mRecyclerView.scrollToPosition(0);
    }

    public void smoothScrollToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    public void scrollToPosition(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    public void smoothScrollToPosition(int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }

    public void setEmptyView(View emptyView) {
        mEmptyViewContainer.removeAllViews();
        mEmptyViewContainer.addView(emptyView);
    }

    public void setDefaultView(View defaultView, boolean show) {
        mDefaultViewContainer.removeAllViews();
        if (defaultView != null) {
            mDefaultViewContainer.addView(defaultView);
        }
        RecyclerView.Adapter<?> adapter = mRecyclerView.getAdapter();
        if (adapter != null && mDefaultViewContainer.getChildCount() != 0) {
            if (adapter.getItemCount() == 0 && show) {
                mEmptyViewContainer.setVisibility(View.GONE);
                mDefaultViewContainer.setVisibility(View.VISIBLE);
            } else {
                mDefaultViewContainer.setVisibility(View.GONE);
            }
        }
    }

    public void showEmptyView() {
        RecyclerView.Adapter<?> adapter = mRecyclerView.getAdapter();
        if (adapter != null && mEmptyViewContainer.getChildCount() != 0) {
            if (adapter.getItemCount() == 0) {
                mFooterView.setVisibility(View.GONE);
                mEmptyViewContainer.setVisibility(View.VISIBLE);
            } else {
                mEmptyViewContainer.setVisibility(View.GONE);
                mDefaultViewContainer.setVisibility(View.GONE);
            }
        }
    }

    /**
     * should called after onAttachedToWindow
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mRecyclerView.setAdapter(adapter);
            showEmptyView();
            registerEmptyDataObserver();
        }
    }

    private void registerEmptyDataObserver() {
        RecyclerView.Adapter<?> adapter = mRecyclerView.getAdapter();
        if (mEmptyDataObserver == null) {
            mEmptyDataObserver = new PullLoadMoreRecyclerView.AdapterDataObserver();
        }
        if (!isRegister && adapter != null) {
            synchronized (mEmptyDataObserver) {
                adapter.registerAdapterDataObserver(mEmptyDataObserver);
                isRegister = true;
            }
        }
    }

    private void unregisterEmptyDataObserver() {
        RecyclerView.Adapter<?> adapter = mRecyclerView.getAdapter();
        if (isRegister && adapter != null && mEmptyDataObserver != null) {
            synchronized (mEmptyDataObserver) {
                adapter.unregisterAdapterDataObserver(mEmptyDataObserver);
                isRegister = false;
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        registerEmptyDataObserver();
    }

    /**
     * When view detached from window , unregister adapter data observer, avoid momery leak.
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unregisterEmptyDataObserver();
    }

    public boolean getPullRefreshEnable() {
        return pullRefreshEnable;
    }

    public void setPullRefreshEnable(boolean enable) {
        pullRefreshEnable = enable;
        setSwipeRefreshEnable(enable);
    }

    public boolean getSwipeRefreshEnable() {
        return mSwipeRefreshLayout.isEnabled();
    }

    public void setSwipeRefreshEnable(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public void setColorSchemeResources(int... colorResIds) {
        mSwipeRefreshLayout.setColorSchemeResources(colorResIds);

    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void setRefreshing(final boolean isRefreshing) {
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                if (pullRefreshEnable)
                    mSwipeRefreshLayout.setRefreshing(isRefreshing);
            }
        });

    }

    public boolean getPushRefreshEnable() {
        return pushRefreshEnable;
    }

    public void setPushRefreshEnable(boolean pushRefreshEnable) {
        this.pushRefreshEnable = pushRefreshEnable;
    }

    public LinearLayout getFooterViewLayout() {
        return loadMoreLayout;
    }

    public void setFooterViewBackgroundColor(int color) {
        loadMoreLayout.setBackgroundColor(ContextCompat.getColor(mContext, color));
    }

    public void setFooterViewText(CharSequence text) {
        loadMoreText.setText(text);
    }

    public void setFooterViewText(int resid) {
        loadMoreText.setText(resid);
    }

    public void setFooterViewTextColor(int color) {
        loadMoreText.setTextColor(ContextCompat.getColor(mContext, color));
    }

    public void refresh() {
        if (mPullLoadMoreListener != null) {
            mPullLoadMoreListener.onRefresh();
        }
    }

    public void loadMore() {
        if (mPullLoadMoreListener != null && hasMore) {
            mFooterView.animate()
                    .translationY(0)
                    .setDuration(300)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            mFooterView.setVisibility(View.VISIBLE);
                        }
                    })
                    .start();
            invalidate();
            mPullLoadMoreListener.onLoadMore();
        }
    }

    public void setPullLoadMoreCompleted() {
        isRefresh = false;
        setRefreshing(false);

        isLoadMore = false;
        mFooterView.animate()
                .translationY(mFooterView.getHeight())
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

    }

    public void setOnPullLoadMoreListener(PullLoadMoreListener listener) {
        mPullLoadMoreListener = listener;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setIsLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setIsRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public interface PullLoadMoreListener {
        void onRefresh();

        void onLoadMore();
    }

    /**
     * This Observer receives adapter data change.
     * When adapter's item count greater than 0 and empty view has been set,then show the empty view.
     * when adapter's item count is 0 ,then empty view hide.
     */
    private class AdapterDataObserver extends android.support.v7.widget.RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            showEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            showEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            showEmptyView();
        }
    }

    /**
     * Solve IndexOutOfBoundsException exception
     */
    public class onTouchRecyclerView implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
                if (isRefresh != mSwipeRefreshLayout.isRefreshing()) {// 快速上下滑动导致不同步问题修正
                    setRefreshing(isRefresh);
                }
            }
            return isRefresh || isLoadMore;
        }
    }
}
