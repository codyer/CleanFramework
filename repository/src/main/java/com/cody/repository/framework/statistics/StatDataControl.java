/*
 * @Title StatDataControl.java
 * @Copyright Copyright 2016 Redstar Macalline All Rights Reserved.
 * @author SongZheng
 * @date 2016-4-11 下午4:20:04
 * @version 1.0
 */
package com.cody.repository.framework.statistics;

import java.util.Map;

/**
 * @author SongZheng
 * @Description 埋点数据上报
 * @date 2016-4-11 下午4:20:04
 */
public abstract class StatDataControl {
    /**
     * 上报埋点数据
     *
     * @param params  url参数
     * @param params2 post参数
     */
    public abstract void reportStatPvUvData(Map<String, String> params, Map<String, String> params2);

    /**
     * 上报埋点F数据
     *
     * @param params  url参数
     * @param params2 post参数
     */
    public abstract void reportStatFData(Map<String, String> params, Map<String, String> params2);

    /**
     * 上报埋点Z数据
     *
     * @param params  url参数
     * @param params2 post参数
     */
    public abstract void reportStatZData(Map<String, String> params, Map<String, String> params2);

    /**
     * 上报埋点S数据
     *
     * @param params  url参数
     * @param params2 post参数
     */
    public abstract void reportStatSData(Map<String, String> params, Map<String, String> params2);

    /**
     * 上报埋点I数据
     *
     * @param params  url参数
     * @param params2 post参数
     */
    public abstract void reportStatIData(Map<String, String> params, Map<String, String> params2);
}
