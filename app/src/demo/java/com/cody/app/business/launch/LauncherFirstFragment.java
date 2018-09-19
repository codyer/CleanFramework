package com.cody.app.business.launch;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.cody.app.R;
import com.cody.app.databinding.FragmentLauncherFirstBinding;
import com.cody.handler.business.viewmodel.LauncherViewModel;
import com.cody.handler.framework.presenter.Presenter;

/**
 * Created by cody.yi on 2017/9/4.
 * 启动动画 第一屏
 */
public class LauncherFirstFragment extends LauncherBaseFragment<Presenter<LauncherViewModel>, LauncherViewModel, FragmentLauncherFirstBinding> {
    private ScaleAnimation mScaleAnim;
    private static int margin = 10;

    public static LauncherFirstFragment getInstance(OnPageAnimatorListener onPageAnimatorListener) {
        LauncherFirstFragment fragment = new LauncherFirstFragment();
        fragment.setArgus(onPageAnimatorListener, 0);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScaleAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f);
        mScaleAnim.setDuration(2500);
        mScaleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mScaleAnim.setFillAfter(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBinding().firstInfo1.setAnimationDuration(1000);
        getBinding().firstInfo2.setAnimationDuration(1000);
        getBinding().getRoot().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //此处可以正常获取View的宽高和View的边框与父View边框的距离
                getBinding().getRoot().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                float endX = getBinding().animatorW.getX();
                float endY = getBinding().animatorLine1.getY() + 2 * margin;

                getBinding().animatorW.addLineToY(endY * 3 / 2).addLineToY(endY);
                endX += (getBinding().animatorW.getWidth() + getBinding().animatorE.getWidth()) / 4 + margin;
                getBinding().animatorE.addLineToX(endX).addLineToY(endY);
                endX += (getBinding().animatorE.getWidth() + getBinding().animatorL.getWidth()) / 4 + margin;
                getBinding().animatorL.addLineToY(endY).addLineToX(endX);
                endX += (getBinding().animatorL.getWidth() + getBinding().animatorC.getWidth()) / 4 + margin;
                getBinding().animatorC.addLineToX(endX).addLineToY(endY);
                endX += (getBinding().animatorC.getWidth() + getBinding().animatorO.getWidth()) / 4 + margin;
                getBinding().animatorO.addLineToX(endX).addLineToY(endY);
                endX += (getBinding().animatorO.getWidth() + getBinding().animatorM.getWidth()) / 4 + margin;
                getBinding().animatorM.addLineToX(endX).addLineToY(endY);
                endX += (getBinding().animatorM.getWidth() + getBinding().animatorE2.getWidth()) / 4 + margin;
                getBinding().animatorE2.addLineToY(endY).addLineToX(endX);
                startAnimation();
            }
        });
    }

    @Override
    public void startAnimation() {
        getBinding().animatorW.startAnimation();
        getBinding().animatorE.startAnimation();
        getBinding().animatorL.startAnimation();
        getBinding().animatorC.startAnimation();
        getBinding().animatorO.startAnimation();
        getBinding().animatorM.startAnimation();
        getBinding().animatorE2.startAnimation();
        getBinding().animatorLine1.startAnimation(mScaleAnim);
        getBinding().animatorLine2.startAnimation(mScaleAnim);

        getBinding().firstInfo1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!getBinding().firstInfo1.isInAnimation()) {
                    getBinding().firstInfo1.setTextViewAnimation("欢迎来到新设计！",12, Color.YELLOW);
                }
                if (!getBinding().firstInfo2.isInAnimation()) {
                    getBinding().firstInfo2.setTextViewAnimation("助你成为更好的设计师。",12, Color.YELLOW);
                }
                getBinding().firstInfo1.setVisibility(View.VISIBLE);
                getBinding().firstInfo2.setVisibility(View.VISIBLE);

                getBinding().firstInfo1.setAnimationListener(new RandomAnimationFlowLayout.AnimationListener() {
                    @Override
                    public void onAnimationEnd() {
                        LauncherFirstFragment.super.startAnimation();
                    }
                });
            }
        },getBinding().animatorW.getDuration());

    }

    @Override
    public void stopAnimation() {
        getBinding().animatorW.stopAnimation();
        getBinding().animatorE.stopAnimation();
        getBinding().animatorL.stopAnimation();
        getBinding().animatorC.stopAnimation();
        getBinding().animatorO.stopAnimation();
        getBinding().animatorM.stopAnimation();
        getBinding().animatorE2.stopAnimation();
        getBinding().animatorLine1.clearAnimation();
        getBinding().animatorLine2.clearAnimation();
        getBinding().firstInfo1.setVisibility(View.INVISIBLE);
        getBinding().firstInfo2.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_launcher_first;
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
