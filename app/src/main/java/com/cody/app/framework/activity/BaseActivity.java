package com.cody.app.framework.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cody.app.R;
import com.cody.handler.framework.IView;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.StringUtil;
import com.cody.xf.utils.ToastUtil;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener,
        DialogInterface.OnCancelListener , IView {
    /**
     * Log tag
     */
    protected String TAG = null;
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
        TAG = this.getClass().getSimpleName();

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
    public void showFailure(String msg) {
        this.hideLoading();
        ToastUtil.showToast(msg);
        LogUtil.d(TAG, "BaseFragment ++ showFailure msg = " + msg);
    }

    @CallSuper
    @Override
    public void showError(String msg) {
        this.hideLoading();
        LogUtil.d(TAG, "BaseFragment ++ showError msg = " + msg);
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
        LogUtil.d(TAG, "BaseActivity ++ onUpdate");
    }

    @CallSuper
    @Override
    public void onCancel(DialogInterface dialog) {
        this.hideLoading();
        LogUtil.d(TAG, "BaseActivity ++ onCancel");
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
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
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
