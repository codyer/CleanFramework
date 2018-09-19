package com.cody.app.business.order;

import android.support.v4.app.Fragment;
import android.view.View;

import com.cody.app.R;
import com.cody.app.framework.activity.TabWithHeaderActivity;
import com.cody.handler.framework.presenter.DefaultWithHeaderPresenter;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.xf.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单列表
 */

public class BusinessOrderListActivity extends TabWithHeaderActivity<DefaultWithHeaderPresenter> {
    //全部
    public static final String ALL = "st_0";
    //待付款
    public static final String UNPAID = "st_1";
    //已付款
    public static final String PAID = "st_2";
    //交易成功
    public static final String SUCCESS = "st_3";
    //售后
    public static final String AFTER_SALE = "st_4";

    String[] mStatus = {ALL, UNPAID, PAID, SUCCESS, AFTER_SALE};

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setLeft(true);
        header.setTitle("订单列表");
        header.setRightIsText(false);
        header.setRight(true);
        header.setLineVisible(false);
        header.setRightResId(R.drawable.xf_ic_search_black);
    }

    @Override
    protected int getChildTabTitles() {
        return R.array.business_order_list_tab;
    }

    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        for (String tabs : mStatus) {
            fragments.add(BusinessOrderListFragment.newInstance(tabs));
        }
        return fragments;
    }

    @Override
    protected DefaultWithHeaderPresenter buildPresenter() {
        return new DefaultWithHeaderPresenter();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.headerRightBtn:
                ActivityUtil.navigateTo(SearchBusinessOrderActivity.class);
                break;
            case R.id.headerLeftBtn:
                finish();
                break;
        }
    }
}
