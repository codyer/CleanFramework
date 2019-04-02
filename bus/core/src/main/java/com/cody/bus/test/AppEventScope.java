/*
 * ************************************************************
 * 文件：AppEventScope.java  模块：core  项目：CleanFramework
 * 当前修改时间：2019年03月31日 21:16:33
 * 上次修改时间：2019年03月31日 19:39:06
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.bus.test;

import com.cody.live.event.bus.core.annotation.EventScope;
import com.cody.live.event.bus.core.annotation.EventType;

/**
 * Created by xu.yi. on 2019/3/31.
 * test demo
 */
@EventScope(name = "App test", active = false)
public enum AppEventScope {
    @EventType(description = "user login", data = String.class) login,
    @EventType(data = Integer.class) logout,
    @EventType("超时") timeout;
}
