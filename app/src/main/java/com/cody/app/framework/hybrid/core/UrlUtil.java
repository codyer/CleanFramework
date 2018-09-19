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
                //文件服务器正式环境不走寻常路，不是.mmall.com，所有做特殊处理
                if (host.endsWith(Domain.HOST_SUFFIX) || host.endsWith(".mklimg.com")) {
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
