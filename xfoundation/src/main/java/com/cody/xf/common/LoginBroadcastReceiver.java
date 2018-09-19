package com.cody.xf.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.cody.xf.XFoundation;
import com.cody.xf.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cody.yi on 2017/5/25.
 * 登录超时广播接收器
 */
public class LoginBroadcastReceiver extends BroadcastReceiver {
    private static final String ACTION = "com.cody.xf.common.LoginBroadcastReceiver";
    private static final String LOGIN_BROADCAST_KEY = "LOGIN_BROADCAST_KEY";
    private static List<LoginListener> sListeners = new ArrayList<>();

    /**
     * 注册接收者
     */
    public static LoginBroadcastReceiver register() {
        LoginBroadcastReceiver receiver = new LoginBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION);
        LocalBroadcastManager.getInstance(XFoundation.getContext()).registerReceiver(receiver, intentFilter);
        return receiver;
    }

    /**
     * 取消注册接收者
     *
     * @param receiver 接收更新
     */
    public static void unRegister(LoginBroadcastReceiver receiver) {
        if (receiver == null) return;
        LocalBroadcastManager.getInstance(XFoundation.getContext()).unregisterReceiver(receiver);
    }

    /**
     * 发送登录结果
     *
     * @param result 结果 成功：true
     */
    public static void sentEvent(boolean result) {
        Intent intent = new Intent(ACTION);
        intent.putExtra(LOGIN_BROADCAST_KEY, result);
        LocalBroadcastManager.getInstance(XFoundation.getContext()).sendBroadcast(intent);
    }

    /**
     * 添加监听器
     *
     * @param listener 监听器
     */
    public static void addListener(LoginListener listener) {
        sListeners.add(listener);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean success = intent.getBooleanExtra(LOGIN_BROADCAST_KEY, false);
        if (success) {
            for (LoginListener listener : sListeners) {
                listener.onSuccess();
            }
        } else {
            ToastUtil.showToast("不登录啥也干不了~");
        }
        sListeners.clear();
    }

    public interface LoginListener {
        void onSuccess();
    }
}
