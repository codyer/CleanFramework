package com.cody.app.framework.hybrid.handler;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.WebView;

import com.cody.app.R;
import com.cody.app.business.scan.ScanActivity;
import com.cody.app.framework.activity.HtmlActivity;
import com.cody.app.framework.hybrid.JsBridge;
import com.cody.app.framework.hybrid.core.JsCallback;
import com.cody.app.framework.hybrid.core.JsHandler;
import com.cody.xf.common.NotProguard;
import com.cody.xf.utils.ResourceUtil;
import com.cody.xf.utils.ToastUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Cody.yi on 16/4/19.
 * Js handler 实现类
 */
@NotProguard
public final class JsHandlerCommonImpl implements JsHandler {

    public static void showToast(WebView webView, JsonObject params, JsCallback callback) {
        JsonPrimitive msg = params.getAsJsonPrimitive("msg");
        if (msg != null) {
            ToastUtil.showToast(msg.getAsString());
        }
        callback.success(params);
    }

//    public static void getIMSI(WebView webView, JsonObject params, JsCallback callback) {
//        TelephonyManager telephonyManager = ((TelephonyManager) webView.getContext().getSystemService(Context
//                .TELEPHONY_SERVICE));
//        String imsi = telephonyManager.getSubscriberId();
//        if (TextUtils.isEmpty(imsi)) {
//            imsi = telephonyManager.getDeviceId();
//        }
//        JsonObject data = new JsonObject();
//        data.addProperty("imsi", imsi);
//        callback.success(data);
//    }
//
//    public static void getAppName(WebView webView, JsonObject params, JsCallback callback) {
//        String appName;
//        try {
//            PackageManager packageManager = webView.getContext().getApplicationContext().getPackageManager();
//            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(webView.getContext()
//                    .getApplicationContext().getPackageName(), 0);
//            appName = packageManager.getApplicationLabel(applicationInfo).toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            appName = "";
//        }
//        JsonObject data = new JsonObject();
//        data.addProperty("result", appName);
//        callback.success(data);
//    }
//
//    public static void getOsSdk(WebView webView, JsonObject params, JsCallback callback) {
//        JsonObject data = new JsonObject();
//        data.addProperty("os_sdk", Build.VERSION.SDK_INT);
//        callback.success(data);
//    }
//
//    public static void share(final WebView webView, JsonObject params, final JsCallback callback) {
//        String title = "This is title";
//        String description = "This is description";
//        String url = "https://mobile.umeng.com/";
//        String thumbUrl = "https://mobile.umeng.com/images/pic/home/social/img-1.png";
//        String type = "";
////        {
////            "url":"",//分享链接
////                "title":"",//分享标题
////                "desc":"",//副标题
////                "imgUrl":""//分享图片链接
////        }
//
//        JsonPrimitive urlElement = params.getAsJsonPrimitive("url");
//        if (urlElement != null)
//            url = urlElement.getAsString();
//        JsonPrimitive titleElement = params.getAsJsonPrimitive("title");
//        if (titleElement != null)
//            title = titleElement.getAsString();
//        JsonPrimitive descElement = params.getAsJsonPrimitive("desc");
//        if (descElement != null)
//            description = descElement.getAsString();
//        JsonPrimitive imgUrlElement = params.getAsJsonPrimitive("imgUrl");
//        if (imgUrlElement != null)
//            thumbUrl = imgUrlElement.getAsString();
//        JsonPrimitive typeElement = params.getAsJsonPrimitive("type");
//        if (typeElement != null)
//            type = typeElement.getAsString();
//        final ShareBean bean = new ShareBean();
//        bean.setUrl(url);
//        bean.setTitle(title);
//        bean.setDesc(description);
//        bean.setImgUrl(thumbUrl);
//        final String finalType = type;
//        ShareBlock.share((Activity) webView.getContext(), bean, new ShareBlock.ShareListener() {
//            @Override
//            public void onStart(SHARE_MEDIA var1) {
//
//            }
//
//            @Override
//            public void onResult(SHARE_MEDIA var1) {
//                if (finalType != null && LoginBlock.isLogin()) {
//                    /*PhotoBrowserDetailsPresenter presenter = new PhotoBrowserDetailsPresenter();
//                    presenter.shareAddStarCoin(((Activity) webView.getContext()).getClass().getSimpleName(), finalType);*/
//                }
//            }
//
//            @Override
//            public void onError(SHARE_MEDIA var1, Throwable var2) {
//
//            }
//
//            @Override
//            public void onCancel(SHARE_MEDIA var1) {
//
//            }
//        });
////
////        String[] permissions = new String[]{
////                Manifest.permission.WRITE_EXTERNAL_STORAGE,
////                Manifest.permission.ACCESS_FINE_LOCATION,
////                Manifest.permission.CALL_PHONE,
////                Manifest.permission.READ_PHONE_STATE};
////
////        if (webView.getContext() instanceof Activity) {
////            if (!EasyPermissions.hasPermissions(webView.getContext(), permissions)) {
////                JsBridge.requestPermissions(ResourceUtil.getString(R.string.permission_request),
////                        new EasyPermissions.PermissionCallbacks() {
////                            @Override
////                            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//// @NonNull int[] grantResults) {
////                                LogUtil.d("onRequestPermissionsResult" + requestCode);
////                            }
////
////                            @Override
////                            public void onPermissionsGranted(int requestCode, List<String> perms) {
////                                ShareBlock.share((Activity) webView.getContext(), bean,null);
////                            }
////
////                            @Override
////                            public void onPermissionsDenied(int requestCode, List<String> perms) {
////                                ToastUtil.showToast(R.string.some_permission_not_grant);
////                            }
////                        }, permissions);
////            } else {
////                ShareBlock.share((Activity) webView.getContext(), bean,null);
////            }
////        }
//    }

    public static void finish(WebView webView, JsonObject params, JsCallback callback) {
        if (webView.getContext() instanceof Activity) {
            ((Activity) webView.getContext()).finish();
        }
        callback.success("finish");
    }

    /**
     * Nativ scan
     */
    public static void scan(final WebView webView, JsonObject params, final JsCallback callback) {
        JsBridge.startActivityForResult(new Intent(webView.getContext(), ScanActivity.class), new
                JsBridge.OnActivityResultListener() {
                    @Override
                    public void onActivityResult(int resultCode, Intent intent) {
                        if (resultCode == RESULT_OK) {
                            String result = intent.getStringExtra(ScanActivity.SCAN_RESULT);
                            JsonObject data = new JsonObject();
                            data.addProperty("result", result);
                            callback.success(result, data);
                        } else {
                            callback.failure(ResourceUtil.getString(R.string.cancel));
                        }
                    }
                });
    }

    /**
     * 打开新的H5 页面
     */
    public static void openHtml(final WebView webView, JsonObject params, final JsCallback callback) {
        JsonPrimitive title = params.getAsJsonPrimitive("title");
        JsonPrimitive url = params.getAsJsonPrimitive("url");
        if (url == null || TextUtils.isEmpty(url.getAsString())) {
            callback.failure("跳转链接不能为空");
        } else {
            if (title == null || TextUtils.isEmpty(title.getAsString())) {
                HtmlActivity.startHtml(null, url.getAsString());
            } else {
                HtmlActivity.startHtml(title.getAsString(), url.getAsString());
            }
            callback.success("跳转成功");
        }
    }
}
