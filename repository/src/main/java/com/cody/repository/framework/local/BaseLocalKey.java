package com.cody.repository.framework.local;

/**
 * Created by cody.yi on 2017/3/30.
 * 本地数据存储
 * 目前只提供sharePreference方式
 */
public interface BaseLocalKey {
    String BASE = "R_";
    String VERSION_CODE = BASE + "version_code";
    String HEADERS = BASE + "headers";
    String X_TOKEN = BASE + "x_token";
    String OPEN_ID = BASE + "open_id";//用户openId
    String DEVICE_ALI_UTD_ID = "device_ali_utdid";//设备唯一号
    String FIRST_OPEN = BASE + "first_open";
    String CITY_NAME = "cityName";// 城市名称
    String CITY_CODE = "cityCode";// 城市code
}
