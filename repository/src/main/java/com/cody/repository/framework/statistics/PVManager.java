package com.cody.repository.framework.statistics;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by cody.yi on 2018/7/10.
 * 管理所有的P点和Z点
 */
public class PVManager {
    private static PVManager mInstance;
    private static P[] mActivityP = new P[]{
//            new P(SettingActivity.class, 10,2,"测试"),
    };
    private static P[] mFragmentP = new P[]{
//            new P(PersonalTabNewFragment.class, 10,2,"测试"),
    };

    public static void setActivityP(P[] activityP) {
        mActivityP = activityP;
    }

    public static void setFragmentP(P[] fragmentP) {
        mFragmentP = fragmentP;
    }

    public static void install() {
        if (mInstance == null) {
            mInstance = new PVManager();
        }
    }

    private PVManager() {
        for (P p : mActivityP) {
            HashMap<String, String> map = buildMap(p);
            if (p.classname != null) {
                UserDataManager.addStatActivityPageItem(p.classname.getName(), map);
            }
        }
        for (P p : mFragmentP) {
            HashMap<String, String> map = buildMap(p);
            if (p.classname != null) {
                UserDataManager.addStatFragmentPageItem(p.classname.getName(), map);
            }
        }
    }

    @NonNull
    private HashMap<String, String> buildMap(P p) {
        HashMap<String, String> map = new HashMap<>();
        if (p.id != 0) {
            map.put(StatKey.Parameter.id, String.valueOf(p.id));
        }
        if (p.zid != 0) {
            map.put(StatKey.Parameter.zid, String.valueOf(p.zid));
        }
        if (!TextUtils.isEmpty(p.p_title)) {
            map.put(StatKey.Parameter.p_title, p.p_title);
        }
        return map;
    }

    @Override
    public String toString() {
        return "PVManager{" +
                "mActivityP=" + Arrays.toString(mActivityP) +
                ", mFragmentP=" + Arrays.toString(mFragmentP) +
                '}';
    }

    public static class P {
        int id;
        int zid;
        String p_title;
        String pageDes;
        Class classname;

        public P(Class classname, int id) {
            this(classname, id, 0, null, null);
        }

        public P(Class classname, int id, String pageDes) {
            this(classname, id, 0, null, pageDes);
        }

        public P(Class classname, int id, int zid) {
            this(classname, id, zid, null);
        }

        public P(Class classname, int id, int zid, String pageDes) {
            this(classname, id, zid, null, pageDes);
        }

        public P(Class classname, int id, int zid, String p_title, String pageDes) {
            this.classname = classname;
            this.id = id;
            this.zid = zid;
            this.p_title = p_title;
            this.pageDes = pageDes;
        }
    }
}
