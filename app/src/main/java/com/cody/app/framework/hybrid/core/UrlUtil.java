package com.cody.app.framework.hybrid.core;

import android.net.Uri;
import android.text.TextUtils;

import com.cody.repository.Domain;

/**
 * Created by chen.huarong on 2018/3/13.
 * url工具类
 */
public class UrlUtil {

    /**
     * 是否是内部链接
     */
    public static boolean isInnerLink(String url) {
        try {
            if (!TextUtils.isEmpty(url)) {
                String host = Uri.parse(url).getHost();
                if (host != null && host.endsWith(Domain.HOST_SUFFIX)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
