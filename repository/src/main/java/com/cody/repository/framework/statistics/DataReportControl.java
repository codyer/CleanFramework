/*
 * @Title DataReportControl.java
 * @Copyright Copyright 2016 Cody All Rights Reserved.
 * @author Cody
 * @date 2016-4-11 下午4:23:17
 * @version 1.0
 */
package com.cody.repository.framework.statistics;


import com.cody.repository.Domain;
import com.cody.repository.framework.Repository;
import com.cody.repository.framework.local.BaseLocalKey;
import com.cody.xf.utils.CommonUtil;
import com.cody.xf.utils.HttpUtil;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.http.SimpleBean;

import java.util.Map;


/**
 * @author Cody.yi
 *         埋点请求
 */
public class DataReportControl extends StatDataControl {
    private static String TAG;

    public DataReportControl() {
        TAG = getClass().getName();
    }

    @Override
    public void reportStatPvUvData(Map<String, String> params, Map<String, String> params2) {
        postStat(params, params2, StatUrlPath.STAT_URL_PV_UV);
    }

    @Override
    public void reportStatFData(Map<String, String> params, Map<String, String> params2) {
        postStat(params, params2, StatUrlPath.STAT_URL_F);
    }

    @Override
    public void reportStatZData(Map<String, String> params, Map<String, String> params2) {
        postStat(params, params2, StatUrlPath.STAT_URL_Z);
    }

    @Override
    public void reportStatSData(Map<String, String> params, Map<String, String> params2) {
        postStat(params, params2, StatUrlPath.STAT_URL_S);
    }

    @Override
    public void reportStatIData(Map<String, String> params, Map<String, String> params2) {
        postStat(params, params2, StatUrlPath.STAT_URL_I);
    }

    private void postStat(Map<String, String> params, Map<String, String> params2, String path) {
        String url = Domain.STAT_DOMAIN + CommonUtil.buildPathUrl(path, params);
        LogUtil.d(TAG, url);
        Map<String, String> token = Repository.getLocalMap(BaseLocalKey.X_TOKEN);
        HttpUtil.getData(TAG, HttpUtil.Method.POST, url, token, params2, SimpleBean.class, new HttpUtil.Callback<SimpleBean>() {
            @Override
            public void onSuccess(SimpleBean data) {

            }

            @Override
            public void onFailure(SimpleBean result) {

            }

            @Override
            public void onError(SimpleBean error) {

            }

            @Override
            public void onProgress(int progress, int max) {

            }
        });
    }
}
