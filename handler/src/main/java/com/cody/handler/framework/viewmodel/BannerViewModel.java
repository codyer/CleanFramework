package com.cody.handler.framework.viewmodel;

/**
 * Created by cody.yi on 2017/4/26.
 * banner viewModel
 */
public class BannerViewModel extends XItemViewModel {
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
        if (!super.equals(o)) return false;

        BannerViewModel that = (BannerViewModel) o;

        if (materType != null ? !materType.equals(that.materType) : that.materType != null)
            return false;
        if (materName != null ? !materName.equals(that.materName) : that.materName != null)
            return false;
        if (imgDesc != null ? !imgDesc.equals(that.imgDesc) : that.imgDesc != null) return false;
        if (imgUrl != null ? !imgUrl.equals(that.imgUrl) : that.imgUrl != null) return false;
        if (imgSize != null ? !imgSize.equals(that.imgSize) : that.imgSize != null) return false;
        if (imgType != null ? !imgType.equals(that.imgType) : that.imgType != null) return false;
        if (imgId != null ? !imgId.equals(that.imgId) : that.imgId != null) return false;
        return linkUrl != null ? linkUrl.equals(that.linkUrl) : that.linkUrl == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (materType != null ? materType.hashCode() : 0);
        result = 31 * result + (materName != null ? materName.hashCode() : 0);
        result = 31 * result + (imgDesc != null ? imgDesc.hashCode() : 0);
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        result = 31 * result + (imgSize != null ? imgSize.hashCode() : 0);
        result = 31 * result + (imgType != null ? imgType.hashCode() : 0);
        result = 31 * result + (imgId != null ? imgId.hashCode() : 0);
        result = 31 * result + (linkUrl != null ? linkUrl.hashCode() : 0);
        return result;
    }
}
