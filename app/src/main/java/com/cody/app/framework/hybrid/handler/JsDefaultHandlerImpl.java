package com.cody.app.framework.hybrid.handler;

import android.text.TextUtils;
import android.webkit.WebView;

import com.cody.app.framework.hybrid.core.JsCallback;
import com.cody.app.framework.hybrid.core.JsHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.repository.framework.local.BaseLocalKey;
import com.cody.repository.framework.statistics.StatConstData;
import com.cody.repository.framework.statistics.StatKey;
import com.cody.repository.framework.statistics.UserDataManager;
import com.cody.xf.common.NotProguard;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.DeviceUtil;

/**
 * Created by Cody.yi on 16/4/19.
 * Js handler 默认实现类
 * 兼容C端老的方法在这里添加
 */
@NotProguard
public final class JsDefaultHandlerImpl implements JsHandler {
    /**
     * 获取用户信息
     */
    public static void userInfo(WebView webView, JsonObject params, JsCallback callback) {
        JsonObject object = new JsonObject();
        int gender;
        switch (Repository.getLocalValue(LocalKey.USER_GENDER)) {
            // -1：未设定 0：女 1：男
            case "女":
                gender = 0;
                break;
            case "男":
                gender = 1;
                break;
            default:
                gender = -1;
                break;
        }
        object.addProperty("openId", Repository.getLocalValue(LocalKey.OPEN_ID));
        object.addProperty("introduction", "");
        object.addProperty("picUrl", Repository.getLocalValue(LocalKey.PICTURE_URL));
        object.addProperty("gender", gender);
        object.addProperty("shopId", Repository.getLocalValue(LocalKey.SHOP_ID));
        object.addProperty("openid", Repository.getLocalValue(LocalKey.OPEN_ID));
        object.addProperty("fullName", Repository.getLocalValue(LocalKey.REAL_NAME));
        object.addProperty("sessionid", Repository.getLocalMap(BaseLocalKey.X_TOKEN).get("x-auth-token"));
        object.addProperty("mobilePhone", Repository.getLocalValue(LocalKey.MOBILE_PHONE));
        object.addProperty("genderName", Repository.getLocalValue(LocalKey.USER_GENDER));
        object.addProperty("nickName", Repository.getLocalValue(LocalKey.NICK_NAME));
        callback.oldFormat(object);
    }

    /**
     * finish
     */
    public static void routerToNavtive(WebView webView, JsonObject params, JsCallback callback) {
        JsHandlerCommonImpl.finish(webView, params, callback);
    }

    /**
     * call_native
     */
    public static void call_native(WebView webView, JsonObject params, JsCallback callback) {
        String H5_KEY = "H5_KEY_";
        JsonPrimitive h5Key = params.getAsJsonPrimitive("h5Key");
        JsonPrimitive h5Value = params.getAsJsonPrimitive("h5Value");
        JsonPrimitive tag = params.getAsJsonPrimitive("tag");
        JsonObject result = new JsonObject();
        if (tag != null && !TextUtils.isEmpty(tag.getAsString())) {
            switch (tag.getAsString()) {
                case "1"://setUserDefaultKey
                    if (h5Key == null || TextUtils.isEmpty(h5Key.getAsString())) {
                        result.addProperty("h5Key", "noKey");
                    } else if (h5Value == null || TextUtils.isEmpty(h5Value.getAsString())) {
                        result.addProperty("h5Key", "noValue");
                    } else {
                        Repository.setLocalValue(H5_KEY + h5Key.getAsString(), h5Value.getAsString());
                        result.addProperty(h5Key.getAsString(), "ok");
                    }
                    callback.oldFormat(result);
                    break;
                case "2"://getUserDefaultKey
                    if (h5Key == null || TextUtils.isEmpty(h5Key.getAsString())) {
                        result.addProperty("h5Key", "noKey");
                    } else {
                        String value = Repository.getLocalValue(H5_KEY + h5Key.getAsString());
                        result.addProperty(h5Key.getAsString(), value);
                    }
                    callback.oldFormat(result);
                    break;
//                case "3"://scan
//                    JsHandlerCommonImpl.scan(webView, params, callback);
//                    break;
            }
        }
    }

    /**
     * 获取埋点信息
     */
    public static void getter(WebView webView, JsonObject params, JsCallback callback) {
        JsonObject object = new JsonObject();
        if (!ActivityUtil.isActivityDestroyed()) {
            object.addProperty("nativeRUrl", UserDataManager.mLastPauseActivityCLassName == null ?
                    ActivityUtil.getCurrentActivity().getLocalClassName() : UserDataManager.mLastPauseActivityCLassName);
        }
        object.addProperty("version", StatKey.checkValue(StatConstData.appVersionName));
        object.addProperty("appFrom", UserDataManager.getChannel());
        object.addProperty("hxiphoneUUID", UserDataManager.getDeviceUtdid());
//        if (LocationUtil.getLocation() != null) {
        object.addProperty("ShowCityName", "");
        object.addProperty("ShowCityId", "");
        object.addProperty("ShowProvince", "");
//        }
        object.addProperty(StatKey.Parameter.d_os_version, StatKey.checkValue(StatConstData.osVersion));
        object.addProperty(StatKey.Parameter.d_os, StatKey.OS_TYPE_ANDROID);
        object.addProperty(StatKey.Parameter.d_prixel_x, StatKey.checkValue(String.valueOf(DeviceUtil.getScreenWidth())));
        object.addProperty(StatKey.Parameter.d_prixel_y, StatKey.checkValue(String.valueOf(DeviceUtil.getScreenHeight())));
        object.addProperty(StatKey.Parameter.d_model, StatKey.checkValue(StatConstData.deviceName));
        object.addProperty(StatKey.Parameter.d_mark, StatKey.checkValue(StatConstData.deviceBrand));
        object.addProperty(StatKey.Parameter.u_mid, UserDataManager.getDeviceUtdid());
        object.addProperty(StatKey.Parameter.app_v, StatKey.checkValue(StatConstData.appVersionName));
        object.addProperty(StatKey.Parameter.l_country, StatKey.checkValue(UserDataManager.getCountry()));
        object.addProperty(StatKey.Parameter.l_province, StatKey.checkValue(UserDataManager.getProvince()));
        object.addProperty(StatKey.Parameter.l_city, StatKey.checkValue(UserDataManager.getCity()));
        object.addProperty(StatKey.Parameter.l_dist, StatKey.checkValue(UserDataManager.getDistrict()));
        object.addProperty(StatKey.Parameter.u_idfa, StatKey.checkValue(StatConstData.deviceImei));
        object.addProperty(StatKey.Parameter.l_gps, StatKey.checkValue(UserDataManager.getGps()));
        callback.success(object);
    }
}
