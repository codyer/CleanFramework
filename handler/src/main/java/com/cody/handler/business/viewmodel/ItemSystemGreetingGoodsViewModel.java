package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * 系统欢迎词设置页 商品item
 */
public class ItemSystemGreetingGoodsViewModel extends XItemViewModel {
    private String mGoodsId;
    private String mGoodsName;
    private String mGoodsPrice;
    private String mGoodsImageUrl;

    public String getGoodsId() {
        return mGoodsId;
    }

    public void setGoodsId(String goodsId) {
        mGoodsId = goodsId;
    }

    public String getGoodsName() {
        return mGoodsName;
    }

    public void setGoodsName(String goodsName) {
        mGoodsName = goodsName;
    }

    public String getGoodsPriceStr() {
        return "￥" + mGoodsPrice;
    }

    public String getGoodsPrice() {
        return mGoodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        mGoodsPrice = goodsPrice;
    }

    public String getGoodsImageUrl() {
        return mGoodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        mGoodsImageUrl = goodsImageUrl;
    }
}
