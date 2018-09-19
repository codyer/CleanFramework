package com.cody.handler.business.presenter;

import android.text.TextUtils;

import com.cody.handler.business.viewmodel.EventMarketViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.business.interaction.CouponInteraction;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.http.SimpleBean;

/**
 * Create by jiquan.zhong  on 2018/8/14.
 * description:活动营销
 */
public class EventMarketPresenter extends Presenter<EventMarketViewModel> {
    private CouponInteraction mInteraction;

    public EventMarketPresenter() {
        mInteraction = Repository.getInteraction(CouponInteraction.class);
    }

    public void couponHasNew(Object tag) {
        mInteraction.couponHasNew(tag, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onBegin(Object tag) {
            }

            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                if (null != bean && null != bean.getData()) {
                    String data = (String) bean.getData();
                    if (!TextUtils.isEmpty(data)) {
                        getViewModel().getHasNew().set(true);
                    } else {
                        getViewModel().getHasNew().set(false);
                    }
                } else {
                    getViewModel().getHasNew().set(false);
                }
                refreshUI();
            }
        });
    }

    public void couponAsOld(Object tag) {
        mInteraction.couponAsOld(tag, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onBegin(Object tag) {
            }

            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                getViewModel().getHasNew().set(false);
                refreshUI();
            }
        });
    }
}
