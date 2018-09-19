package com.cody.handler.business.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cody.handler.business.mapper.CustomerOrderListMapper;
import com.cody.handler.business.viewmodel.ItemCustomerOrderListViewModel;
import com.cody.handler.business.viewmodel.CustomerOrderListViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.AbsListPresenter;
import com.cody.repository.business.bean.order.CustomerOrderListBean;
import com.cody.repository.business.interaction.order.CustomerOrderInteraction;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by cody.yi on 2018/7/30.
 * TA的订单 C端用户的订单列表
 */
public class CustomerOrderListPresenter extends AbsListPresenter<CustomerOrderListViewModel, ItemCustomerOrderListViewModel> {
    private CustomerOrderInteraction mInteraction;
    private CustomerOrderListMapper mOrderListMapper;
    private String mUserImId;

    public CustomerOrderListPresenter(String userImId) {
        mUserImId = userImId;
        mInteraction = Repository.getInteraction(CustomerOrderInteraction.class);
        mOrderListMapper = new CustomerOrderListMapper();
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        if (TextUtils.isEmpty(mUserImId) || mUserImId.length() < 2) {
            refreshUI();
            return;
        }
        params.clear();
        params.put("merchantId", Repository.getLocalValue(LocalKey.SHOP_ID));
        params.put("purchaserId", mUserImId.substring(2));
        params.put("showCount", getViewModel().getPageSize() + "");
        params.put("currentPage", getViewModel().getPageNO() + "");
        mInteraction.getOrderListAll(tag, params, CustomerOrderListBean.class, new DefaultCallback<CustomerOrderListBean>(this) {
            @Override
            public void onBegin(Object tag) {
            }

            @Override
            public void onSuccess(CustomerOrderListBean bean) {
                super.onSuccess(bean);
                if (bean != null && bean.getData() != null) {
                    List<CustomerOrderListBean.OrderBean> list = bean.getData();
                    if (list != null && list.size() > 0) {
                        CustomerOrderListBean.OrderBean orderBean = list.get(0);
                        String province = orderBean.getProvinceName() != null ? orderBean.getProvinceName() :
                                "";
                        String city = orderBean.getCityName() != null ? orderBean.getCityName() : "";
                        String distribute = orderBean.getDistrictName() != null ? orderBean.getDistrictName()
                                : "";
                        String address = orderBean.getAddress() != null ? orderBean.getAddress() : "";
                        String myAddress = province + city + distribute + address;
                        String name = !TextUtils.isEmpty(orderBean.getPurchaserName()) ? orderBean.getPurchaserName() : "暂无";
                        String telephone = !TextUtils.isEmpty(orderBean.getMobile()) ? orderBean.getMobile() : "暂无";
                        String finalAddress = !TextUtils.isEmpty(myAddress) ? myAddress : "暂无";
                        getViewModel().setName(name);
                        getViewModel().setPhoneNumber(telephone);
                        getViewModel().setAddress(finalAddress);
                        mOrderListMapper.mapperList(getViewModel(), list, getViewModel().getPosition(), bean.isHasNextPage());
                    }else {
                        getViewModel().setName("该客户尚未在本店下过订单");
                        getViewModel().setPhoneNumber("------");
                        getViewModel().setAddress("------");
                    }
                }
                refreshUI();
            }
        });
    }
}
