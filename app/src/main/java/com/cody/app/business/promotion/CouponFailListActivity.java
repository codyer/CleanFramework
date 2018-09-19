package com.cody.app.business.promotion;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cody.app.R;
import com.cody.app.framework.activity.ListWithHeaderActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.handler.business.presenter.CouponFailListPresenter;
import com.cody.handler.business.viewmodel.ItemCouponFailViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;

/**
 * Created by liuliwei on 2018-09-03.
 * 发劵失败列表
 */

public class CouponFailListActivity extends ListWithHeaderActivity<CouponFailListPresenter,ItemCouponFailViewModel> {
    public final static String VERSION="version";
    public final static String SEND_TIME="send_time";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {

    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle(getIntent().getStringExtra(SEND_TIME));
        header.setLeft(true);
        header.setRight(false);
    }

    @Override
    protected CouponFailListPresenter buildPresenter() {
        return new CouponFailListPresenter(getIntent().getStringExtra(VERSION), Repository.getLocalValue(LocalKey.SHOP_ID));
    }

    @Override
    protected BaseRecycleViewAdapter<ItemCouponFailViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<ItemCouponFailViewModel>(getViewModel()) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_fail_coupon_list;
            }
        };
    }
}
