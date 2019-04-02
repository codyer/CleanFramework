/*
 * ************************************************************
 * 文件：TestApp.java  模块：core  项目：CleanFramework
 * 当前修改时间：2019年03月31日 21:16:59
 * 上次修改时间：2019年03月31日 21:16:59
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.bus.test;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.cody.live.event.bus.LiveEventBus;
import com.cody.live.event.bus.core.wrapper.ObserverWrapper;

/**
 * Created by xu.yi. on 2019/3/31.
 * CleanFramework
 */
public class TestApp {
    public static void main() {
        ToBeCompilerOut out = null;
        out.EVENT1().observe(null, new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object o) {

            }
        });
        LiveEventBus.begin()
                .inScope(ToBeCompilerOut.class)
                .withEvent$LOGIN()
                .observe(null, new ObserverWrapper<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                    }
                });

        LiveEventBus.begin()
                .inScope(ToBeCompilerOut.class)
                .withEvent$login().postValue("");
    }
}
