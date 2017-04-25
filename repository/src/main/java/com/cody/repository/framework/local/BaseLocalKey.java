package com.cody.repository.framework.local;

/**
 * Created by cody.yi on 2017/3/30.
 * 本地数据存储
 * 目前只提供sharePreference方式
 */
public interface BaseLocalKey {
    String BASE = "R_";
    String VERSION = BASE + "Version";
    String HEADERS = BASE + "headers";
}
