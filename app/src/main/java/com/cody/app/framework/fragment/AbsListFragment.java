package com.cody.app.framework.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cody.app.R;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.handler.framework.presenter.AbsListPresenter;
import com.cody.handler.framework.viewmodel.ListViewModel;
import com.cody.handler.framework.viewmodel.ViewModel;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * Created by haiyan.chen on 2016/12/23.
 * 下拉刷新和上拉加载的Activity基类，包含头部
 */

public abstract class AbsListFragment<P extends AbsListPresenter<AbsListViewModel, ItemViewModel>,
        AbsListViewModel extends ListViewModel<ItemViewModel>,
        ItemViewModel extends ViewModel,
        B extends ViewDataBinding>
        extends BaseLazyFragment<P, AbsListViewModel, B>
        implements BaseRecycleViewAdapter.OnItemClickListener, PullLoadMoreRecyclerView.PullLoadMoreListener {

    protected PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    protected BaseRecycleViewAdapter<ItemViewModel> mRecyclerViewAdapter;

    protected abstract BaseRecycleViewAdapter<ItemViewModel> buildRecycleViewAdapter();

    /**
     * 通过binding 返回 pullLoadMoreRecyclerView
     * getBinding().pullLoadMoreRecyclerView;
     */
    protected abstract PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView();

    /**
     * 定制empty view
     */
    protected abstract int getEmptyViewId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onFirstUserVisible() {
        super.onFirstUserVisible();
        getPresenter().getInitPage(TAG);
    }

    @Override
    public void onRefresh() {
        getPresenter().getRefreshPage(TAG);
    }

    @Override
    public void onLoadMore() {
        getPresenter().getNextPage(TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (mRecyclerViewAdapter == null) {
            mRecyclerViewAdapter = buildRecycleViewAdapter();
            mRecyclerViewAdapter.setItemClickListener(this);
            mRecyclerViewAdapter.setItemLongClickListener(this);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPullLoadMoreRecyclerView == null) {
//            mRecyclerViewAdapter = getAdapter();
            mPullLoadMoreRecyclerView = buildPullLoadMoreRecyclerView();
            //设置是否可以下拉刷新
            mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
            //设置是否可以上拉刷新
            mPullLoadMoreRecyclerView.setPushRefreshEnable(true);
            //设置下拉刷新是否可见 : 显示下拉刷新
            mPullLoadMoreRecyclerView.setRefreshing(false);
            //设置加载更多背景色
            mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.background_gray);
            //设置线性布局
            mPullLoadMoreRecyclerView.setLinearLayout();
            //设置事件监听
            mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
            //设置分割线
//            mPullLoadMoreRecyclerView.addItemDecoration(new RecycleDividerItemDecoration(getActivity(), R.drawable
//                    .divider));
            //设置无数据的视图
            mPullLoadMoreRecyclerView.setEmptyView(LayoutInflater.from(getActivity()).inflate(getEmptyViewId(),
                    null));
            //设置适配器
            mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
        }
    }

    @Override
    protected void onUserVisible() {
        super.onUserVisible();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPullLoadMoreRecyclerView != null) {
            mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
            getPresenter().cancel(TAG);
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (mPullLoadMoreRecyclerView != null) {
            mPullLoadMoreRecyclerView.setHasMore(getViewModel().getHasMore());
            mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        }
    }

    /**
     * 滑动到顶部
     */
    public void scrollToTop() {
        if (mPullLoadMoreRecyclerView != null) {
            mPullLoadMoreRecyclerView.smoothScrollToTop();
        }
    }
}
