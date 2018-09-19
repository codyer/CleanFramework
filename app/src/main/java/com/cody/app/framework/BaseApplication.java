package com.cody.app.framework;


import com.baidu.mobstat.StatService;
import com.cody.app.BuildConfig;
import com.cody.app.framework.blues.Blues;
import com.cody.app.statistics.Pages;
import com.cody.repository.KeyConstant;
import com.cody.repository.business.interaction.constant.Account;
import com.cody.repository.framework.Repository;
import com.cody.repository.framework.local.BaseLocalKey;
import com.cody.repository.framework.statistics.ConstDataSource;
import com.cody.repository.framework.statistics.DataReportControl;
import com.cody.repository.framework.statistics.HxStat;
import com.cody.xf.XFApplication;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by cody.yi on 2017/3/7.
 * Base
 */
public class BaseApplication extends XFApplication {
    static {
        PlatformConfig.setWeixin(KeyConstant.WEIXIN_KEY, KeyConstant.WEIXIN_SECRET);
        PlatformConfig.setQQZone(KeyConstant.QQ_KEY, KeyConstant.QQ_SECRET);
        PlatformConfig.setSinaWeibo(KeyConstant.SINA_KEY, KeyConstant.SINA_SECRET, KeyConstant.SINA_REDIRECT_URL);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //数据仓库
        Repository.install(this);
        Blues.install(this);

        //百度统计
        StatService.setDebugOn(BuildConfig.DEBUG);
        //极光推送
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);

        //埋点
        Pages.install();
        HxStat.install(this);
        HxStat.setConstDataSource(new ConstDataSource());
        HxStat.setDataControl(new DataReportControl());
        //友盟
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    @Override
    public void onLogin(Map<String, String> header) {
        if (header.containsKey(Account.KEY_TOKEN)) {
            Map<String, String> token = new HashMap<>();
            token.put(Account.KEY_TOKEN, header.get(Account.KEY_TOKEN));
            Repository.setLocalMap(BaseLocalKey.X_TOKEN, token);
            Repository.setLocalMap(BaseLocalKey.HEADERS, header);
        } else {
            onLogOutByTime();
        }
    }

    @Override
    public void onLogOutByTime() {
        clearHeader();
    }

    @Override
    public void onLogOutByUser() {
        clearHeader();
    }

    @Override
    public void clearHeader() {
        Repository.setLocalMap(BaseLocalKey.HEADERS, null);
        Repository.setLocalMap(BaseLocalKey.X_TOKEN, null);
    }
}
