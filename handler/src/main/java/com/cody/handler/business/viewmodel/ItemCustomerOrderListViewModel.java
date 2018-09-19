package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Created by cody.yi on 2018/7/30.
 * TA的订单 item
 */
public class ItemCustomerOrderListViewModel extends XItemViewModel {
    private String mOrderNumber;//订单编号
    private String mOrderType;
    private String mGoodsName;//商品名称
    private String mCount;//数量
    private String mOrderAmount;//订单金额
    private String mPaidAmount;//已付金额
    private String mSeverMoney;//服务费
    private boolean mHasServerMoney;//是否有服务费

    public String getOrderNumber() {
        return mOrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        mOrderNumber = orderNumber;
    }

    public String getOrderType() {
        return mOrderType;
    }

    public void setOrderType(String orderType) {
        mOrderType = orderType;
    }

    public String getGoodsName() {
        return mGoodsName;
    }

    public void setGoodsName(String goodsName) {
        mGoodsName = goodsName;
    }

    public String getCount() {
        return mCount;
    }

    public void setCount(String count) {
        mCount = count;
    }

    public String getOrderAmount() {
        return mOrderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        mOrderAmount = orderAmount;
    }

    public String getPaidAmount() {
        return mPaidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        mPaidAmount = paidAmount;
    }

    public String getSeverMoney() {
        return mSeverMoney;
    }

    public void setSeverMoney(String severMoney) {
        mSeverMoney = severMoney;
    }

    public boolean isHasServerMoney() {
        return mHasServerMoney;
    }

    public void setHasServerMoney(boolean hasServerMoney) {
        mHasServerMoney = hasServerMoney;
    }
}
