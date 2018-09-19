package com.cody.handler.framework;

import com.cody.handler.framework.presenter.Presenter;

/**
 * Created by cody.yi on 2016/8/5.
 * <p>
 * 所有View和ViewModel，ViewModel和Presenter之间的回调使用这个Callback
 * 默认回调函数，通用处理可以放在这里
 * <p>
 * 下拉刷新处理显示加载中提示特殊处理
 */
public class RefreshCallback<T> extends DefaultCallback<T> {

    public RefreshCallback(Presenter presenter) {
        super(presenter);
    }

    @Override
    protected void showLoading() {
        // do nothing
        // 下拉刷新有自己的数据加载中的提示，需要去掉通用的加载中提示
    }
}
