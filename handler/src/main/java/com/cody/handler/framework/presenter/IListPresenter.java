package com.cody.handler.framework.presenter;

import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by cody.yi on 2017/5/11.
 * 处理列表
 */
public interface IListPresenter {
    /**
     * 获取初始页数据
     *
     * @param tag Http请求标识
     */
    void getInitPage(Object tag);

    /**
     * 获取最新数据
     *
     * @param tag Http请求标识
     */
    void getRefreshPage(Object tag);

    /**
     * 获取下一页数据
     *
     * @param tag Http请求标识
     */
    void getNextPage(Object tag);

    /**
     * 获取列表
     *
     * @param tag    请求tag，用来取消请求
     * @param params 参数
     *               page:1
     *               pageSize:20
     */
    void getDefaultRecycleList(Object tag, @NonNull Map<String, String> params);

    void getRecycleList(Object tag, @NonNull Map<String, String> params);
}
