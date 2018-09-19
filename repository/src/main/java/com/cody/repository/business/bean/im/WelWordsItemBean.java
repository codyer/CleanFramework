package com.cody.repository.business.bean.im;

import java.io.Serializable;

/**
 * Created by  qiaoping.xiao on 2017/10/10.
 */

public class WelWordsItemBean implements Serializable {
    private int type ;
    private String content;

    private String merchandiseId;
    private String imageUrl;
    private String merchandiseName;
    private double merchandisePrice;
    private String merchandiseSku;

    public WelWordsItemBean() {
    }

    public WelWordsItemBean(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public WelWordsItemBean(int type, String merchandiseId, String imageUrl, String merchandiseName, double merchandisePrice, String merchandiseSku) {
        this.type = type;
        this.merchandiseId = merchandiseId;
        this.imageUrl = imageUrl;
        this.merchandiseName = merchandiseName;
        this.merchandisePrice = merchandisePrice;
        this.merchandiseSku = merchandiseSku;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMerchandiseId() {
        return merchandiseId;
    }

    public void setMerchandiseId(String merchandiseId) {
        this.merchandiseId = merchandiseId;
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

    public double getMerchandisePrice() {
        return merchandisePrice;
    }

    public void setMerchandisePrice(double merchandisePrice) {
        this.merchandisePrice = merchandisePrice;
    }

    public String getMerchandiseSku() {
        return merchandiseSku;
    }

    public void setMerchandiseSku(String merchandiseSku) {
        this.merchandiseSku = merchandiseSku;
    }
}
