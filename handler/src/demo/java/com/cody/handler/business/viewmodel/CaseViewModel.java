package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

/**
 * Created by cody.yi on 2017/3/30.
 * 案例模型
 */
public class CaseViewModel extends WithHeaderViewModel {
    private String mInfo;
    private boolean mVisibility;
    private String mImageUrl;

    public String getInfo() {
        return mInfo;
    }

    public void setInfo(String info) {
        mInfo = info;
    }

    public boolean isVisibility() {
        return mVisibility;
    }

    public void setVisibility(boolean visibility) {
        mVisibility = visibility;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
