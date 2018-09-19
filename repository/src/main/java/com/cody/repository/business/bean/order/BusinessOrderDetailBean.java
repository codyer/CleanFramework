package com.cody.repository.business.bean.order;


import java.util.ArrayList;
import java.util.List;

public class BusinessOrderDetailBean {
    private String id;
    private String serialNumber;
    private String systemTime;
    private String endTime;
    private String createDate;

    //顾客信息
    private String purchaserName;
    private String receiverName;
    private String receiverMobile;
    private String address;


    private String deliverDate;
    private String realDeliverDate;
    private String carriage;
    private String serviceAmount;
    private String serviceRemark;
    private String deliverTypeDesc;



    private String orderItemTotalAmount;
    private String promotionTotalAmount;
    private String exceptedChangeAmount;
    private String payableAmount;
    private String paidAmount;

    //发票
    private OrderAdditionBean orderAdditionVo;

    //商品
    private List<GoodsBean> orderItems=new ArrayList<>();

    //促销信息
    private List<PromotionBean> promotions=new ArrayList<>();

    public static class PromotionBean{
        private String promotionName;
        private String promotionDiscountAmount;

        public String getPromotionName() {
            return promotionName;
        }

        public String getPromotionDiscountAmount() {
            return promotionDiscountAmount;
        }
    }

    public static class GoodsBean{
        private String productName;
        private String color;
        private String sizeUnit;
        private boolean isHotItem;
        private String unitPrice;
        private String salePrice;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSizeUnit() {
            return sizeUnit;
        }

        public void setSizeUnit(String sizeUnit) {
            this.sizeUnit = sizeUnit;
        }

        public boolean isHotItem() {
            return isHotItem;
        }

        public void setHotItem(boolean hotItem) {
            isHotItem = hotItem;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(String salePrice) {
            this.salePrice = salePrice;
        }
    }



    //发票
    public static class OrderAdditionBean{
        private String invoiceHead;
        private String taxpayerNumber;
        private String finalPayRemark;
        private String otherPrintAmount;

        public String getInvoiceHead() {
            return invoiceHead;
        }

        public void setInvoiceHead(String invoiceHead) {
            this.invoiceHead = invoiceHead;
        }

        public String getTaxpayerNumber() {
            return taxpayerNumber;
        }

        public void setTaxpayerNumber(String taxpayerNumber) {
            this.taxpayerNumber = taxpayerNumber;
        }

        public String getFinalPayRemark() {
            return finalPayRemark;
        }

        public void setFinalPayRemark(String finalPayRemark) {
            this.finalPayRemark = finalPayRemark;
        }

        public String getOtherPrintAmount() {
            return otherPrintAmount;
        }

        public void setOtherPrintAmount(String otherPrintAmount) {
            this.otherPrintAmount = otherPrintAmount;
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getRealDeliverDate() {
        return realDeliverDate;
    }

    public void setRealDeliverDate(String realDeliverDate) {
        this.realDeliverDate = realDeliverDate;
    }

    public String getCarriage() {
        return carriage;
    }

    public void setCarriage(String carriage) {
        this.carriage = carriage;
    }

    public String getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(String serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public String getServiceRemark() {
        return serviceRemark;
    }

    public void setServiceRemark(String serviceRemark) {
        this.serviceRemark = serviceRemark;
    }

    public String getDeliverTypeDesc() {
        return deliverTypeDesc;
    }

    public void setDeliverTypeDesc(String deliverTypeDesc) {
        this.deliverTypeDesc = deliverTypeDesc;
    }

    public String getOrderItemTotalAmount() {
        return orderItemTotalAmount;
    }

    public void setOrderItemTotalAmount(String orderItemTotalAmount) {
        this.orderItemTotalAmount = orderItemTotalAmount;
    }

    public String getPromotionTotalAmount() {
        return promotionTotalAmount;
    }

    public void setPromotionTotalAmount(String promotionTotalAmount) {
        this.promotionTotalAmount = promotionTotalAmount;
    }

    public String getExceptedChangeAmount() {
        return exceptedChangeAmount;
    }

    public void setExceptedChangeAmount(String exceptedChangeAmount) {
        this.exceptedChangeAmount = exceptedChangeAmount;
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

    public OrderAdditionBean getOrderAdditionVo() {
        return orderAdditionVo;
    }

    public void setOrderAdditionVo(OrderAdditionBean orderAdditionVo) {
        this.orderAdditionVo = orderAdditionVo;
    }

    public List<GoodsBean> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<GoodsBean> orderItems) {
        this.orderItems = orderItems;
    }

    public List<PromotionBean> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<PromotionBean> promotions) {
        this.promotions = promotions;
    }
}