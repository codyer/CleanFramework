package com.cody.repository.business.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ShareBean implements Parcelable {

    private String desc;
    private String imgUrl;
    private String title;
    private String url;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.desc);
        dest.writeString(this.imgUrl);
        dest.writeString(this.title);
        dest.writeString(this.url);
    }

    public ShareBean() {
    }

    protected ShareBean(Parcel in) {
        this.desc = in.readString();
        this.imgUrl = in.readString();
        this.title = in.readString();
        this.url = in.readString();
    }

    public static final Creator<ShareBean> CREATOR = new Creator<ShareBean>() {
        @Override
        public ShareBean createFromParcel(Parcel source) {
            return new ShareBean(source);
        }

        @Override
        public ShareBean[] newArray(int size) {
            return new ShareBean[size];
        }
    };
}
