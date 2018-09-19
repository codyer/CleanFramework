package com.cody.repository.business.bean.im;

import java.io.Serializable;

/**
 * Created by  qiaoping.xiao on 2017/10/11.
 */

public class SysGreetingWordsGoodsBean implements Serializable {

    String imageUrl;               ///< 商品的图片URL
    String merchandiseName;        ///< 商品的名称
    String merchandisePrice;           ///< 商品的价格
    String merchandiseId;          ///< 商品的ID
    String merchandiseSku;          ///< 商品的sku

    public SysGreetingWordsGoodsBean() {
    }

    public SysGreetingWordsGoodsBean(String imageUrl, String merchandiseName, String merchandisePrice, String merchandiseID,String merchandiseSku) {
        this.imageUrl = imageUrl;
        this.merchandiseName = merchandiseName;
        this.merchandisePrice = merchandisePrice;
        this.merchandiseId = merchandiseID;
        this.merchandiseSku = merchandiseSku;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMerchandiseName() {
        return merchandiseName;
    }

    public void setMerchandiseName(String merchandiseName) {
        this.merchandiseName = merchandiseName;
    }

    public String getMerchandisePrice() {
        return merchandisePrice;
    }

    public void setMerchandisePrice(String merchandisePrice) {
        this.merchandisePrice = merchandisePrice;
    }

    public String getMerchandiseId() {
        return merchandiseId;
    }

    public void setMerchandiseId(String merchandiseID) {
        this.merchandiseId = merchandiseID;
    }

    public String getMerchandiseSku() {
        return merchandiseSku;
    }

    public void setMerchandiseSku(String merchandiseSku) {
        this.merchandiseSku = merchandiseSku;
    }
}
