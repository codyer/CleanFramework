package com.cody.xf.binding;

import android.support.annotation.CallSuper;

import com.cody.xf.FoundationApplication;
import com.cody.xf.binding.handler.Presenter;
import com.cody.xf.common.Constant;
import com.cody.xf.common.SimpleBean;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.ToastUtil;

/**
 * Created by cody.yi on 2016/8/5.
 * <p/>
 * 所有View和ViewModel，ViewModel和Presenter之间的回调使用这个Callback
 */
public abstract class Callback<T> implements ICallback<T> {

    private IView mView;

    public Callback(Presenter presenter) {
        this.mView = presenter.getView();
    }

    // 操作执行前
    @CallSuper
    public void onBegin(Object tag) {
        LogUtil.i("Callback onBegin" + tag);
    }

    //操作执行结束
    @CallSuper
    public void onSuccess(T bean) {
        LogUtil.i("Callback onSuccess" + bean);
        if (!isViewRecycled()) {
            mView.hideLoading();
        }
    }

    //执行出错
    @CallSuper
    public void onFailure(SimpleBean simpleBean) {
        LogUtil.i("Callback onFailure" + simpleBean);
        if (!isViewRecycled()) {
            mView.hideLoading();
        }
        if (simpleBean != null && Constant.HttpCode.UN_LOGIN.equals(simpleBean.getCode())) {
            FoundationApplication.getInstance().logOut();
        }
        ToastUtil.showToast(simpleBean);
    }

    //执行出错并取消
    @CallSuper
    public void onError(SimpleBean simpleBean) {
        if (!isViewRecycled()) {
            mView.hideLoading();
        }
        LogUtil.i("Callback onError" + simpleBean);
        ToastUtil.showToast(simpleBean);
    }

    //执行进度
    @CallSuper
    public void onProgress(long count, long current) {
        LogUtil.i("Callback onProgress count=" + count + " current=" + current);
    }

    private boolean isViewRecycled() {
        return mView == null;
    }
}
