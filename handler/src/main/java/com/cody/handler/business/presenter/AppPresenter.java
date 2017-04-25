package com.cody.handler.business.presenter;

import com.cody.xf.utils.http.LoginStatusListener;

/**
 * Created by cody.yi on 2017/3/30.
 * App级别的处理，可以处理本地数据
 * 全局就一个，因此可以使用单例模式
 */
public class AppPresenter implements LoginStatusListener{
    private static AppPresenter ourInstance = new AppPresenter();

    public static AppPresenter getInstance() {
        return ourInstance;
    }

    private AppPresenter() {

    }

    @Override
    public void logOut() {

    }
}