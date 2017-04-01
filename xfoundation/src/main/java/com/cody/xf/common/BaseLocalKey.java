package com.cody.xf.common;

/**
 * Created by cody.yi on 2017/3/30.
 * 本地数据存储
 * 目前只提供sharePreference方式
 */

public interface BaseLocalKey {
    String BASE = "XF_";
    String WITH_HEADERS = BASE + "with_headers";
    String HEADERS = BASE + "headers";
}
