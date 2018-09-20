package com.cody.app.framework.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.cody.app.business.MainActivity;
import com.cody.xf.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class HandlerJPushReceiver extends BroadcastReceiver {
    private final String TAG = this.getClass().getSimpleName();
    public static final String MESSAGE_RECEIVED_ACTION = "com.cody.app.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null) return;
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notificationId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            //打开自定义的Activity
            parseExtra(context, bundle);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    private void parseExtra(Context context, Bundle bundle) {
        try {
            //jsonStr = {"port":"80","method":"活动详情页","param":"{\"pageType\":\"活动详情页\",\"param1\":141}","clazz":"MessageHandlerCommonImpl"}
            String jsonStr = bundle.getString(JPushInterface.EXTRA_EXTRA);
            LogUtil.d("PushReceiver", "=============== push extra:" + jsonStr);
//            if (jsonStr == null) {
            startApp(context);
//                return;
//            }
            // 暂时没有做消息分发
//            PushBean pushBean = JsonUtil.fromJson(jsonStr, PushBean.class);
//            JpushMessageDispatcher.jump(context,pushBean.getParam());
        } catch (Exception e) {
            e.printStackTrace();
            startApp(context);
        }
    }

    private void startApp(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(context, MainActivity.class));
        context.startActivity(intent);
    }

    // 打印所有的 intent extra 数据
    private String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:").append(key).append(", value: [").append(myKey).append(" - ").append(json.optString(myKey)).append("]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Intent msgIntent = new Intent(MESSAGE_RECEIVED_ACTION);
        msgIntent.putExtra(KEY_MESSAGE, message);
        if (!JPushUtil.isEmpty(extras)) {
            try {
                JSONObject extraJson = new JSONObject(extras);
                if (extraJson.length() > 0) {
                    msgIntent.putExtra(KEY_EXTRAS, extras);
                }
            } catch (JSONException e) {

            }

        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
    }
}
