package com.cody.xf.hybrid.core;


import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.webkit.WebView;

import com.google.gson.JsonObject;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cody.yi on 17/4/12.
 * 符合注入的方法的格式:
 * public static void ***(WebView webView, JsonObject data, JsCallback callback){}
 */
public class JsHandlerFactory {
    private ArrayMap<String, ArrayMap<String, Method>> mHandlerMethods;
    private List<Class<? extends JsHandler>> mHandlerClasses;

    public JsHandlerFactory() {
        mHandlerMethods = new ArrayMap<>();
        mHandlerClasses = new ArrayList<>();
    }

    /**
     * 注册可以处理Js请求的类
     */
    public JsHandlerFactory register(Class<? extends JsHandler> clazz) {
        if (clazz == null)
            throw new NullPointerException("NativeMethodInjectHelper:The addJsHandler can not be null!");
        mHandlerClasses.add(clazz);
        return this;
    }

    /**
     * 构建所有注册的Js Handler
     */
    public void build() {
        for (Class<?> clazz : mHandlerClasses) {
            putMethod(clazz);
        }
        mHandlerClasses.clear();
    }

    public Method findMethod(String className, String methodName) {
        if (TextUtils.isEmpty(className) || TextUtils.isEmpty(methodName))
            return null;
        if (mHandlerMethods.containsKey(className)) {
            ArrayMap<String, Method> arrayMap = mHandlerMethods.get(className);
            if (arrayMap == null)
                return null;
            if (arrayMap.containsKey(methodName)) {
                return arrayMap.get(methodName);
            }
        }
        return null;
    }

    /**
     * 添加类包含符合规则的Js处理方法
     */
    private void putMethod(Class<?> clazz) {
        if (clazz == null)
            return;
        ArrayMap<String, Method> arrayMap = new ArrayMap<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (checkMethod(method)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes != null && parameterTypes.length == 3) {
                    if (checkParameter(parameterTypes)) {
                        arrayMap.put(method.getName(), method);
                    }
                }
            }
        }
        mHandlerMethods.put(clazz.getSimpleName(), arrayMap);
    }

    /**
     * 检查Js处理器的方法格式是否正确
     * 正确格式：public static void ***(WebView webView, JsonObject data, JsCallback callback){}
     */
    private boolean checkMethod(Method method) {
        return method.getModifiers() == (Modifier.PUBLIC | Modifier.STATIC) &&
                method.getReturnType() == void.class &&
                method.getName() != null;
    }

    /**
     * 检查参数格式是否正确
     * 正确格式：public static void ***(WebView webView, JsonObject data, JsCallback callback){}
     */
    private boolean checkParameter(Class<?>[] parameterTypes) {
        return WebView.class == parameterTypes[0] &&
                JsonObject.class == parameterTypes[1] &&
                JsCallback.class == parameterTypes[2];
    }

}
