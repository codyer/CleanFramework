package com.cody.app.statistics;

import com.cody.app.business.MainActivity;
import com.cody.repository.framework.statistics.PVManager;

/**
 * Created by cody.yi on 2018/7/30.
 * 所有的埋点pv z点配置类
 */
public class Pages {
    private static PVManager.P[] mActivityP = new PVManager.P[]{
//            new P(SettingActivity.class, 10,2,"测试"),
            new PVManager.P(MainActivity.class, 3964, 3965, "首页"),
    };

    private static PVManager.P[] mFragmentP = new PVManager.P[]{
//            new P(PersonalTabNewFragment.class, 10,2,"测试"),
    };

    /**
     * 要在HxStat.install(this);之前调用
     */
    public static void install() {
        new Pages();
    }

    public Pages() {
        PVManager.setActivityP(mActivityP);
        PVManager.setFragmentP(mFragmentP);
    }
}
