/*
 * Copyright (c)  Created by Cody.yi on 2016/9/18.
 */

package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;

import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

/**
 * Created by chy on 2016/9/18.
 * 用户手册的viewModel
 */
public class GuideManualViewModel extends WithHeaderViewModel {
    private String url;
    private long downrRefernece;
    private int mGuideId;
    //IM软键盘进入导购手册
    private boolean mImEnter = false;
    //IM会话列表进入导购手册
    private boolean mImListEnter = false;
    private ObservableBoolean mDownload = new ObservableBoolean(false);
    private ObservableBoolean mHasGuide = new ObservableBoolean(false);

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setDownrRefernece(long downrRefernece) {
        this.downrRefernece = downrRefernece;
    }

    public long getDownrRefernece() {
        return downrRefernece;
    }

    public void setGuideId(int guideId) {
        mGuideId = guideId;
    }

    public int getGuideId() {
        return mGuideId;
    }

    public ObservableBoolean getDownload() {
        return mDownload;
    }

    public ObservableBoolean getHasGuide() {
        return mHasGuide;
    }

    public void setHasGuide(Boolean hasGuide) {
        this.mHasGuide.set(hasGuide);
    }

    public void setDownload(boolean download) {
        mDownload.set(download);
    }

    public void setImEnter(boolean imEnter) {
        mImEnter = imEnter;
    }

    public boolean isImEnter() {
        return mImEnter;
    }

    public void setImListEnter(boolean imListEnter) {
        mImListEnter = imListEnter;
    }

    public boolean isImListEnter() {
        return mImListEnter;
    }
}
