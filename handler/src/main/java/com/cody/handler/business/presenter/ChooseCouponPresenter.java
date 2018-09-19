/*
 * Copyright (c)  Created by Cody.yi on 2016/8/30.
 */

package com.cody.handler.business.presenter;


import android.support.annotation.NonNull;

import com.cody.repository.business.interaction.ChooseCouponInteraction;
import com.cody.handler.business.mapper.ChooseCouponListMapper;
import com.cody.handler.business.viewmodel.ItemCouponImViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.ListWithHeaderPresenter;
import com.cody.repository.business.bean.ChooseCouponListBean;
import com.cody.repository.business.bean.CouponNumBean;
import com.cody.repository.framework.Repository;

import java.util.Map;

/**
 * Created by chy on 2016/8/4.
 * 优惠券相关处理
 */
public class ChooseCouponPresenter extends ListWithHeaderPresenter<ItemCouponImViewModel> {

    private ChooseCouponInteraction mInteraction;
    private ChooseCouponListMapper mCouponListMapper = new ChooseCouponListMapper();

    public ChooseCouponPresenter() {
        mInteraction = Repository.getInteraction(ChooseCouponInteraction.class);
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        getCouponList(tag, params);
    }

    private void getCouponList(final Object tag, Map<String, String> params) {
        mInteraction.getCouponList(tag, params, ChooseCouponListBean.class, new DefaultCallback<ChooseCouponListBean>(this) {
            @Override
            public void onBegin(Object tag) {
            }

            @Override
            public void onSuccess(ChooseCouponListBean bean) {
                super.onSuccess(bean);
                if (getView() != null) {
                    if (bean == null) {
                        bean = new ChooseCouponListBean();
                    }
                    mCouponListMapper.mapperList(getViewModel(), bean.getList(), getViewModel().getPosition(), false);
                    getView().onUpdate(tag);
                }
            }
        });
    }

    public void tapReceiveCoupon(final Object tag, Map<String, String> params) {
        mInteraction.getReceiveCoupon(tag, params, CouponNumBean.class, new DefaultCallback<CouponNumBean>(this) {
            @Override
            public void onSuccess(CouponNumBean bean) {
                super.onSuccess(bean);
                if (getView() != null) {
                    getView().onUpdate(tag, bean);
                }
            }
        });
    }
}
