/*
 * @Title StatUrl.java
 * @Copyright Copyright 2016 Cody All Rights Reserved.
 * @author Cody
 * @date 2016-2-24 上午9:06:22
 * @version 1.0
 */
package com.cody.repository.framework.statistics;


/**
 * @author Cody
 * @Description 数据统计URL地址
 * @date 2016-2-24 上午9:06:22
 */
public interface StatUrlPath {
    /**
     * 统计PVUV地址
     */
    String STAT_URL_PV_UV = "/p?";
    /**
     * 统计F点(点击)地址
     */
    String STAT_URL_F = "/f?";
    /**
     * 统计Z点(停留时间)地址
     */
    String STAT_URL_Z = "/z?";
    /**
     * 统计S点(屏数)地址
     */
    String STAT_URL_S = "/s?";
    /**
     * 统计I点地址
     */
    String STAT_URL_I = "/i?";
}
