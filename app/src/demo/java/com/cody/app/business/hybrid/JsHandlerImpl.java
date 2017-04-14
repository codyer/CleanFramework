package com.cody.app.business.hybrid;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.Toast;

import com.cody.xf.common.NotProguard;
import com.google.gson.JsonObject;
import com.cody.xf.hybrid.core.JsCallback;
import com.cody.xf.hybrid.core.JsCode;
import com.cody.xf.hybrid.core.JsHandler;
import com.cody.xf.hybrid.core.JsResult;
import com.cody.xf.hybrid.core.async.AsyncTaskExecutor;

/**
 * Created by Cody.yi on 16/4/19.
 * Js handler 实现类
 */
@NotProguard
public class JsHandlerImpl implements JsHandler {

    public static void showToast(WebView webView, JsonObject params, JsCallback callback) {
        Toast.makeText(webView.getContext(), params.toString(), Toast.LENGTH_SHORT).show();

        JsResult<JsonObject> result = new JsResult<>();
        result.setCode(JsCode.SUCCESS);
        result.setMessage("成功了");

        callback.callJs(result);
    }

    public static void getIMSI(WebView webView, JsonObject params, JsCallback callback) {
        TelephonyManager telephonyManager = ((TelephonyManager) webView.getContext().getSystemService(Context.TELEPHONY_SERVICE));
        String imsi = telephonyManager.getSubscriberId();
        if (TextUtils.isEmpty(imsi)) {
            imsi = telephonyManager.getDeviceId();
        }
        JsonObject data = new JsonObject();
        data.addProperty("imsi", imsi);
        callback.success("成功了", data);
    }

    public static void getAppName(WebView webView, JsonObject params, JsCallback callback) {
        String appName;
        try {
            PackageManager packageManager = webView.getContext().getApplicationContext().getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(webView.getContext().getApplicationContext().getPackageName(), 0);
            appName = packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (Exception e) {
            e.printStackTrace();
            appName = "";
        }
        JsonObject data = new JsonObject();
        data.addProperty("result", appName);
        callback.success("成功了", data);
    }

    public static void getOsSdk(WebView webView, JsonObject params, JsCallback callback) {
        JsonObject data = new JsonObject();
        data.addProperty("os_sdk", Build.VERSION.SDK_INT);
        callback.success("成功了", data);
    }

    public static void finish(WebView webView, JsonObject params, JsCallback callback) {
        if (webView.getContext() instanceof Activity) {
            ((Activity) webView.getContext()).finish();
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
