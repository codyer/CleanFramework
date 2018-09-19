/*
 * Copyright (c)  Created by Cody.yi on 2016/9/18.
 */

package com.cody.repository.business.bean;

/**
 * Created by chy on 2016/9/18.
 * 导购员 宣传手册bean类
 */
public class GuideManualBean {

    /**
     * shoppingGuide :
     * shopId : 0
     * marketName :
     * marketAddress :
     * shopName :
     * guideId : 0
     */

    private String shoppingGuide;
    private int shopId;
    private String marketName;
    private String marketAddress;
    private String shopName;
    private int guideId;

    public String getShoppingGuide() {
        return shoppingGuide;
    }

    public void setShoppingGuide(String shoppingGuide) {
        this.shoppingGuide = shoppingGuide;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketAddress() {
        return marketAddress;
    }

    public void setMarketAddress(String marketAddress) {
        this.marketAddress = marketAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getGuideId() {
        return guideId;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    }
}
