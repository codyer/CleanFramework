package com.cody.handler.business.presenter;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.cody.handler.business.mapper.CouponListMapper;
import com.cody.handler.business.viewmodel.ItemCouponViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.ListPresenter;
import com.cody.repository.business.bean.CouponListBean;
import com.cody.repository.business.interaction.CouponInteraction;
import com.cody.repository.framework.Repository;

import java.util.Map;

/**
 * Create by jiquan.zhong  on 2018/8/15.
 * description:优惠券列表
 */
public class CouponListPresenter extends ListPresenter<ItemCouponViewModel> {
    private CouponInteraction mInteraction;
    private CouponListMapper mListMapper;
    private int mCouponStatus;

    public CouponListPresenter(int couponStatus) {
        mInteraction = Repository.getInteraction(CouponInteraction.class);
        mListMapper = new CouponListMapper();
        mCouponStatus = couponStatus;
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pageSize", getViewModel().getPageSize() + "");
        jsonObject.addProperty("pageNo", getViewModel().getPageNO() + "");
        jsonObject.addProperty("shopAppCouponStatus", mCouponStatus);
        mInteraction.getCouponList(tag, jsonObject, CouponListBean.class, new DefaultCallback<CouponListBean>(this) {
            @Override
            public void onBegin(Object tag) {
            }

            @Override
            public void onSuccess(CouponListBean bean) {
                super.onSuccess(bean);
                if (null != bean) {
                    boolean hasNextPage = bean.getPageNo() < bean.getTotalPages();
                    mListMapper.mapperList(getViewModel(), bean.getRecords(), getViewModel().getPosition(), hasNextPage);
                }
                refreshUI();
            }
        });
    }
}
