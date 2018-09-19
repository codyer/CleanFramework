package com.cody.repository.business.bean.order;



/**
 * Created by liuliwei on 2018-07-12.
 * 订单列表
 */
public class BusinessOrderListBean {
/*
   purchaserName: "测试"
    mobile: "18797721523"
    serialNumber: "SO5513890000013681"
    payableAmount: 0.02
    paidAmount: 0.02
    originalPayableAmount: null
    createDate: "2018-07-04 14:21:03"
    orderCloseDate: 1530686163000
    orderType: 退单类型： 1 销售单 2 销售退单 5 补差退单 ,
    orderStatus: "PAID"
    orderStatusDesc: "已付款"
    */
    private String id;
    private String purchaserName;
    private String mobile;
    private String serialNumber;
    private String originalSerialNumber;
    private String payableAmount;
    private String paidAmount;
    private String originalPayableAmount;
    private String createDate;
    private long orderCloseDate;
    private int orderType;
    private String orderStatus;
    private String orderStatusDesc;
    private String lastPaymentDate;
    private int orderStatusValue;
    private int afterSaleStatus;

    public String getPurchaserName() {
        return purchaserName;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(String payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getOriginalPayableAmount() {
        return originalPayableAmount;
    }

    public void setOriginalPayableAmount(String originalPayableAmount) {
        this.originalPayableAmount = originalPayableAmount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public long getOrderCloseDate() {
        return orderCloseDate;
    }

    public void setOrderCloseDate(long orderCloseDate) {
        this.orderCloseDate = orderCloseDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusDesc() {
        return orderStatusDesc;
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setOriginalSerialNumber(String originalSerialNumber) {
        this.originalSerialNumber = originalSerialNumber;
    }

    public String getOriginalSerialNumber() {
        return originalSerialNumber;
    }

    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setOrderStatusValue(int orderStatusValue) {
        this.orderStatusValue = orderStatusValue;
    }

    public int getOrderStatusValue() {
        return orderStatusValue;
    }

    public void setAfterSaleStatus(int afterSaleStatus) {
        this.afterSaleStatus = afterSaleStatus;
    }

    public int getAfterSaleStatus() {
        return afterSaleStatus;
    }
}
