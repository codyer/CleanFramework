/*
 * Copyright (c)  Created by Cody.yi on 2016/9/18.
 */

package com.cody.app.framework.EventBus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.cody.xf.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cody.yi on 2016/9/18.
 * 预约单数据同步数据总线
 * 根据唯一id-->key 更新对应列表的item
 */
public class SimpleEventBus extends BroadcastReceiver {
    private static final String ACTION = "cody.ReservationBroadcastReceiver";
    private static final String KEY = "KEY";
    private EventReceiver mReceiver;
    private static final Map<EventReceiver, SimpleEventBus> mBusMap = new HashMap<>();

    private SimpleEventBus(EventReceiver receiver) {
        mReceiver = receiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String key = intent.getStringExtra(KEY);
        if (StringUtil.isNotEmpty(key)) {
            mReceiver.onEventReceiver(key);
        }
    }

    /**
     * 注册接收者
     *
     * @param context  上下文
     * @param receiver 接收更新
     */
    public static SimpleEventBus register(Context context, EventReceiver receiver) {
        SimpleEventBus eventBus;
        if (!mBusMap.containsKey(receiver)) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION);
            eventBus = new SimpleEventBus(receiver);
            mBusMap.put(receiver, eventBus);
            context.registerReceiver(eventBus, intentFilter);
        } else {
            eventBus = mBusMap.get(receiver);
        }
        return eventBus;
    }

    /**
     * 取消注册接收者
     *
     * @param context  上下文
     * @param receiver 接收更新
     */
    public static void unRegister(Context context, EventReceiver receiver) {
        if (mBusMap.containsKey(receiver)) {
            SimpleEventBus eventBus = mBusMap.get(receiver);
            context.unregisterReceiver(eventBus);
            mBusMap.remove(receiver);
        }
    }

    /**
     * 发送需要更新的列表的唯一id
     *
     * @param context 上下文
     * @param key     唯一ID
     */
    public static void sentEvent(Context context, String key) {
        Intent intent = new Intent(ACTION);
        intent.putExtra(KEY, key);
        context.sendBroadcast(intent);
    }

    /**
     * 事件接收刷新
     */
    public interface EventReceiver {
        /**
         * 更新某个key对应的item
         *
         * @param key 列表中的唯一id
         */
        void onEventReceiver(String key);
    }
}
