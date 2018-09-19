package com.cody.repository.framework.database;

import com.cody.xf.utils.CommonUtil;

/**
 * Created by cody.yi on 2018/7/20.
 * 数据库基类
 */
public abstract class BaseDatabase {
    private String middle;

    public BaseDatabase(String name) {
        middle = name;
    }

    protected String prefix() {
        return "r_";
    }

    public String middle() {
        return middle;
    }

    public String name() {
        return prefix() + middle() + suffix();
    }

    public byte[] key() {
        return CommonUtil.getKey(name());
    }

    public abstract String suffix();

    public abstract long version();
}
