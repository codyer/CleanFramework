/*
 * Copyright (c)  Created by Cody.yi on 2016/9/1.
 */
package com.cody.xf.utils;

import com.cody.xf.FoundationApplication;

/**
 * Created by cody.yi on 2016/9/1.
 * 资源获取工具类
 */
public class ResourceUtil {
    /**
     * 根据ID获取字符串
     *
     * @param id 资源id
     * @return 资源id对应的字符串
     */
    public static String getString(int id) {
        return FoundationApplication.getContext().getString(id);
    }
}
