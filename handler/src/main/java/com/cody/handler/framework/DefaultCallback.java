package com.cody.handler.framework;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.support.annotation.StringRes;

import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.ResourceUtil;
import com.cody.xf.utils.ToastUtil;
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

    private static final int RESPONSE_UPDATE = 0x02;
    //主线程Handler
    private Handler mHandler;
    private Reference<IView> mViewRef;

    public DefaultCallback(Presenter presenter) {
        this.mViewRef = new WeakReference<>(presenter.getView());
    }

    public DefaultCallback(Presenter presenter, boolean progress) {
        this.mViewRef = new WeakReference<>(presenter.getView());
        if (progress) {
            mHandler = new UIHandler(Looper.getMainLooper());
        }
    }

    protected
    @StringRes
    int loadingMsg() {
        return -1;
    }

    // 操作执行前
    @Override
    public void onBegin(Object tag) {
        LogUtil.d("Callback onBegin" + tag);
        showLoading();
    }

    //操作执行结束
    @CallSuper
    @Override
    public void onSuccess(T bean) {
        LogUtil.d("Callback onSuccess" + bean);
    }

    //执行出错
    @CallSuper
    @Override
    public void onFailure(SimpleBean simpleBean) {
        LogUtil.d("Callback onFailure" + simpleBean);
        ToastUtil.showSimpleBean(simpleBean);
        if (!isViewRecycled() && simpleBean != null) {
            mViewRef.get().showFailure(simpleBean);
        } else {
            hideLoading();
        }
    }

    //执行出错并取消
    @CallSuper
    @Override
    public void onError(SimpleBean simpleBean) {
        LogUtil.d("Callback showError" + simpleBean);
        ToastUtil.showSimpleBean(simpleBean);
        if (!isViewRecycled() && simpleBean != null) {
            mViewRef.get().showError(simpleBean);
        } else {
            hideLoading();
        }
    }

    //执行进度
    @CallSuper
    @Override
    public void onProgress(int progress, int max) {
        LogUtil.d("Callback onProgress max=" + max + " progress=" + progress);
        if (!isViewRecycled() && mHandler != null) {
            //通过Handler发送进度消息
            Message message = Message.obtain();
            message.what = RESPONSE_UPDATE;
            message.arg1 = progress;
            message.arg2 = max;
            mHandler.sendMessage(message);
        }
    }

    protected void showLoading() {
        if (!isViewRecycled()) {
            mViewRef.get().showLoading(loadingMsg() > 0 ? ResourceUtil.getString(loadingMsg()) : null);
        }
    }

    protected void hideLoading() {
        if (!isViewRecycled()) {
            mViewRef.get().hideLoading();
            if (mHandler != null){
                mHandler = null;
            }
        }
    }

    protected boolean isViewRecycled() {
        return mViewRef == null || mViewRef.get() == null;
    }

    //处理UI层的Handler子类
    private class UIHandler extends Handler {

        UIHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RESPONSE_UPDATE:
                    IView callback = mViewRef.get();
                    if (callback != null) {
                        int progress = msg.arg1;
                        int max = msg.arg2;
                        callback.onProgress(progress, max);
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
}
