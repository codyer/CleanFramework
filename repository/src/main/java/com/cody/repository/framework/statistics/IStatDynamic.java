package com.cody.repository.framework.statistics;

import java.util.HashMap;

/**
 * Created by cody.yi on 2018/7/10.
 * 埋点动态参数
 */
public interface IStatDynamic {
    void onStatResume(HashMap<String, String> hashMap, HashMap<String, String> hashMap1);
}
