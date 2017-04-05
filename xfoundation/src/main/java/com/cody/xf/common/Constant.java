package com.cody.xf.common;

/**
 * Created by cody.yi on 2016/7/13.
 * 常量
 */
public class Constant {
    public static final boolean DEBUG = true;

    /**
     * 运行模式 DEBUG = 1; INFO = 2; WARN = 3; ERROR = 4; 优先级DEBUG < INFO < WARN <
     * ERROR
     * RUNNING_MODE大于4时Log关闭
     */
    public static final int RUNNING_MODE = 1;
}
