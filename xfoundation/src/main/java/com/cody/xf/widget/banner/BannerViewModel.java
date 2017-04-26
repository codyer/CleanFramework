package com.cody.xf.widget.banner;

/**
 * Created by cody.yi on 2017/4/26.
 * banner viewModel
 */
public class BannerViewModel {
    public BannerViewModel(String url) {
        mUrl = url;
    }

    private String mUrl;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString() {
        return "{" +
                "mUrl='" + mUrl + '\'' +
                '}';
    }
}
