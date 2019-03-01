package com.cody.app.framework.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.baidu.mobstat.StatService;
import com.cody.app.R;
import com.cody.handler.framework.IView;
import com.cody.repository.framework.statistics.HxStat;
import com.cody.xf.XFoundation;
import com.cody.xf.common.Constant;
import com.cody.xf.common.LoginBroadcastReceiver;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.DeviceUtil;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.ResourceUtil;
import com.cody.xf.utils.SystemBarUtil;
import com.cody.xf.utils.http.SimpleBean;
import com.cody.xf.widget.swipebacklayout.BGASwipeBackHelper;
import com.umeng.socialize.UMShareAPI;

import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadger;
import pub.devrel.easypermissions.EasyPermissions;


public abstract class BaseActivity extends AppCompatActivity implements IView,
        DialogInterface.OnCancelListener,
        BGASwipeBackHelper.Delegate,
        EasyPermissions.PermissionCallbacks {

    private final static float DISTANCE = DeviceUtil.dip2px(XFoundation.getContext(), 10);
    private final static int ANIMATION_IN[] = new int[]{
            R.anim.fw_fade_in,
            R.anim.fw_left_in,
            R.anim.fw_right_in,
    };
    private final static int ANIMATION_OUT[] = new int[]{
            R.anim.fw_fade_out,
            R.anim.fw_right_out,
            R.anim.fw_left_out,
    };
    /**
     * Log tag
     */
    protected String TAG = null;
    protected BGASwipeBackHelper mSwipeBackHelper;
    private ProgressDialog mLoading;
    private ProgressDialog mProgressDialog;
    private boolean mIsFirstVisible = true;
    /*是否正在切换Fragment*/
    private boolean mIsSwitchFragmenting = false;
    private boolean mIsMoving = false;
    private float mDownX;
    private float mDownY;

    /**
     * 可以做初始化一次的操作
     */
    protected void onFirstUserVisible() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        ActivityUtil.setCurrentActivity(this);
        initLoading();
    }

    /**
     * 状态栏或者沉浸式
     * 有特殊要求的全屏需要重载这个方法
     */
    protected void onImmersiveReady() {
        if (!SystemBarUtil.isFlyme4Later()) {
            SystemBarUtil.tintWhiteStatusBar(this, true);
        }

        View topLayout = findViewById(android.R.id.content);
        if (topLayout != null) {
            SystemBarUtil.setStatusBarDarkMode(this, true);
            SystemBarUtil.tintStatusBar(this, Color.TRANSPARENT);
            SystemBarUtil.immersiveStatusBar(this, 0.0f);
            SystemBarUtil.setPadding(this, topLayout);
        }
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        if (mSwipeBackHelper != null) {
            mSwipeBackHelper.swipeBackward();
        }
    }

    @Override
    public void onBackPressed() {
        if (mSwipeBackHelper != null) {
            // 正在滑动返回的时候取消返回按钮事件
            if (mSwipeBackHelper.isSliding()) {
                return;
            }
            mSwipeBackHelper.backward();
        }
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.xf_swipeback_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    private void initLoading() {
        if (mLoading == null) {
            mLoading = new ProgressDialog(this);
            mLoading.setCanceledOnTouchOutside(false);
            mLoading.setCancelable(true);
            mLoading.setMessage(getString(R.string.fw_html_loading));
            mLoading.setOnCancelListener(this);
        }
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle(ResourceUtil.getString(R.string.fw_html_loading));
            mProgressDialog.setProgress(0);
            mProgressDialog.setProgressNumberFormat(null);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setOnCancelListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        HxStat.onResume(this);
        StatService.onPageStart(this, TAG);
        ActivityUtil.setCurrentActivity(this);
        if (mIsFirstVisible) {
            mIsFirstVisible = false;
            onFirstUserVisible();
        }
        //移除角标
        ShortcutBadger.removeCount(getApplicationContext()); //for 1.1.4+
    }

    @Override
    protected void onPause() {
        super.onPause();
        HxStat.onPause(this);
        StatService.onPageEnd(this, TAG);
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        UMShareAPI.get(this).release();
        mSwipeBackHelper = null;
        mLoading = null;
        mProgressDialog = null;
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.LOGIN_REQUEST_CODE) {
            LoginBroadcastReceiver.sentEvent(resultCode == RESULT_OK);
        }
    }

    @CallSuper
    @Override
    public void showLoading(String msg) {
        LogUtil.d(TAG, "BaseActivity ++ showLoading");
        if (null == mLoading) return;
        if (TextUtils.isEmpty(msg)) {
            mLoading.setMessage(getString(R.string.fw_html_loading));
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
        if (null == mLoading) return;
        if (mLoading.isShowing()) {
            mLoading.dismiss();
        }
        if (null == mProgressDialog) return;
        if (mProgressDialog.isShowing()) {
            mProgressDialog.setProgress(0);
            mProgressDialog.dismiss();
        }
    }

    @CallSuper
    @Override
    public void showFailure(SimpleBean msg) {
        this.hideLoading();
        LogUtil.d(TAG, "BaseFragment ++ showFailure msg = " + msg);
    }

    @CallSuper
    @Override
    public void showError(SimpleBean msg) {
        this.hideLoading();
        LogUtil.d(TAG, "BaseFragment ++ showError msg = " + msg);
    }

    @CallSuper
    @Override
    public void onProgress(int progress, int max) {
        LogUtil.d(TAG, "BaseActivity ++ onProgress");
        if (null == mProgressDialog) return;
        mProgressDialog.setMax(max);
        mProgressDialog.setProgress(progress);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
        if (progress == max && mProgressDialog.isShowing()) {
            mProgressDialog.setProgress(0);
            mProgressDialog.dismiss();
        }
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
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mIsMoving = false;
                mDownX = ev.getX();
                mDownY = ev.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float moveX = Math.abs(ev.getX() - mDownX);//X轴距离
                float moveY = Math.abs(ev.getY() - mDownY);//y轴距离
                mIsMoving = (moveX > DISTANCE || moveY > DISTANCE);
                break;
            }
            case MotionEvent.ACTION_UP: {
                View v = getCurrentFocus();
                if (!mIsMoving && isShouldHideKeyboard(v, ev)) {
                    hideKeyboard(v.getWindowToken());
                }
                break;
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

    /**
     * 替换Fragment
     *
     * @param resLayId       container id
     * @param fragment       fragment
     * @param isAddBackStack 是否加入返回栈
     * @param animation      切换动画
     */
    public void replaceFragment(int resLayId, Fragment fragment, AnimationEnum animation,
                                boolean isAddBackStack) {
        if (mIsSwitchFragmenting) {
            return;
        }
        mIsSwitchFragmenting = true;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (animation.getValue() != AnimationEnum.WITHOUT.getValue()) {
            fragmentTransaction.setCustomAnimations(ANIMATION_IN[animation.getValue()],
                    ANIMATION_OUT[animation.getValue()], ANIMATION_IN[animation.getValue()],
                    ANIMATION_OUT[animation.getValue()]);
        }
        fragmentTransaction.replace(resLayId, fragment);
        if (isAddBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        mIsSwitchFragmenting = false;
    }

    /**
     * 添加Fragment
     *
     * @param resLayId       container id
     * @param showFragment   fragment
     * @param animation      animation
     * @param isAddBackStack stack
     * @param hideFragments  要隐藏的Fragment数组
     */
    public void addFragment(int resLayId, Fragment showFragment,
                            AnimationEnum animation, boolean isAddBackStack,
                            Fragment... hideFragments) {
        if (mIsSwitchFragmenting) {
            return;
        }
        mIsSwitchFragmenting = true;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (animation.getValue() != AnimationEnum.WITHOUT.getValue()) {
            fragmentTransaction.setCustomAnimations(ANIMATION_IN[animation.getValue()],
                    ANIMATION_OUT[animation.getValue()], ANIMATION_IN[animation.getValue()],
                    ANIMATION_OUT[animation.getValue()]);
        }
        if (hideFragments != null) {
            for (Fragment hideFragment : hideFragments) {
                if (hideFragment != null && hideFragment != showFragment)
                    fragmentTransaction.hide(hideFragment);
            }
        }
        fragmentTransaction.add(resLayId, showFragment);
        if (isAddBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        mIsSwitchFragmenting = false;
    }

    /**
     * 显示隐藏Fragment
     *
     * @param showFragment   显示的fragment
     * @param hideFragments  要隐藏的Fragment数组
     * @param isAddBackStack 是否加入返回栈
     */
    public void showHideFragment(Fragment showFragment, AnimationEnum animation,
                                 boolean isAddBackStack, Fragment... hideFragments) {
        if (mIsSwitchFragmenting) {
            return;
        }
        mIsSwitchFragmenting = true;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (animation.getValue() != AnimationEnum.WITHOUT.getValue()) {
            fragmentTransaction.setCustomAnimations(ANIMATION_IN[animation.getValue()],
                    ANIMATION_OUT[animation.getValue()], ANIMATION_IN[animation.getValue()],
                    ANIMATION_OUT[animation.getValue()]);
        }
        if (hideFragments != null) {
            for (Fragment hideFragment : hideFragments) {
                if (hideFragment != null && hideFragment.isAdded() && hideFragment != showFragment)
                    fragmentTransaction.hide(hideFragment);
            }
        }
        if (showFragment != null) {
            fragmentTransaction.show(showFragment);
        }
        if (isAddBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        mIsSwitchFragmenting = false;
    }

    public enum AnimationEnum {
        WITHOUT(-1),
        DEFAULT_TYPE(0),
        LEFT_IN(1),
        RIGHT_IN(2);
        private int mValue;

        AnimationEnum(int value) {
            mValue = value;
        }

        public int getValue() {
            return mValue;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    /**
     * 设置能否左滑关闭页面
     *
     * @param enable
     */
    public void setEnableBackLayout(boolean enable) {
        if (mSwipeBackHelper != null) {
            mSwipeBackHelper.setSwipeBackEnable(enable);
        }
    }
}
