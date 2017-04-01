package com.cody.xf.binding;

import com.cody.xf.common.SimpleBean;

/**
 * Created by cody.yi on 2016/8/5.
 * <p/>
 * 所有View和ViewModel，ViewModel和Presenter之间的回调使用这个Callback
 */
public interface ICallback<T> {

    // 操作执行前
    public void onBegin(Object tag);

    //操作执行结束
    public void onSuccess(T bean);

    //执行出错
    public void onFailure(SimpleBean simpleBean);

    //执行出错并取消
    public void onError(SimpleBean simpleBean);

    //执行进度
    public void onProgress(long count, long current);
}
