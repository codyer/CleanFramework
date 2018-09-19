package com.cody.app.business.launch;


import android.os.Bundle;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.FragmentLauncherThirdBinding;
import com.cody.handler.business.viewmodel.LauncherViewModel;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.xf.utils.LogUtil;

/**
 * Created by cody.yi on 2017/9/4.
 * 启动动画 第三屏
 */
public class LauncherThirdFragment extends LauncherBaseFragment<Presenter<LauncherViewModel>, LauncherViewModel, FragmentLauncherThirdBinding> {

    public static LauncherThirdFragment getInstance(OnPageAnimatorListener onPageAnimatorListener) {
        LauncherThirdFragment fragment = new LauncherThirdFragment();
        fragment.setArgus(onPageAnimatorListener, 2);
        return fragment;
    }

    @Override
    public void startAnimation() {
        LogUtil.d(TAG, "startAnimation 1");
        if (!getBinding().thirdInfo1.isInAnimation()) {
            LogUtil.d(TAG, "startAnimation 2");
            getBinding().thirdInfo1.setTextViewAnimation("每天的记忆，");
        }
        if (!getBinding().thirdInfo2.isInAnimation()) {
            getBinding().thirdInfo2.setTextViewAnimation("都变得生动难忘。");
        }
        getBinding().thirdInfo1.setVisibility(View.VISIBLE);
        getBinding().thirdInfo2.setVisibility(View.VISIBLE);

        getBinding().thirdInfo1.postDelayed(new Runnable() {
            @Override
            public void run() {
                LauncherThirdFragment.super.startAnimation();
            }
        }, getBinding().thirdInfo1.getAnimationDuration() / 2);
    }

    @Override
    public void stopAnimation() {
        super.stopAnimation();
        LogUtil.d(TAG, "stopAnimation 1");
        getBinding().thirdInfo1.setVisibility(View.INVISIBLE);
        getBinding().thirdInfo2.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_launcher_third;
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
