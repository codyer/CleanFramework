package com.cody.app.framework.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cody.app.R;
import com.cody.app.BR;
import com.cody.handler.framework.viewmodel.BaseViewModel;
import com.cody.xf.binding.activity.BaseBindingActivity;
import com.cody.xf.binding.handler.Presenter;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.StringUtil;
import com.cody.xf.utils.ToastUtil;


/**
 * MVVM架构的基类，将ViewModel的属性和行为进行拆分，行为交由P处理，属性由VM持有
 *
 * @param <P>  处理逻辑的类，从ViewModel拆出来的行为
 * @param <VM> 所有ViewModel中原来的属性；
 * @param <B>  和V（XML）进行绑定的自动生成的类，可以通过data节点添加class自定义binding的类名
 */
public abstract class BaseActivity<P extends Presenter<VM>, VM extends BaseViewModel, B extends ViewDataBinding>
        extends BaseBindingActivity<P, VM, B> implements View.OnClickListener, DialogInterface.OnCancelListener {
    private ProgressDialog mLoading;
    private boolean isFirstVisible = true;

    /**
     * 可以做初始化一次的操作
     */
    protected void onFirstUserVisible() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoading = new ProgressDialog(this);
        mLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mLoading.setCanceledOnTouchOutside(false);
        mLoading.setCancelable(true);
        mLoading.setMessage(getString(R.string.r_load_more_text));
        mLoading.setOnCancelListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstVisible) {
            isFirstVisible = false;
            onFirstUserVisible();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @CallSuper
    @Override
    public void showLoading(String msg) {
        LogUtil.d(TAG, "BaseActivity ++ showLoading");
        if (StringUtil.isEmpty(msg)) {
            mLoading.setMessage(getString(R.string.r_load_more_text));
        } else {
            mLoading.setMessage(msg);
        }
        if (!mLoading.isShowing()) {
            mLoading.show();
        }
    }

    @CallSuper
    @Override
    public void hideLoading() {
        LogUtil.d(TAG, "BaseActivity ++ hideLoading");
        if (mLoading.isShowing()) {
            mLoading.dismiss();
        }
    }

    @CallSuper
    @Override
    public void showError(String msg) {
        this.hideLoading();
        LogUtil.d(TAG, "BaseActivity ++ showError");
    }

    @CallSuper
    @Override
    public void showException(String msg) {
        this.hideLoading();
        ToastUtil.showToast(msg);
        LogUtil.d(TAG, "BaseActivity ++ showException");
    }

    @CallSuper
    @Override
    public void showNetError() {
        this.hideLoading();
        ToastUtil.showToast(getString(R.string.r_no_network_connection_toast));
        LogUtil.d(TAG, "BaseActivity ++ showNetError");
    }

    @CallSuper
    @Override
    public void onProgress(long count, long current) {
        LogUtil.d(TAG, "BaseActivity ++ onProgress");
    }

    @CallSuper
    @Override
    public void onUpdate(Object... args) {
        this.hideLoading();
        getBinding().setVariable(BR.viewModel, getViewModel());
        LogUtil.d(TAG, "BaseActivity ++ onUpdate");
    }

    @CallSuper
    @Override
    public void onCancel(DialogInterface dialog) {
        this.hideLoading();
        LogUtil.d(TAG, "BaseActivity ++ onCancel");
        getPresenter().cancel(TAG);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        intent.putExtra("previous", getComponentName().getClassName());
        super.startActivity(intent);
    }
}
