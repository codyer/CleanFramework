package com.cody.app.framework.hybrid.handler;

import android.webkit.WebView;
import com.google.gson.JsonObject;
import com.cody.app.framework.hybrid.core.JsCallback;
import com.cody.app.framework.hybrid.core.JsHandler;
import com.cody.xf.common.NotProguard;

/**
 * Created by chen.huarong on 18/1/18.
 * Js handler 实现类
 */
@NotProguard
public final class PushHandlerCommonImpl implements JsHandler {

    /****
     * 推送活动详情页
     * @param webView
     * @param params
     * @param callback
     */
    public static void jumpToActiveDetail(final WebView webView, final JsonObject params, JsCallback callback) {
        if (params != null) {
            if (params.has("activeId")) {
                String activeId = params.getAsJsonPrimitive("activeId").getAsString();
            }
        }
    }
}



