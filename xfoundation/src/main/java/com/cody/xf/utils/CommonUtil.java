package com.cody.xf.utils;

import android.os.Build;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by cody.yi on 2016/7/29.
 * 通用库
 * 1、获取类型 {@link #getType(Class, Type...)}
 * 2、拼接get请求的url地址 {@link #reBuildUrl(String, Map)}
 * 3、restful接口地址字段替换 {@link #reBuildRestFulUrl(String, Map)}
 * 4、下载文件是否可用{@link #isDownloadManagerAvailable}
 */
public class CommonUtil {

    /**
     * 获取组合类类型
     */
    public static ParameterizedType getType(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    /**
     * 拼接get请求的url地址
     * @param url 原地址
     * @param params 参数
     * @return 拼接完成的地址
     */
    public static String reBuildUrl(String url, Map<String, String> params) {
        if (params.size() > 0 && StringUtil.isNotEmpty(url)) {
            //restful
            if (url.contains("{")) {
                return reBuildRestFulUrl(url, params);
            }
            url += url.contains("?") ? "&" : "?";
            StringBuilder encodedParams = new StringBuilder();
            try {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    encodedParams.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    encodedParams.append('&');
                }
                url += encodedParams.toString();
                url = url.substring(0, url.length() - 1);
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException("Encoding not supported: " + "UTF-8", uee);
            }
        }
        return url;
    }

    /**
     * restful接口地址字段替换
     * @param url 包含大括号的地址
     * @param params 需要替换的参数
     * @return 真实的请求地址
     */
    public static String reBuildRestFulUrl(String url, Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url = url.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return url;
    }

    /**
     * @return true if the download manager is available
     */
    public static boolean isDownloadManagerAvailable() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }
}
