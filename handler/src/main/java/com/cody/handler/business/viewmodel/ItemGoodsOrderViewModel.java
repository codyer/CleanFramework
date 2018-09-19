/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.handler.business.viewmodel;


import android.databinding.ObservableField;

import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Created by cody.yi on 2016/8/10.
 * modification by lisongin 2016/9/10
 * 商品 item
 */
public class ItemGoodsOrderViewModel extends XItemViewModel {

    private String userName;//客户姓名
    private String userMobile;
    private String orderId;//订单号
    private String goodsName;//商户名称
    private String Quantity;//商品数量
    private String orderStatusDesc;//订单支付状态
    private String payableAmount;//订单金额
    private String paidAmount;//已付金额
    private ObservableField<String> keyWord = new ObservableField<>("");

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    public String getOrderStatusDesc() {
        return orderStatusDesc;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPayableAmount(String payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getPayableAmount() {
        return payableAmount;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getQuantity() {
        return Quantity;
    }

    public ObservableField<String> getKeyWord() {
        return keyWord;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserMobile() {
        return userMobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemGoodsOrderViewModel that = (ItemGoodsOrderViewModel) o;

        if (userName != null ? !userName.equals(that.userName) : that.userName != null)
            return false;
        if (userMobile != null ? !userMobile.equals(that.userMobile) : that.userMobile != null)
            return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (goodsName != null ? !goodsName.equals(that.goodsName) : that.goodsName != null)
            return false;
        if (Quantity != null ? !Quantity.equals(that.Quantity) : that.Quantity != null)
            return false;
        if (orderStatusDesc != null ? !orderStatusDesc.equals(that.orderStatusDesc) : that.orderStatusDesc != null)
            return false;
        if (payableAmount != null ? !payableAmount.equals(that.payableAmount) : that.payableAmount != null)
            return false;
        if (paidAmount != null ? !paidAmount.equals(that.paidAmount) : that.paidAmount != null)
            return false;
        return keyWord.get() != null ? keyWord.get().equals(that.keyWord.get()) : that.keyWord.get() == null;

    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (userMobile != null ? userMobile.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (goodsName != null ? goodsName.hashCode() : 0);
        result = 31 * result + (Quantity != null ? Quantity.hashCode() : 0);
        result = 31 * result + (orderStatusDesc != null ? orderStatusDesc.hashCode() : 0);
        result = 31 * result + (payableAmount != null ? payableAmount.hashCode() : 0);
        result = 31 * result + (paidAmount != null ? paidAmount.hashCode() : 0);
        result = 31 * result + (keyWord != null ? keyWord.hashCode() : 0);
        return result;
    }
}
