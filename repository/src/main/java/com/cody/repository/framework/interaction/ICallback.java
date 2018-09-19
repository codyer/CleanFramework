package com.cody.repository.framework.interaction;

import com.cody.xf.utils.http.SimpleBean;

/**
 * Created by cody.yi on 2016/8/5.
 * <p/>
 * 所有View和ViewModel，ViewModel和Presenter之间的回调使用这个Callback
 */
public interface ICallback<T> {

    // 操作执行前
    void onBegin(Object tag);

    //操作执行结束
    void onSuccess(T bean);

    //执行出错
    void onFailure(SimpleBean simpleBean);

    //执行出错并取消
    void onError(SimpleBean simpleBean);

    //执行进度
    void onProgress(int progress, int max);
}
