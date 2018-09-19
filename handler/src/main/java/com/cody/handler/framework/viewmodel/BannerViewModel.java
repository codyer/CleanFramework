package com.cody.handler.framework.viewmodel;

import java.util.Objects;

/**
 * Created by cody.yi on 2017/4/26.
 * banner viewModel
 */
public class BannerViewModel {
    private String materType;
    private String materName;
    private String imgDesc;
    private String imgUrl;
    private String imgSize;
    private String imgType;
    private String imgId;
    private String linkUrl;

    public BannerViewModel() {
    }

    public BannerViewModel(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMaterType() {
        return materType;
    }

    public void setMaterType(String materType) {
        this.materType = materType;
    }

    public String getMaterName() {
        return materName;
    }

    public void setMaterName(String materName) {
        this.materName = materName;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgSize() {
        return imgSize;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BannerViewModel that = (BannerViewModel) o;
        return Objects.equals(materType, that.materType) &&
                Objects.equals(materName, that.materName) &&
                Objects.equals(imgDesc, that.imgDesc) &&
                Objects.equals(imgUrl, that.imgUrl) &&
                Objects.equals(imgSize, that.imgSize) &&
                Objects.equals(imgType, that.imgType) &&
                Objects.equals(imgId, that.imgId) &&
                Objects.equals(linkUrl, that.linkUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(materType, materName, imgDesc, imgUrl, imgSize, imgType, imgId, linkUrl);
    }
}
