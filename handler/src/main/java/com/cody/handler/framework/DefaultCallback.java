package com.cody.handler.framework;

import android.support.annotation.CallSuper;

import com.cody.xf.FoundationApplication;
import com.cody.xf.binding.ICallback;
import com.cody.xf.binding.IView;
import com.cody.xf.binding.handler.Presenter;
import com.cody.xf.common.Constant;
import com.cody.xf.common.SimpleBean;
import com.cody.xf.utils.LogUtil;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by cody.yi on 2016/8/5.
 * <p>
 * 所有View和ViewModel，ViewModel和Presenter之间的回调使用这个Callback
 * 默认回调函数，通用处理可以放在这里
 */
public class DefaultCallback<T> implements ICallback<T> {

    private Reference<IView> mViewRef;

    public DefaultCallback(Presenter presenter) {
        this.mViewRef = new WeakReference<>(presenter.getView());
    }

    // 操作执行前
    @CallSuper
    public void onBegin(Object tag) {
        LogUtil.d("Callback onBegin" + tag);
    }

    //操作执行结束
    @CallSuper
    public void onSuccess(T bean) {
        LogUtil.d("Callback onSuccess" + bean);
        if (!isViewRecycled()) {
            mViewRef.get().hideLoading();
        }
    }

    //执行出错
    @CallSuper
    public void onFailure(SimpleBean simpleBean) {
        LogUtil.d("Callback onFailure" + simpleBean);
        // 未登录统一处理
        if (simpleBean != null && Constant.HttpCode.UN_LOGIN.equals(simpleBean.getCode())) {
            FoundationApplication.getInstance().logOut();
        } else if (!isViewRecycled() && simpleBean != null) {
            mViewRef.get().showFailure(simpleBean.getMessage());
        }
    }

    //执行出错并取消
    @CallSuper
    public void onError(SimpleBean simpleBean) {
        if (!isViewRecycled() && simpleBean != null) {
            mViewRef.get().showError(simpleBean.toString());
        }
        LogUtil.d("Callback showError" + simpleBean);
    }

    //执行进度
    @CallSuper
    public void onProgress(long count, long current) {
        LogUtil.d("Callback onProgress count=" + count + " current=" + current);
    }

    private boolean isViewRecycled() {
        return mViewRef == null || mViewRef.get() == null;
    }
}
