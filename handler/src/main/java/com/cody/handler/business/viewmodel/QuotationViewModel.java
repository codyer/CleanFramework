/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.handler.business.viewmodel;

import android.databinding.ObservableField;

import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

/**
 * Created by chy on 2016/9/5.
 * 报价单详情viewmodel
 */
public class QuotationViewModel extends WithHeaderViewModel {

    /**
     * offerId : 0
     * productName :
     * productPicUrl :
     * model :
     * standard :
     * salePrice :
     * offerPrice :
     * quantity : 0
     * memo :
     */

    private Long offerId;
    private ObservableField<String> productName = new ObservableField<>("");
    private ObservableField<String> productPicUrl = new ObservableField<>("");
    private ObservableField<String> model = new ObservableField<>("");
    private ObservableField<String> standard = new ObservableField<>("");
    private ObservableField<String> salePrice = new ObservableField<>("");
    private ObservableField<String> address = new ObservableField<>("");
    private ObservableField<String> market = new ObservableField<>("");
    private String pdtSku;
    private boolean mEditable;
    private int type;
    private boolean enterType;
    private ObservableField<String> offerPrice = new ObservableField<>("");
    private ObservableField<String> quantity = new ObservableField<>("1");
    private ObservableField<String> memo = new ObservableField<>("");

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ObservableField<String> getProductName() {
        return productName;
    }

    public ObservableField<String> getProductPicUrl() {
        return productPicUrl;
    }

    public ObservableField<String> getModel() {
        return model;
    }

    public ObservableField<String> getStandard() {
        return standard;
    }

    public ObservableField<String> getSalePrice() {
        return salePrice;
    }

    public ObservableField<String> getAddress() {
        return address;
    }

    public ObservableField<String> getMarket() {
        return market;
    }

    public interface Type {
        int IM_ENTER = 0x1;
        int QUOTATION_DETAIL = 0x2;
        int MANAGER_ENTER = 0x3;
    }

    public void setPdtSku(String pdtSku) {
        this.pdtSku = pdtSku;
    }

    public String getPdtSku() {
        return pdtSku;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }


    public void setProductName(String productName) {
        this.productName.set(productName);
    }


    public void setProductPicUrl(String productPicUrl) {
        this.productPicUrl.set(productPicUrl);
    }


    public void setModel(String model) {
        this.model.set(model);
    }


    public void setStandard(String standard) {
        this.standard.set(standard);
    }


    public void setSalePrice(String salePrice) {
        this.salePrice.set(salePrice);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }


    public void setMarket(String market) {
        this.market.set(market);
    }


    public void setOfferPrice(String offerPrice) {
        this.offerPrice.set(offerPrice);
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public ObservableField<String> getOfferPrice() {
        return offerPrice;
    }

    public ObservableField<String> getQuantity() {
        return quantity;
    }

    public void setMemo(String memo) {
        this.memo.set(memo);
    }

    public ObservableField<String> getMemo() {
        return memo;
    }

    public void setEditable(boolean editable) {
        mEditable = editable;
    }

    public boolean isEditable() {
        switch (type) {
            case Type.IM_ENTER:
            case Type.MANAGER_ENTER:
                return true;
            case Type.QUOTATION_DETAIL:
                return false;
        }
        return true;
    }

    public void setEnterType(boolean enterType) {
        this.enterType = enterType;
    }

    public boolean isEnterType() {
        switch (type) {
            case Type.MANAGER_ENTER:
                return true;
            case Type.IM_ENTER:
                return false;
        }
        return true;
    }
}
