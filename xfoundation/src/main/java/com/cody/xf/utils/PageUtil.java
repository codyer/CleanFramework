/*
 * Copyright (c)  Created by Cody.yi on 2016/8/31.
 */
package com.cody.xf.utils;

/**
 * Created by cody.yi on 2016/8/31.
 * 页码控制
 */
public class PageUtil {
    /**
     * 页码操作
     */
    public static final int FirstPage = 1;
    public static final int NextPage = 2;
    public static final int PageSize = 20;//默认页码大小

    public static int getType(int pageNo) {
        if (pageNo == 1)
            return FirstPage;
        return NextPage;
    }
}
