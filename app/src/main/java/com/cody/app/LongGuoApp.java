package com.cody.app;

import com.cody.app.business.LoginNewActivity;
import com.cody.app.business.easeui.ImManager;
import com.cody.app.framework.BaseApplication;
import com.cody.xf.XFoundation;

/**
 * Created by cody.yi on 2016/8/4.
 * App
 */
public class LongGuoApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //配置IM中的Debug和release设置
//        EasePathUtil.init();
        //初始化DemoHelper
        ImManager.getInstance().init(XFoundation.getContext());
    }

    @Override
    public void onLogOutByTime() {
        super.onLogOutByTime();
        //跳转页面到登录
        LoginNewActivity.logOutByTime();
    }

    @Override
    public void onLogOutByUser() {
        super.onLogOutByUser();
        LoginNewActivity.logOutByUser();
    }
}
