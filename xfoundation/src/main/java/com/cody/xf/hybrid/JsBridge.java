package com.cody.xf.hybrid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.SparseArray;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.cody.xf.hybrid.core.JsCallback;
import com.cody.xf.hybrid.core.JsHandler;
import com.cody.xf.hybrid.core.JsHandlerFactory;
import com.cody.xf.hybrid.core.JsInteract;
import com.cody.xf.hybrid.core.JsWebChromeClient;
import com.cody.xf.hybrid.core.JsWebViewClient;
import com.cody.xf.utils.LogUtil;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Cody.yi on 17/4/12.
 * JsBridge
 */
public class JsBridge {
    private volatile static JsBridge sInstance;
    private JsHandlerFactory mJsHandlerFactory;
    private SparseArray<OnActivityResultListener> mResultListener;
    private WeakReference<WebView> mWebViewRef;

    private JsBridge() {
        mJsHandlerFactory = new JsHandlerFactory();
        mResultListener = new SparseArray<>();
    }

    private static JsBridge getInstance() {
        if (sInstance == null) {
            synchronized (JsBridge.class) {
                if (sInstance == null) {
                    sInstance = new JsBridge();
                }
            }
        }
        return sInstance;
    }

    /**
     * 添加Js处理类，可以使用连缀的方式添加多个处理类，最后需要调用build方法使处理类生效
     *
     * @param clazz 处理类类型
     */
    public static JsBridge addJsHandler(Class<? extends JsHandler> clazz) {
        getInstance().mJsHandlerFactory.register(clazz);
        return sInstance;
    }

    /**
     * 同步指定地址的cookie到webView
     */
    public JsBridge syncCookie(Context context, String url, Map<String, String> cookies) {
        if (context == null || TextUtils.isEmpty(url) || cookies == null) {
            return sInstance;
        }

        CookieManager cookieManager = CookieManager.getInstance();
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            cookieManager.setCookie(url, entry.getValue() + "=" + entry.getKey());
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(context);
            cookieSyncManager.sync();
        }
        return sInstance;
    }

    /**
     * 构建已经注册的处理类
     */
    @SuppressLint("SetJavaScriptEnabled")
    public void build(WebView webView, OnProgressListener onProgressListener) {
        if (webView == null) {
            throw new NullPointerException("webView is null,can't build js handler!");
        }
        mWebViewRef = new WeakReference<>(webView);
        getInstance().mJsHandlerFactory.build();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        String userAgentString = settings.getUserAgentString();
        settings.setUserAgentString(userAgentString + ";android;chinaredstar");
//		settings.setPluginState(PluginState.ON_DEMAND);
        settings.setAllowFileAccess(true);
        // 设置 缓存模式
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        String cacheDirPath = webView.getContext().getFilesDir().getAbsolutePath() + "/webView";
        //设置  Application Caches 缓存目录
        settings.setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //5.0之后h5内支持混合模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new JsWebViewClient());
        webView.setWebChromeClient(new JsWebChromeClient(onProgressListener));
    }

    /**
     * 查找处理js处理方法
     */
    public static Method findMethod(String className, String methodName) {
        return getInstance().mJsHandlerFactory.findMethod(className, methodName);
    }

    /**
     * 调用Native方法
     */
    public static void callNative(WebView webView, String message) {
        JsInteract.newInstance().callNative(webView, message);
    }

    public static JsCallback getJsCallback(WebView webView, String port) {
        return JsCallback.newInstance(webView, port);
    }

    /**
     * 替换Activity中的startActivityForResult
     */
    public static void startActivityForResult(Intent intent, OnActivityResultListener listener) {
        int requestCode = getInstance().mResultListener.size();
        getInstance().mResultListener.put(requestCode, listener);
        if (getInstance().mWebViewRef != null && getInstance().mWebViewRef.get() != null) {
            WebView webView = getInstance().mWebViewRef.get();
            if (webView.getContext() instanceof Activity) {
                ((Activity) webView.getContext()).startActivityForResult(intent, requestCode);
            }
        } else {
            LogUtil.d("webView is recycled.");
        }
    }

    /**
     * 需要用到startActivityForResult的时候需要调用此回调
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        OnActivityResultListener listener = getInstance().mResultListener.get(requestCode);
        if (listener != null) {
            listener.onActivityResult(resultCode, data);
            getInstance().mResultListener.remove(requestCode);
        }
    }

    /**
     * 需要在包含webView的Activity中调用，用来回收webView数据
     */
    public static void onDestroy() {
        if (getInstance().mWebViewRef != null && getInstance().mWebViewRef.get() != null) {
            WebView webView = getInstance().mWebViewRef.get();
            webView.stopLoading();
            webView.removeAllViews();
            webView.setTag(null);
            webView.clearHistory();
            webView.removeAllViews();
            webView.destroy();
        }
        getInstance().mResultListener.clear();
    }

    public interface OnActivityResultListener {
        void onActivityResult(int resultCode, Intent data);
    }

    public interface OnProgressListener {
        void onProgress(int progress);
    }
}
