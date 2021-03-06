package com.cody.app.framework.hybrid.core;

import com.cody.xf.common.NotProguard;

/**
 * Created by cody.yi on 2017/4/13.
 * 所有Js处理类实现这个接口
 * Js调用的方法必须按照一定的格式定义，否则不生效
 * 格式：
 * public static void ***(WebView webView, JsonObject data, JsCallback callback){}
 */
@NotProguard
public interface JsHandler {
}
