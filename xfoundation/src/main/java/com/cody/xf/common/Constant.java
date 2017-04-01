package com.cody.xf.common;


import com.cody.xf.FoundationApplication;

/**
 * Created by cody.yi on 2016/7/13.
 * 常量
 */
public class Constant {
    public final static String PKG_NAME = FoundationApplication.getContext().getPackageName();
    public static final boolean DEBUG = true;
    public static final boolean TOAST_DEBUG = true;

    /**
     * 运行模式 DEBUG = 1; INFO = 2; WARN = 3; ERROR = 4; 优先级DEBUG < INFO < WARN <
     * ERROR
     * RUNNING_MODE大于4时Log关闭
     */
    public static final int RUNNING_MODE = 1;

    /**
     * 请求地址相关
     */
    public interface HttpUrl {
        String SERVER = "http://192.168.226.50";
        String PORT = ":8080";
        String PREFIX = "/order_api";
        String API_TEST = SERVER + PORT + PREFIX + "/test";
    }

    /**
     * http请求返回码
     */
    public interface HttpCode {
        String REQUEST_ERROR = "41001";//请求出错
        String NETWORK_DISCONNECTED = "41002";//网络无连接
        String PARAMETER_ERROR = "41003";//参数错误
        String UN_LOGIN = "-401";           //未登录
        String SUCCESS = "200";           //请求成功  (后面统一规定)
        String SERVER_ERROR = "500";       //服务端运行异常
        String NOT_FOUND = "404";     //未找到数据/查询无信息/某某数据不存在
        String OTHER_ERROR = "4000";//其它错误类型
    }

}
