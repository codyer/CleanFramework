/*
 * ************************************************************
 * 文件：ToBeCompilerOut.java  模块：core  项目：CleanFramework
 * 当前修改时间：2019年03月31日 21:16:40
 * 上次修改时间：2019年03月31日 21:15:54
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.bus.test;

import android.arch.lifecycle.MutableLiveData;

import com.cody.live.event.bus.core.IEvent;
import com.cody.live.event.bus.core.wrapper.LiveEventWrapper;

/**
 * Created by xu.yi. on 2019/3/31.
 * 自动生成，请勿修改
 */
public interface ToBeCompilerOut extends IEvent {
    MutableLiveData<Object> EVENT1();
    LiveEventWrapper<String> withEvent$LOGIN();
    LiveEventWrapper<String> withEvent$login();
}
