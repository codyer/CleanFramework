/*
 * @Title StatConstDataSource.java
 * @Copyright Copyright 2016 Cody All Rights Reserved.
 * @author Cody
 * @date 2016-4-11 下午2:57:00
 * @version 1.0
 */
package com.cody.repository.framework.statistics;

/**
 * 需要针对APP,获取的信息
 * 需要实现外部抽象方法
 */
public abstract class StatConstDataSource {
    /**
     * device id
     */
    public abstract String deviceId();

    /**
     * 登陆用户ID
     */
    public abstract String userId();

    /**
     * 国家名称
     */
    public abstract String country();

    /**
     * 省份名称
     */
    public abstract String province();

    /**
     * 城市名称,定位城市
     */
    public abstract String city();

    /**
     * 城市名称,用户手动选择,非定位城市
     */
    public abstract String userCity();

    /**
     * 区名称
     */
    public abstract String district();

    /**
     * 应用连接的服务器域名
     */
    public abstract String domain();

    /**
     * 获得渠道号
     */
    public abstract String channel();
    /**
     * 获得 经度，纬度
     */
    public abstract String gps();
    /*******************************************************************************
     *	Private Methods
     *******************************************************************************/

    /*******************************************************************************
     *	Internal Class,Interface
     *******************************************************************************/

    /*******************************************************************************
     *	Unused Codes
     *******************************************************************************/
}
