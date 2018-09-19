package com.cody.app.business.launch;


import android.os.Bundle;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.FragmentLauncherSecondBinding;
import com.cody.handler.business.viewmodel.LauncherViewModel;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.xf.utils.LogUtil;

/**
 * Created by cody.yi on 2017/9/4.
 * 启动动画 第二屏
 */
public class LauncherSecondFragment extends LauncherBaseFragment<Presenter<LauncherViewModel>, LauncherViewModel, FragmentLauncherSecondBinding> {

    public static LauncherSecondFragment getInstance(OnPageAnimatorListener onPageAnimatorListener) {
        LauncherSecondFragment fragment = new LauncherSecondFragment();
        fragment.setArgus(onPageAnimatorListener, 1);
        return fragment;
    }

    @Override
    public void startAnimation() {
        LogUtil.d(TAG,"startAnimation 1");
        if (!getBinding().secondInfo1.isInAnimation()) {
            LogUtil.d(TAG,"startAnimation 2");
            getBinding().secondInfo1.setTextViewAnimation("发现每个人的精彩，");
        }
        if (!getBinding().secondInfo2.isInAnimation()) {
            getBinding().secondInfo2.setTextViewAnimation("分享自己眼中的世界。");
        }
        getBinding().secondInfo1.setVisibility(View.VISIBLE);
        getBinding().secondInfo2.setVisibility(View.VISIBLE);

        getBinding().secondInfo1.postDelayed(new Runnable() {
            @Override
            public void run() {
                LauncherSecondFragment.super.startAnimation();
            }
        }, getBinding().secondInfo1.getAnimationDuration() / 2);
    }

    @Override
    public void stopAnimation() {
        super.stopAnimation();
        LogUtil.d(TAG,"stopAnimation 1");
        getBinding().secondInfo1.setVisibility(View.INVISIBLE);
        getBinding().secondInfo2.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_launcher_second;
    }

    @Override
    protected Presenter<LauncherViewModel> buildPresenter() {
        return new Presenter<>();
    }

    @Override
    protected LauncherViewModel buildViewModel(Bundle savedInstanceState) {
        return new LauncherViewModel();
    }

    @Override
    public void onClick(View v) {

    }
}
