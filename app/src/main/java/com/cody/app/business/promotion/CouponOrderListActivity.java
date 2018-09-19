package com.cody.app.business.promotion;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cody.app.business.order.BusinessOrderListFragment;
import com.cody.app.framework.activity.FragmentContainerActivity;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.xf.utils.ActivityUtil;

public class CouponOrderListActivity extends FragmentContainerActivity {
    private final static String TITLE = "title";
    private final static String COUPON_ID = "couponId";

    public static void startCouponOrderList(String title, int couponId) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putInt(COUPON_ID, couponId);
        ActivityUtil.navigateTo(CouponOrderListActivity.class, bundle);
    }

    @Override
    public Fragment buildFragment() {
        int couponId = getIntent().getIntExtra(COUPON_ID, -1);
        if (couponId == -1) return null;
        return BusinessOrderListFragment.newInstance(couponId);
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setLeft(true);
        header.setTitle(getIntent().getStringExtra(TITLE));
    }
}
