package com.cody.handler.framework;

import android.support.annotation.CallSuper;

import com.cody.handler.business.presenter.AppPresenter;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.http.HttpCode;
import com.cody.xf.utils.http.SimpleBean;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by cody.yi on 2016/8/5.
 * <p>
 * 所有View和ViewModel，ViewModel和Presenter之间的回调使用这个Callback
 * 默认回调函数，通用处理可以放在这里
 */
public abstract class DefaultCallback<T> implements ICallback<T> {

    protected Reference<IView> mViewRef;

    public DefaultCallback(Presenter presenter) {
        this.mViewRef = new WeakReference<>(presenter.getView());
    }

    // 操作执行前
    @CallSuper
    public void onBegin(Object tag) {
        LogUtil.d("Callback onBegin" + tag);
        showLoading();
    }

    //操作执行结束
    @CallSuper
    public void onSuccess(T bean) {
        LogUtil.d("Callback onSuccess" + bean);
    }

    //执行出错
    @CallSuper
    public void onFailure(SimpleBean simpleBean) {
        LogUtil.d("Callback onFailure" + simpleBean);
        // 未登录统一处理
        if (simpleBean != null && HttpCode.UN_LOGIN.equals(simpleBean.getCode())) {
            AppPresenter.getInstance().logOut();
        } else if (!isViewRecycled() && simpleBean != null) {
            mViewRef.get().showFailure(simpleBean.getMessage());
        }
    }

    //执行出错并取消
    @CallSuper
    public void onError(SimpleBean simpleBean) {
        LogUtil.d("Callback showError" + simpleBean);
        if (!isViewRecycled() && simpleBean != null) {
            mViewRef.get().showError(simpleBean.toString());
        }
    }

    //执行进度
    @CallSuper
    public void onProgress(long count, long current) {
        LogUtil.d("Callback onProgress count=" + count + " current=" + current);
    }

    protected void showLoading() {
        if (!isViewRecycled()) {
            mViewRef.get().showLoading(null);
        }
    }

    protected boolean isViewRecycled() {
        return mViewRef == null || mViewRef.get() == null;
    }
}
