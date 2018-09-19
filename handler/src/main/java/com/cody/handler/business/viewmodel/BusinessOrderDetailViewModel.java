package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.ViewModel;
import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuliwei on 2018-07-13.
 * 商户订单详情
 */
public class BusinessOrderDetailViewModel extends WithHeaderViewModel {
    //订单信息
    private String orderStatusDesc; //订单状态
    private String serialNumber;    //订单编号
    private String createDate;      //
    private String payDate;
    private String channel;
    private String remainingDate;//剩余时间

    //收货人信息
    private String receiverMobile;
    private String username;
    private String receiverName;
    private String receiverAddress;

    //商品
    private List<GoodsViewModel> goodsList=new ArrayList<>();


    //物流信息
    private String deliveryDate;
    private String deliveryCompleteDate;
    private String freight;
    private String servicePrice;
    private String serviceRemark;
    private String deliveryMode;

    //促销信息
    private List<PromotionViewModel> promotionList=new ArrayList<>();

    //金额明细
    private String goodsAmount;
    private String promotionAmount;
    private String serviceAmount;
    private String freightAmount;
    private String discountAmount;
    private String receivableAmount;
    private String receivedAmount;
    private String payableAmount;


    //发票信息
    private String invoiceHead;
    private String invoiceNumber;
    private String invoiceRemark;

    private String orderRemark;


    //其它约定
    private String finalPayRemark;
    private String otherPrintAmount;


    public static class GoodsViewModel extends ViewModel {
        private String productName;
        private String attr;
        private String salePrice;
        private String unitPrice;
        private int num;
        private boolean isHotItem;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
        }

        public String getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(String salePrice) {
            this.salePrice = salePrice;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public boolean isHotItem() {
            return isHotItem;
        }

        public void setHotItem(boolean hotItem) {
            isHotItem = hotItem;
        }
    }


    public static class PromotionViewModel extends ViewModel{
        private String promotionName;
        private String promotionPrice;

        public String getPromotionName() {
            return promotionName;
        }

        public void setPromotionName(String promotionName) {
            this.promotionName = promotionName;
        }

        public String getPromotionPrice() {
            return promotionPrice;
        }

        public void setPromotionPrice(String promotionPrice) {
            this.promotionPrice = promotionPrice;
        }
    }












    public String getOrderStatusDesc() {
        return orderStatusDesc;
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRemainingDate() {
        return remainingDate;
    }

    public void setRemainingDate(String remainingDate) {
        this.remainingDate = remainingDate;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public List<GoodsViewModel> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsViewModel> goodsList) {
        this.goodsList = goodsList;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryCompleteDate() {
        return deliveryCompleteDate;
    }

    public void setDeliveryCompleteDate(String deliveryCompleteDate) {
        this.deliveryCompleteDate = deliveryCompleteDate;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceRemark() {
        return serviceRemark;
    }

    public void setServiceRemark(String serviceRemark) {
        this.serviceRemark = serviceRemark;
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public List<PromotionViewModel> getPromotionList() {
        return promotionList;
    }

    public void setPromotionList(List<PromotionViewModel> promotionList) {
        this.promotionList = promotionList;
    }

    public String getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(String goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(String promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public String getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(String serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public String getFreightAmount() {
        return freightAmount;
    }

    public void setFreightAmount(String freightAmount) {
        this.freightAmount = freightAmount;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getReceivableAmount() {
        return receivableAmount;
    }

    public void setReceivableAmount(String receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public String getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(String receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public String getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(String payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getInvoiceHead() {
        return invoiceHead;
    }

    public void setInvoiceHead(String invoiceHead) {
        this.invoiceHead = invoiceHead;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceRemark() {
        return invoiceRemark;
    }

    public void setInvoiceRemark(String invoiceRemark) {
        this.invoiceRemark = invoiceRemark;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
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
