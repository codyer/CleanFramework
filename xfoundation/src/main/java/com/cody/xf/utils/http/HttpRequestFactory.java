package com.cody.xf.utils.http;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.cody.xf.XFoundation;
import com.cody.xf.utils.ImageUtil;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.StringUtil;
import com.google.gson.JsonObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by cody.yi on 2016/7/13.
 * http 请求封装，需要优化
 */
public class HttpRequestFactory {

    private static HttpRequestFactory sHttpClient = null;
    private final RequestQueue mRequestQueue;

    private HttpRequestFactory(Context context) {
        mRequestQueue = Volley.newRequestQueue(context, new OkHttpStack(new OkHttpClient()));
    }

    /**
     * 单例模式（静态内部类）
     *
     * @return HttpManager instance
     */
    public static synchronized HttpRequestFactory getInstance() {
        if (sHttpClient == null) {
            sHttpClient = new HttpRequestFactory(XFoundation.getContext());
        }
        return sHttpClient;
    }

    private <T> Request<T> add(Object tag, Request<T> request) {
        request.setTag(tag);
        return mRequestQueue.add(request);//添加请求到队列
    }

    /**
     * 包含全部参数
     *
     * @param tag           页面tag
     * @param method        方法
     * @param url           地址
     * @param params        参数
     * @param jsonParams    json格式参数
     * @param type          响应数据类型
     * @param listener      成功监听器
     * @param errorListener 错误监听器
     * @param <T>           返回类类型
     */
    public <T> Request<T> createNormalRequest(Object tag,
                                              int method,
                                              @NonNull String url,
                                              Map<String, String> headers,
                                              Map<String, String> params,
                                              JsonObject jsonParams,
                                              Type type,
                                              HeaderListener headerListener,
                                              @NonNull Response.Listener<T> listener,
                                              Response.ErrorListener errorListener) {
        return add(tag, new BaseRequest<>(method, url, headers, params, jsonParams, type,
                listener, errorListener, headerListener));
    }

    /**
     * Put：上传base64图片
     *
     * @param tag           页面tag
     * @param url           请求地址
     * @param listener      成功的回调函数
     * @param errorListener 失败回调函数
     */
    public <T> Request<T> createUploadBase64ImageRequest(Object tag,
                                                         String url,
                                                         String imageName, Bitmap bitmap,
                                                         Map<String, String> headers,
                                                         Map<String, String> params, Type type,
                                                         Response.Listener<T> listener,
                                                         Response.ErrorListener errorListener) {
        return add(tag, new UploadBase64ImageRequest<>(url, imageName, bitmap, headers, params, type, listener,
                errorListener));
    }

    /**
     * Put：multipart图片
     * 支持多图上传
     *
     * @param tag           页面tag
     * @param url           请求地址
     * @param bitmapList    需要上传的图片列表
     * @param listener      成功的回调函数
     * @param errorListener 失败回调函数
     */
    public <T> Request<T> createMultipartRequest(Object tag,
                                                 String url,
                                                 String name,
                                                 List<Bitmap> bitmapList,
                                                 Map<String, String> headers,
                                                 Map<String, String> params,
                                                 Type type,
                                                 Response.Listener<T> listener,
                                                 Response.ErrorListener errorListener) {
        Map<String, DataPart> byteData = new HashMap<>();
        for (int i = 0; i < bitmapList.size(); i++) {
            String fileName = new Date(System.currentTimeMillis()).getTime() + "_" + i + ".jpg";
            byteData.put(fileName, new DataPart(name, fileName, ImageUtil.getFileDataFromBitmap(bitmapList.get(i)),
                    "image/jpeg"));
        }
        MultipartRequest<T> multipartRequest = new MultipartRequest<>(url, headers, params, byteData, type, listener, errorListener);
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 1, 1.0f));
        return add(tag, multipartRequest);
    }

    /**
     * createImageRequest
     */
    public Request createImageRequest(Object tag,
                                      String url,
                                      int maxWidth,
                                      int maxHeight,
                                      ImageView.ScaleType scaleType,
                                      Bitmap.Config decodeConfig,
                                      Response.Listener<Bitmap> listener,
                                      Response.ErrorListener errorListener) {
        return add(tag, new ImageRequest(url, listener, maxWidth, maxHeight, scaleType,
                decodeConfig, errorListener));
    }

    /**
     * 使用系统的下载管理器下载文件
     *
     * @param tag         tag
     * @param url         文件的下载地址
     * @param description 下载过程显示的描述
     * @param title       下载上显示的标题
     * @param path        下载后存放的路径
     * @param fileName    下载后文件的名字，需要带文件格式后缀,如：filename.txt
     */
    public long createDownloadRequest(Object tag,
                                      @NonNull String url,
                                      @NonNull String description,
                                      @NonNull String title,
                                      String path,
                                      @NonNull String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription(description);
        request.setTitle(title);
        // in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        if (StringUtil.isEmpty(path)) path = Environment.DIRECTORY_DOWNLOADS;

        File path1 = new File(path);
        if (!path1.exists()) {
            // 若不存在，创建目录，可以在应用启动的时候创建
            boolean result = path1.mkdirs();
            if (!result) {
                LogUtil.e(tag + "下载文件：" + fileName + "，创建目录失败！");
            }
        }
        request.setDestinationInExternalPublicDir(path, fileName);
        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) XFoundation.getContext().getSystemService(Context
                .DOWNLOAD_SERVICE);
        return manager.enqueue(request);
    }

    /**
     * 取消请求 酌情在Activity或其他组件的onStop中调用
     */
    public void cancel(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
}
