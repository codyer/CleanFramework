package com.cody.xf.hybrid;

import android.annotation.SuppressLint;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.cody.xf.hybrid.core.JsCallback;
import com.cody.xf.hybrid.core.JsHandler;
import com.cody.xf.hybrid.core.JsHandlerFactory;
import com.cody.xf.hybrid.core.JsInteract;
import com.cody.xf.hybrid.core.JsWebChromeClient;

import java.lang.reflect.Method;

/**
 * Created by Cody.yi on 17/4/12.
 * JsBridge
 */
public class JsBridge {
    private volatile static JsBridge sInstance;
    private JsHandlerFactory mJsHandlerFactory;

    private JsBridge() {
        mJsHandlerFactory = new JsHandlerFactory();
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
     * 构建已经注册的处理类
     */
    @SuppressLint("SetJavaScriptEnabled")
    public void build(WebView webView) {
        if (webView == null){
            throw new NullPointerException("webView is null,can't build js handler!");
        }
        getInstance().mJsHandlerFactory.build();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new JsWebChromeClient());
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
}
