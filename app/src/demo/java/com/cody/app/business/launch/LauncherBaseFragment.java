package com.cody.app.business.launch;

import android.databinding.ViewDataBinding;

import com.cody.app.framework.fragment.BaseBindingFragment;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.handler.framework.viewmodel.IViewModel;

/**
 * Created by cody.yi on 2017/9/4.
 * 启动动画基类
 */
public abstract class LauncherBaseFragment<P extends Presenter<VM>,
        VM extends IViewModel, B extends ViewDataBinding>
        extends BaseBindingFragment<P, VM, B> {
    private OnPageAnimatorListener mOnPageAnimatorListener;
    private int mIndex;

    public LauncherBaseFragment() {
    }

    public void setArgus(OnPageAnimatorListener onPageAnimatorListener, int index) {
        mOnPageAnimatorListener = onPageAnimatorListener;
        mIndex = index;
    }

    public void startAnimation() {
        if (mOnPageAnimatorListener != null) {
            mOnPageAnimatorListener.startAnimation(mIndex);
        }
    }

    public void stopAnimation() {
        if (mOnPageAnimatorListener != null) {
            mOnPageAnimatorListener.stopAnimation(mIndex);
        }
    }
}
