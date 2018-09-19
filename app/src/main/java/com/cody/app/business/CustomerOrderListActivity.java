package com.cody.app.business;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ActivityCustomerOrderListBinding;
import com.cody.app.framework.activity.AbsListActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.handler.business.presenter.CustomerOrderListPresenter;
import com.cody.handler.business.viewmodel.CustomerOrderListViewModel;
import com.cody.handler.business.viewmodel.ItemCustomerOrderListViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * TA的订单
 */
public class CustomerOrderListActivity extends AbsListActivity<CustomerOrderListPresenter, CustomerOrderListViewModel, ItemCustomerOrderListViewModel, ActivityCustomerOrderListBinding> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_customer_order_list;
    }

    @Override
    protected CustomerOrderListPresenter buildPresenter() {
        String userImId = getIntent().getStringExtra("user");
        return new CustomerOrderListPresenter(userImId);
    }

    @Override
    protected CustomerOrderListViewModel buildViewModel(Bundle savedInstanceState) {
        CustomerOrderListViewModel viewModel = new CustomerOrderListViewModel();
        HeaderViewModel headerViewModel = viewModel.getHeaderViewModel();
        headerViewModel.setTitle("TA的订单");
        headerViewModel.setLeft(true);
        return viewModel;
    }

    @Override
    protected BaseRecycleViewAdapter<ItemCustomerOrderListViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<ItemCustomerOrderListViewModel>(getViewModel()) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_customer_order_list;
            }
        };
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    @Override
    protected int getEmptyViewId() {
        return R.layout.empty_view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
        }
    }
}
