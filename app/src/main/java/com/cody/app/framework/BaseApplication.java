package com.cody.app.framework;


import android.app.ActivityManager;
import android.os.Process;

import com.baidu.mobstat.StatService;
import com.cody.app.BuildConfig;
import com.cody.app.framework.blues.Blues;
import com.cody.app.statistics.Pages;
import com.cody.repository.KeyConstant;
import com.cody.repository.framework.Repository;
import com.cody.repository.framework.local.BaseLocalKey;
import com.cody.repository.framework.statistics.ConstDataSource;
import com.cody.repository.framework.statistics.DataReportControl;
import com.cody.repository.framework.statistics.HxStat;
import com.cody.xf.XFApplication;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.HashMap;
import java.util.List;
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
        if (!isInMainProcess()) {
            return;
        }
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
        if (header.containsKey(BaseLocalKey.KEY_TOKEN)) {
            Map<String, String> token = new HashMap<>();
            token.put(BaseLocalKey.KEY_TOKEN, header.get(BaseLocalKey.KEY_TOKEN));
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

    public boolean isInMainProcess() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        if (processes == null || processes.isEmpty())
            return false;

        int myPid = Process.myPid();
        String pn = getPackageName();
        for (ActivityManager.RunningAppProcessInfo info : processes) {
            if (info.pid == myPid && pn.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
