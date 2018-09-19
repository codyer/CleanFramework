package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.ListWithHeaderViewModel;

/**
 * Created by cody.yi on 2018/7/30.
 * TA的订单
 */
public class CustomerOrderListViewModel extends ListWithHeaderViewModel<ItemCustomerOrderListViewModel> {
    private String mOpenId;
    private String mName;
    private String mPhoneNumber;
    private String mAddress;

    public String getOpenId() {
        return mOpenId;
    }

    public void setOpenId(String openId) {
        mOpenId = openId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }
}
