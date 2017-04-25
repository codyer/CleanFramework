package com.cody.handler.framework.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

/**
 * Created by cody.yi on 2017/4/21.
 * Html ViewModel
 */
public class HtmlViewModel extends WithHeaderViewModel {
    public final static int MAX_PROGRESS = 100;
    public final ObservableBoolean isLoaded = new ObservableBoolean(false);//是否加载完成
    private final ObservableInt mProgress = new ObservableInt(0);//加载进度

    public ObservableInt getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        mProgress.set(progress);
        if (progress >= MAX_PROGRESS){
            isLoaded.set(true);
        }
    }

    private String mUrl;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
