package com.cody.xf.binding;

/**
 * @author codyer .
 *         base activity and base fragment 需要实现的接口
 *         16/8/22.
 */
public interface IView {

    /**
     * update view 不要做耗时操作
     */
    void onUpdate(Object... args);

    /**
     * show loading message
     *
     * @param msg 需要显示的消息：正在加载。。。
     */
    void showLoading(String msg);

    /**
     * hide loading
     */
    void hideLoading();

    /**
     * show error message
     */
    void showError(String msg);

    /**
     * show exception message
     */
    void showException(String msg);

    /**
     * show net error
     */
    void showNetError();

    /**
     * show Progress
     */
    void onProgress(long count, long current);
}
