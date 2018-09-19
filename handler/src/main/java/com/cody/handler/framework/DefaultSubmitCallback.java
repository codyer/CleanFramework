package com.cody.handler.framework;

import com.cody.handler.R;
import com.cody.handler.framework.presenter.Presenter;

/**
 * Created by cody.yi on 2016/8/5.
 * <p>
 * 所有View和ViewModel，ViewModel和Presenter之间的回调使用这个Callback
 * 默认回调函数，通用处理可以放在这里
 */
public abstract class DefaultSubmitCallback<T> extends DefaultCallback<T> {

    public DefaultSubmitCallback(Presenter presenter) {
        super(presenter);
    }

    @Override
    protected int loadingMsg() {
        return R.string.h_submit_loading;
    }
}
