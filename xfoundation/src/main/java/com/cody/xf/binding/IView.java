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
     * 弹出消息 一般onFailure调用，需要用户知道的错误
     */
    void showFailure(String msg);

    /**
     * 出错，一般只记录，只打log，用户不用关心的错误
     */
    void showError(String msg);

    /**
     * show Progress
     */
    void onProgress(long count, long current);
}
