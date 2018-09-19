package com.cody.handler.business.presenter;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.cody.handler.business.mapper.SelectCouponListMapper;
import com.cody.handler.business.viewmodel.CouponViewModel;
import com.cody.handler.business.viewmodel.CouponsListViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.AbsListPresenter;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.repository.business.bean.CouponStoreListBean;
import com.cody.repository.business.interaction.CouponInteraction;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.http.SimpleBean;

import java.util.Map;

/**
 * Created by dong.wang
 * Date: 2018/8/18
 * Time: 15:27
 * Description: 选择优惠券
 */
public class SelectCouponListPresenter extends AbsListPresenter<CouponsListViewModel, CouponViewModel> {

    private CouponInteraction mCouponInteraction;
    private SelectCouponListMapper mSelectCouponListMapper;
    private int userSum;//发券的用户数量

    public SelectCouponListPresenter(int userSum) {
        mCouponInteraction = Repository.getInteraction(CouponInteraction.class);
        mSelectCouponListMapper = new SelectCouponListMapper();
        this.userSum = userSum;
    }

    @Override
    public void getRecycleList(Object tag, @NonNull final Map<String, String> params) {
        params.put("pageSize", "100");
        params.put("channelId", "1");
        mCouponInteraction.getCuponByStore(tag, params, CouponStoreListBean.class, new DefaultCallback<CouponStoreListBean>(this) {
            @Override
            public void onBegin(Object tag) {
//                super.onBegin(tag);
            }

            @Override
            public void onSuccess(CouponStoreListBean bean) {
                super.onSuccess(bean);
                if (bean != null) {
                    mSelectCouponListMapper.setUserSum(userSum);
                    mSelectCouponListMapper.mapperList(getViewModel(), bean.getRecords(), getViewModel().getPosition(), false);
                }
                hideLoading();
            }

            @Override
            public void onFailure(SimpleBean simpleBean) {
                super.onFailure(simpleBean);
            }

            @Override
            public void onError(SimpleBean simpleBean) {
                super.onError(simpleBean);
            }
        });
    }

    public void usersTakeCoupon(Object tag, JsonObject params, final OnActionListener listener) {
        mCouponInteraction.usersTakeCoupon(tag, params, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onBegin(Object tag) {
//                super.onBegin(tag);
            }

            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                listener.onSuccess();
            }

            @Override
            public void onFailure(SimpleBean simpleBean) {
                super.onFailure(simpleBean);
            }

            @Override
            public void onError(SimpleBean simpleBean) {
                super.onError(simpleBean);
            }
        });
    }

    public void batchUsersTakeCoupons(Object tag, JsonObject params, final OnActionListener listener) {
        mCouponInteraction.batchUsersTakeCoupons(tag, params, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onBegin(Object tag) {
//                super.onBegin(tag);
            }

            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                listener.onSuccess();
            }

            @Override
            public void onFailure(SimpleBean simpleBean) {
                super.onFailure(simpleBean);
            }

            @Override
            public void onError(SimpleBean simpleBean) {
                super.onError(simpleBean);
            }
        });
    }
}
