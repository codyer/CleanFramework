/*
 * ************************************************************
 * 文件：App.java  模块：app  项目：CleanFramework
 * 当前修改时间：2019年04月02日 16:59:46
 * 上次修改时间：2019年04月02日 16:59:29
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.app.test;


import com.cody.live.event.bus.lib.annotation.Event;
import com.cody.live.event.bus.lib.annotation.EventScope;

/**
 * Created by xu.yi. on 2019/3/31.
 * test demo
 */
@EventScope(name = "AppScope", active = false)
public enum App {
    @Event(description = "user login", data = String.class) login,
    @Event(data = Integer.class) logout,
    @Event("超时") timeout;
}
