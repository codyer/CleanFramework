package com.cody.app.business.launch;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cody.app.R;
import com.cody.app.databinding.ActivityLauncherPageBinding;
import com.cody.app.framework.activity.BaseBindingActivity;
import com.cody.handler.business.viewmodel.LauncherViewModel;
import com.cody.handler.framework.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cody.yi on 2017/9/4.
 * 加载页
 */
public class LauncherPageActivity extends BaseBindingActivity<Presenter<LauncherViewModel>, LauncherViewModel, ActivityLauncherPageBinding>
        implements OnPageAnimatorListener {

    private List<LauncherBaseFragment> mLauncherFragments = new ArrayList<>();
    private BaseLauncherFragmentAdapter mLauncherFragmentAdapter;

    private ImageView[] mIndicators;
    private int mCurrentPage;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_launcher_page;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化点点点控件
        ViewGroup group = getBinding().launcherIndicator;
        ViewPager viewPager = getBinding().launcherViewPager;
        mIndicators = new ImageView[3];
        for (int i = 0; i < mIndicators.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(10, 10));
            if (i == 0) {
                imageView.setImageResource(R.drawable.page_indicator_focused);
            } else {
                imageView.setImageResource(R.drawable.page_indicator_unfocused);
            }
            mIndicators[i] = imageView;

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 20;//设置点点点view的左边距
            layoutParams.rightMargin = 20;//设置点点点view的右边距
            group.addView(imageView, layoutParams);
        }
        /*
         * 初始化三个fragment  并且添加到list中
         */
        LauncherFirstFragment firstFragment = LauncherFirstFragment.getInstance(this);
        LauncherSecondFragment secondFragment = LauncherSecondFragment.getInstance(this);
        LauncherThirdFragment thirdFragment = LauncherThirdFragment.getInstance(this);

        mLauncherFragments.add(firstFragment);
        mLauncherFragments.add(secondFragment);
        mLauncherFragments.add(thirdFragment);

//        getBinding().secondPictures.setAdapter(new MarqueeViewAdapter(this));
        mLauncherFragmentAdapter = new BaseLauncherFragmentAdapter(getSupportFragmentManager(), mLauncherFragments);
        viewPager.setAdapter(mLauncherFragmentAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 监听viewpager的移动
     */
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int index) {
            setImageBackground(index);//改变点点点的切换效果
            LauncherBaseFragment fragment = mLauncherFragments.get(index);

            mLauncherFragments.get(mCurrentPage).stopAnimation();//停止前一个页面的动画
            fragment.startAnimation();//开启当前页面的动画

            mCurrentPage = index;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    /**
     * 改变点点点的切换效果
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < mIndicators.length; i++) {
            if (i == selectItems) {
                mIndicators[i].setImageResource(R.drawable.page_indicator_focused);
            } else {
                mIndicators[i].setImageResource(R.drawable.page_indicator_unfocused);
            }
        }
    }

    @Override
    public void startAnimation(int index) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);

        switch (index) {
            case 0:
                break;
            case 1:
                getBinding().secondPictures.setVisibility(View.VISIBLE);
                getBinding().secondPictures.start();
                //缩放动画结束 开启改变透明度动画
                getBinding().secondPictures.startAnimation(alphaAnimation);
                break;
            case 2:
                getBinding().thirdPictures.setVisibility(View.VISIBLE);
                //缩放动画结束 开启改变透明度动画
                getBinding().thirdPictures.startAnimation(alphaAnimation);
                break;
        }
    }

    @Override
    public void stopAnimation(int index) {
        switch (index) {
            case 0:
                break;
            case 1:
                getBinding().secondPictures.stop();
                getBinding().secondPictures.setVisibility(View.GONE);
                break;
            case 2:
                getBinding().thirdPictures.setVisibility(View.GONE);
                break;
        }
    }
}
