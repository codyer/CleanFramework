package com.cody.repository.business.bean.im;

/**
 * Created by chen.huarong on 2018-07-30.
 */

public class IMOrderBean {
    private String shopId;       //店铺id
    private int orderId;              //订单id
    private String serialNumber; //订单编号
    private String payableAmount; //订单金额
    private String orderStatus; //订单状态
    private String imgUrl;        //商品图片
    private String createDate;  //创建时间
    private int extendType;     //订单类型
    private boolean stage;      //是否是阶段订单

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getExtendType() {
        return extendType;
    }

    public void setExtendType(int extendType) {
        this.extendType = extendType;
    }

    public boolean isStage() {
        return stage;
    }

    public void setStage(boolean stage) {
        this.stage = stage;
    }
}
