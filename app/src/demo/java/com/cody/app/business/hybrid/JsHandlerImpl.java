package com.cody.app.business.hybrid;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;

import com.cody.xf.common.NotProguard;
import com.cody.xf.hybrid.JsBridge;
import com.cody.xf.hybrid.core.JsCallback;
import com.cody.xf.hybrid.core.JsHandler;
import com.cody.xf.hybrid.core.async.AsyncTaskExecutor;
import com.google.gson.JsonObject;

/**
 * Created by Cody.yi on 16/4/19.
 * Js handler 实现类
 */
@NotProguard
public final class JsHandlerImpl implements JsHandler {

    public static void login(WebView webView, JsonObject params, final JsCallback callback) {
        if (webView.getContext() instanceof Activity) {
            Intent intent = new Intent(webView.getContext(), LoginActivity.class);
            JsBridge.startActivityForResult(intent, new JsBridge.OnActivityResultListener() {
                @Override
                public void onActivityResult(int resultCode, Intent data) {
                    callback.success("onActivityResult" + "\nresultCode=" + resultCode);
                }
            });
        }
    }

    public static void delayExecuteTask(WebView webView, JsonObject params, final JsCallback callback) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                JsonObject data = new JsonObject();
                data.addProperty("result", "延迟3s执行native方法");
                callback.success(data);
            }
        }, 3000);
    }

    public static void performTimeConsumeTask(WebView webView, JsonObject params, final JsCallback callback) {
        AsyncTaskExecutor.runOnAsyncThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                JsonObject data = new JsonObject();
                data.addProperty("result", "执行耗时操作后的返回");
                callback.success(data);
            }
        });
    }
}
