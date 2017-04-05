package com.cody.app.framework.activity;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.cody.app.R;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.handler.framework.presenter.AbstractRecycleViewPresenter;
import com.cody.handler.framework.viewmodel.BaseViewModel;
import com.cody.handler.framework.viewmodel.common.ListViewModel;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * Created by haiyan.chen on 2016/12/23.
 * 下拉刷新和上拉加载的Activity基类，包含头部
 */

public abstract class AbstractRecycleViewWithHeaderActivity
        <P extends AbstractRecycleViewPresenter<AbstractListViewModel, ItemViewModel>,
                ItemViewModel extends BaseViewModel,
                AbstractListViewModel extends ListViewModel<ItemViewModel>,
                B extends ViewDataBinding> extends WithHeaderActivity<P,
        AbstractListViewModel, B> implements BaseRecycleViewAdapter.OnItemClickListener, PullLoadMoreRecyclerView
        .PullLoadMoreListener {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerViewAdapter = buildRecycleViewAdapter();

        mRecyclerViewAdapter.setItemClickListener(this);
        //获取数据
        initRecycleView();
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

    private void initRecycleView() {
        if (mPullLoadMoreRecyclerView == null) {
//            mRecyclerViewAdapter = getAdapter();
            mPullLoadMoreRecyclerView = buildPullLoadMoreRecyclerView();
            //设置是否可以下拉刷新
            mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
            //设置是否可以上拉刷新
            mPullLoadMoreRecyclerView.setPushRefreshEnable(true);
            //设置下拉刷新是否可见 : 显示下拉刷新
            mPullLoadMoreRecyclerView.setRefreshing(false);
            //设置上拉刷新文字
            mPullLoadMoreRecyclerView.setFooterViewText(getString(R.string.load_more_text));
            //设置上拉刷新文字颜色
            mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
            //设置加载更多背景色
            mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.background_gray);
            //设置线性布局
            mPullLoadMoreRecyclerView.setLinearLayout();
            //设置事件监听
            mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
            //设置分割线
//            mPullLoadMoreRecyclerView.addItemDecoration(new RecycleDividerItemDecoration
// (RecycleViewLoadMoreWithHeaderActivity
//                    .this, R.drawable.divider));
            //设置无数据的视图
            mPullLoadMoreRecyclerView.setEmptyView(getLayoutInflater().inflate(getEmptyViewId(), null));
            //设置适配器
            mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
        }
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headerText:
                scrollToTop();
                break;
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

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (mPullLoadMoreRecyclerView != null) {
            mPullLoadMoreRecyclerView.setHasMore(getViewModel().getHasMore());
            mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        }
    }
}