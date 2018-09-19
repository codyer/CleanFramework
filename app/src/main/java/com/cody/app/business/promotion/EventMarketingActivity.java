package com.cody.app.business.promotion;

import android.os.Bundle;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ActivityEventMarketingBinding;
import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.handler.business.presenter.EventMarketPresenter;
import com.cody.handler.business.viewmodel.EventMarketViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.cody.xf.utils.ActivityUtil;

/**
 * Create by jiquan.zhong  on 2018/8/14.
 * description:活动营销
 */
public class EventMarketingActivity extends WithHeaderActivity<EventMarketPresenter, EventMarketViewModel, ActivityEventMarketingBinding> {
    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle(getString(R.string.activity_promotion));
        header.setVisible(true);
        header.setLeft(true);
        header.setRight(false);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_event_marketing;
    }

    @Override
    protected EventMarketPresenter buildPresenter() {
        return new EventMarketPresenter();
    }

    @Override
    protected EventMarketViewModel buildViewModel(Bundle savedInstanceState) {
        return new EventMarketViewModel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().couponHasNew(TAG);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tvActivity:
                BuryingPointUtils.build(EventMarketingActivity.class,4267).submitF();
                ActivityUtil.navigateTo(PromotionListActivity.class);
                break;
            case R.id.tvCoupon:
                BuryingPointUtils.build(EventMarketingActivity.class,4268).submitF();
                if (getViewModel().getHasNew().get()) {
                    getPresenter().couponAsOld(TAG);
                }
                ActivityUtil.navigateTo(CouponListActivity.class);
                break;
        }
    }
}
