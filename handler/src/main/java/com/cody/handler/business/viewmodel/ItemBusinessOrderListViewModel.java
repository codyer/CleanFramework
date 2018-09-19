package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Created by liuliwei on 2018-07-11.
 * 商户订单列表 item
 */
public class ItemBusinessOrderListViewModel extends XItemViewModel {
    private String userName;//客户姓名
    private String userMobile;
    private String serialNumber;
    private String originalSerialNumber;
    private String lastPaymentDate;
    private String orderId;//订单号
    private String goodsName;//商户名称
    private String Quantity;//商品数量
    private String orderStatusDesc;//订单支付状态
    private String payableAmount;//订单金额
    private String paidAmount;//已付金额
    private String createDate;
    private String closeDate;
    private boolean showPay;   //是否显示支付按钮
    private boolean showTime;  //是否显示关闭时间
    private boolean showAmount; //是否显示支付金额
    private boolean saleOrder;  //是否是销售单

    private String startNumber;
    private String endNumber;
    private String mobileStr;
    //"UNPAID", "USED", "SUCCESS", "UNDELIVERED", "DELIVERED", "PART_UNDELIVERED",
    // "CLOSED", "NOT_RECEIVING", "UN_REVIEWED", "AFTER_SALE", "REFOUNDED", "UNCHECKED",
    // "PART_PAID", "PAID", "LOCKED", "PART_REFOUNDED", "REFOUNDING", "CANCELED",
    // "UNREFOUNDED", "REFUND_SUCCESS", "REFUSED"
    private int orderType;//退单类型： 1 销售单 2 销售退单 5 补差退单 ,  2 或者 5跳 售后详情      1.跳订单详情
    private String orderStatus;

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

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

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public boolean isShowPay() {
        return showPay;
    }

    public void setShowPay(boolean showPay) {
        this.showPay = showPay;
    }

    public boolean isShowTime() {
        return showTime;
    }

    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }

    public String getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(String startNumber) {
        this.startNumber = startNumber;
    }

    public String getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(String endNumber) {
        this.endNumber = endNumber;
    }

    public void setMobileStr(String mobileStr) {
        this.mobileStr = mobileStr;
    }

    public String getMobileStr() {
        return mobileStr;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setShowAmount(boolean showAmount) {
        this.showAmount = showAmount;
    }

    public boolean isShowAmount() {
        return showAmount;
    }

    public boolean isSaleOrder() {
        return saleOrder;
    }

    public void setSaleOrder(boolean saleOrder) {
        this.saleOrder = saleOrder;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ItemBusinessOrderListViewModel that = (ItemBusinessOrderListViewModel) o;

        if (showPay != that.showPay) return false;
        if (showTime != that.showTime) return false;
        if (orderType != that.orderType) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null)
            return false;
        if (userMobile != null ? !userMobile.equals(that.userMobile) : that.userMobile != null)
            return false;
        if (serialNumber != null ? !serialNumber.equals(that.serialNumber) : that.serialNumber != null)
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
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null)
            return false;
        if (closeDate != null ? !closeDate.equals(that.closeDate) : that.closeDate != null)
            return false;
        if (startNumber != null ? !startNumber.equals(that.startNumber) : that.startNumber != null)
            return false;
        if (endNumber != null ? !endNumber.equals(that.endNumber) : that.endNumber != null)
            return false;
        if (mobileStr != null ? !mobileStr.equals(that.mobileStr) : that.mobileStr != null)
            return false;
        return orderStatus != null ? orderStatus.equals(that.orderStatus) : that.orderStatus == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userMobile != null ? userMobile.hashCode() : 0);
        result = 31 * result + (serialNumber != null ? serialNumber.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (goodsName != null ? goodsName.hashCode() : 0);
        result = 31 * result + (Quantity != null ? Quantity.hashCode() : 0);
        result = 31 * result + (orderStatusDesc != null ? orderStatusDesc.hashCode() : 0);
        result = 31 * result + (payableAmount != null ? payableAmount.hashCode() : 0);
        result = 31 * result + (paidAmount != null ? paidAmount.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (closeDate != null ? closeDate.hashCode() : 0);
        result = 31 * result + (showPay ? 1 : 0);
        result = 31 * result + (showTime ? 1 : 0);
        result = 31 * result + (startNumber != null ? startNumber.hashCode() : 0);
        result = 31 * result + (endNumber != null ? endNumber.hashCode() : 0);
        result = 31 * result + (mobileStr != null ? mobileStr.hashCode() : 0);
        result = 31 * result + orderType;
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        return result;
    }
}
