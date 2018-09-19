package com.cody.app.business.promotion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cody.app.R;
import com.cody.app.framework.activity.TabWithHeaderActivity;
import com.cody.handler.framework.presenter.DefaultWithHeaderPresenter;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.framework.statistics.BuryingPointUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by jiquan.zhong  on 2018/8/14.
 * description:优惠券列表页
 */
public class CouponListActivity extends TabWithHeaderActivity<DefaultWithHeaderPresenter> {
    //全部
    public static final int COUPON_ALL = 0;
    //待报名
    public static final int COUPON_WAIT_REGISTER = 1;
    //待审核
    public static final int COUPON_WAIT_CONFIRM = 2;
    //进行中
    public static final int COUPON_GOING_ON = 3;
    //已过期
    public static final int COUPON_EXPIRED = 4;

    private int[] mStatus = {COUPON_ALL, COUPON_WAIT_REGISTER, COUPON_WAIT_CONFIRM, COUPON_GOING_ON, COUPON_EXPIRED};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBinding().tabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String[] titles = getResources().getStringArray(R.array.coupon_list_tab);
                if (titles != null && titles.length > position) {
                    BuryingPointUtils.build(CouponListActivity.class, 4276).addTag(titles[position]).submitF();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setLeft(true);
        header.setTitle(getString(R.string.coupon_list));
        header.setLineVisible(false);
    }

    @Override
    protected int getChildTabTitles() {
        return R.array.coupon_list_tab;
    }

    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        for (int tabs : mStatus) {
            fragments.add(CouponListFragment.newInstance(tabs));
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
            case R.id.headerLeftBtn:
                finish();
                break;
        }
    }
}
