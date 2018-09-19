package com.cody.app.business.customer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ActivityMyCustomerBinding;
import com.cody.app.framework.activity.BaseBindingActivity;
import com.cody.handler.business.presenter.MyCustomerPresenter;
import com.cody.handler.business.viewmodel.MyCustomerViewModel;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.cody.xf.utils.SystemBarUtil;

/**
 * 我的客户
 */
public class MyCustomerActivity extends BaseBindingActivity<MyCustomerPresenter, MyCustomerViewModel,
        ActivityMyCustomerBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBinding().swipeRefresh.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color
                        .holo_blue_dark,
                android.R.color.holo_orange_dark);

        getBinding().swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getCustomerInfo(TAG);
            }
        });

        getBinding().swipeRefresh.setRefreshing(true);
        getPresenter().getCustomerInfo(TAG);
    }

    @Override
    protected void onImmersiveReady() {
        SystemBarUtil.setStatusBarDarkMode(this, false);
        SystemBarUtil.tintStatusBar(this, Color.TRANSPARENT);
        SystemBarUtil.immersiveStatusBar(this, 0.0f);
        SystemBarUtil.setPadding(this, getBinding().backBtn);
        SystemBarUtil.setPadding(this, getBinding().title);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_my_customer;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        getBinding().swipeRefresh.setRefreshing(false);
    }

    @Override
    protected MyCustomerPresenter buildPresenter() {
        return new MyCustomerPresenter();
    }

    @Override
    protected MyCustomerViewModel buildViewModel(Bundle savedInstanceState) {
        return new MyCustomerViewModel();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                finish();
                break;
            case R.id.potentialCustomer:
                //埋点 B端龙果APP我的客户_潜在客户
                BuryingPointUtils.build(MyCustomerActivity.class, 4230).submitF();
                PotentialCustomerActivity.startLatentCustomer();
                break;
            case R.id.intentCustomer:
                //埋点 B端龙果APP我的客户_意向客户
                BuryingPointUtils.build(MyCustomerActivity.class, 4231).submitF();
                IntentionCustomerActivity.startIntentionCustomer();
                break;
            case R.id.bargainCustomer:
                //埋点 B端龙果APP我的客户_成交客户
                BuryingPointUtils.build(MyCustomerActivity.class, 4232).submitF();
                BargainCustomerActivity.startBargainCustomer();
                break;
        }
    }
}
