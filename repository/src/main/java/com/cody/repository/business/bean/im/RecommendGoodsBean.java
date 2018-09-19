package com.cody.repository.business.bean.im;

/**
 * Created by  chenhuarong on 2018/5/7.
 */

public class RecommendGoodsBean {

    int type;                        ///< 自定义消息类型
    String imageUrl;               ///< 商品的图片URL
    String merchandiseName;        ///< 商品的名称
    String merchandisePrice;           ///< 商品的价格
    String merchandiseID;          ///< 商品的ID

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getMerchandiseID() {
        return merchandiseID;
    }

    public void setMerchandiseID(String merchandiseID) {
        this.merchandiseID = merchandiseID;
    }


}
