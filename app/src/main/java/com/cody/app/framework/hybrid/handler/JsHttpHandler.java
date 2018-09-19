package com.cody.app.framework.hybrid.handler;

import android.webkit.WebView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.cody.app.framework.hybrid.core.JsCallback;
import com.cody.app.framework.hybrid.core.JsHandler;
import com.cody.repository.framework.Repository;
import com.cody.repository.framework.local.BaseLocalKey;
import com.cody.xf.common.NotProguard;
import com.cody.xf.utils.HttpUtil;
import com.cody.xf.utils.http.SimpleBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cody.yi on 2017/6/28.
 * Http delegate
 * <p> params
 * {
 * method:"post",
 * params:{},
 * url:"http://www.xx.com",
 * type:"json"
 * }
 */
@NotProguard
public final class JsHttpHandler implements JsHandler {
    private static final String TAG = "JsHttpHandler";

    public static void onDestroy() {
        HttpUtil.cancel(TAG);
    }

    /**
     * restful request
     */
    public static void restful(WebView webView, final JsonObject jsonObject, final JsCallback callback) {
        String url = jsonObject.get("url").getAsString();
        String type = jsonObject.get("type").getAsString();//JSON or FORM
        String method = jsonObject.get("method").getAsString();
        JsonObject jsonParams = jsonObject.getAsJsonObject("params");

        Map<String, String> params = null;
        if (!type.equalsIgnoreCase("json")) {
            params = new HashMap<>();
            for (Map.Entry<String, JsonElement> item : jsonParams.entrySet()) {
                params.put(item.getKey(), item.getValue().getAsString());
            }
            jsonParams = null;
        }

        HttpUtil.getOriginalResult(TAG,
                method.equalsIgnoreCase("get") ? HttpUtil.Method.GET : HttpUtil.Method.POST,
                url,
                Repository.getLocalMap(BaseLocalKey.X_TOKEN),
                params,
                jsonParams,
                JsonObject.class,
                null,
                new HttpUtil.Callback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject data) {
                        callback.callback(data);
                    }

                    @Override
                    public void onFailure(SimpleBean result) {
                        callback.failure(result.getMessage());
                    }

                    @Override
                    public void onError(SimpleBean error) {
                        callback.failure(error.getMessage());
                    }

                    @Override
                    public void onProgress(int progress, int max) {

                    }
                });
    }
}
