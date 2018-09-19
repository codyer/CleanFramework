package com.cody.handler.business.presenter.order;

import android.support.annotation.NonNull;

import com.cody.handler.business.mapper.BusinessOrderListMapper;
import com.cody.handler.business.viewmodel.ItemBusinessOrderListViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.ListPresenter;
import com.cody.repository.business.bean.order.BusinessOrderListBean;
import com.cody.repository.business.bean.order.OrderQrCodeBean;
import com.cody.repository.business.interaction.order.BusinessOrderInteraction;
import com.cody.repository.framework.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuliwei on 2018-07-11.
 * 商户订单列表
 */
public class BusinessOrderListPresenter extends ListPresenter<ItemBusinessOrderListViewModel> {

    private BusinessOrderListMapper mMapper;
    private String mOrderStatus;
    private BusinessOrderInteraction mInteraction;
    private String mShopId;
    private int mCouponId = -1;

    public BusinessOrderListPresenter(int couponId, String orderStatus, String shopId) {
        mMapper = new BusinessOrderListMapper();
        this.mCouponId = couponId;
        this.mOrderStatus = orderStatus;
        this.mShopId = shopId;
        mInteraction = Repository.getInteraction(BusinessOrderInteraction.class);
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        params.clear();//这是一个特立独行的接口！！！pageNO和pageSize取了别名
        params.put("currentPage", getViewModel().getPageNO() + "");
        params.put("showCount", getViewModel().getPageSize() + "");
        params.put("shopId", mShopId);
        if (mCouponId == -1) {
            getOrderList(tag, params);
        } else {
            getCouponOrderList(tag, params);
        }
    }

    // 获取使用优惠券的订单列表
    private void getCouponOrderList(Object tag, @NonNull Map<String, String> params) {
        params.put("promotionId", mCouponId + "");
        params.put("bigPromotionType", "3");
        mInteraction.getCouponOrderList(tag, params, BusinessOrderListBean.class, new DefaultCallback<List<BusinessOrderListBean>>(this) {
            @Override
            public void onBegin(Object tag) {
            }

            @Override
            public void onSuccess(List<BusinessOrderListBean> bean) {
                super.onSuccess(bean);
                if (bean != null) {
                    boolean isHasNextPage = bean.size() >= getViewModel().getPageSize();
                    mMapper.mapperList(getViewModel(), bean, getViewModel().getPosition(), isHasNextPage);
                }
                refreshUI();
            }
        });
    }

    //获取订单列表
    private void getOrderList(Object tag, @NonNull Map<String, String> params) {
        params.put("orderStatus", mOrderStatus);
        mInteraction.getOrderList(tag, params, BusinessOrderListBean.class, new DefaultCallback<List<BusinessOrderListBean>>(this) {
            @Override
            public void onBegin(Object tag) {
            }

            @Override
            public void onSuccess(List<BusinessOrderListBean> bean) {
                super.onSuccess(bean);
                if (bean != null) {
                    boolean isHasNextPage = bean.size() >= getViewModel().getPageSize();
                    mMapper.mapperList(getViewModel(), bean, getViewModel().getPosition(), isHasNextPage);
                }
                refreshUI();
            }
        });
    }


    public void getQrCode(Object tag, String serialNumber, int width, int height, final OrderQrCodeListener listener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("serialNumber", serialNumber);
        params.put("width", width + "");
        params.put("height", height + "");
        mInteraction.getQrCode(tag, params, OrderQrCodeBean.class, new DefaultCallback<OrderQrCodeBean>(this) {
            @Override
            public void onSuccess(OrderQrCodeBean bean) {
                super.onSuccess(bean);
                if (listener != null) {
                    listener.onQrCodeSuccess(bean);
                }
                hideLoading();
            }
        });
    }

    public interface OrderQrCodeListener {
        void onQrCodeSuccess(OrderQrCodeBean qrCodeBean);
    }

}
