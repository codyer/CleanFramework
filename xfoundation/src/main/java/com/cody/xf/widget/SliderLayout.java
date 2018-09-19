package com.cody.xf.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.cody.xf.R;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by dong.wang
 * Date: 2018/1/22
 * Time: 15:14
 * Description:
 */
public class SliderLayout extends LinearLayout {
    private int totalNum = 0;
    private Drawable mSliderImage;
    private TabLayout mTabLayout;

    public SliderLayout(Context context) {
        this(context, null);
    }

    public SliderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SliderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.xf_SliderLayout);
        mSliderImage = array.getDrawable(R.styleable.xf_SliderLayout_xf_slider_pic);
        if (mSliderImage == null) {
            mSliderImage = context.getResources().getDrawable(R.drawable.xf_tablayout_bg_shape);
        }
        array.recycle();
    }

    private LinearLayout getTabStrip() {
        if (mTabLayout == null) return null;
        return (LinearLayout) mTabLayout.getChildAt(0);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LinearLayout mTabStrip = getTabStrip();
        if (mTabStrip == null) return;
        totalNum = mTabStrip.getChildCount();
        if (totalNum > 0) {
            View firstView = mTabStrip.getChildAt(0);
            int width = firstView.getMeasuredWidth();
            int paddingVertical = 0;
            int paddingHorizontal = 0;
            int bottom = getHeight() - paddingVertical;
            int right = width - paddingHorizontal;
            mSliderImage.setBounds(paddingVertical, paddingHorizontal, right, bottom);
            mSliderImage.draw(canvas);
        }

    }

    public void setupWithTabLayout(TabLayout tabLayout) {
        this.mTabLayout = tabLayout;
    }


    public static class SliderOnPageChangeListener extends TabLayout.TabLayoutOnPageChangeListener {
        private final SoftReference<SliderLayout> mSliderLayoutRef;

        public SliderOnPageChangeListener(TabLayout tabLayout, SliderLayout layout) {
            super(tabLayout);
            mSliderLayoutRef = new SoftReference<SliderLayout>(layout);
            layout.setupWithTabLayout(tabLayout);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            final SliderLayout layout = mSliderLayoutRef.get();
            if (layout != null) {
                layout.setScrollPosition(position, positionOffset);
            }
        }
    }

    /**
     * 把滑块滑动到指定的位置
     *
     * @param position       当前位置
     * @param positionOffset 滑动到下一个或上一个位置比例
     */
    private void setScrollPosition(int position, float positionOffset) {
        final int roundedPosition = Math.round(position + positionOffset);
        if (roundedPosition < 0 || roundedPosition >= totalNum) {
            return;
        }
        float scrollX = calculateScrollXForTab(position, positionOffset);
        scrollTo((int) scrollX, 0);
    }

    /**
     * 计算滑块需要滑动的距离
     *
     * @param position       当前选择的位置
     * @param positionOffset 滑动位置的百分百
     * @return 滑动的距离
     */
    private int calculateScrollXForTab(int position, float positionOffset) {
        if (mTabLayout == null) return 0;
        LinearLayout mTabStrip = getTabStrip();
        if (mTabStrip == null) return 0;
        //当前选择的View
        final View selectedChild = mTabStrip.getChildAt(position);
        //下一个View
        final View nextChild = position + 1 < mTabStrip.getChildCount()
                ? mTabStrip.getChildAt(position + 1)
                : null;
        //当前选择的View的宽度
        final int selectedWidth = selectedChild != null ? selectedChild.getWidth() : 0;
        //下一个View的宽度
        final int nextWidth = nextChild != null ? nextChild.getWidth() : 0;
        //当前选择的View的左边位置，view的方位
        final int left = selectedChild != null ? selectedChild.getLeft() : 0;
        //计算滑块需要滑动的距离,左 + ，右 - ；
        int scrollX = -(left + ((int) ((selectedWidth + nextWidth) * positionOffset * 0.5f)));
        //现在是把SliderLayout直接放在TableLayout中的所以就不许要考虑TableLayout本身的滑动
        if (mTabLayout.getTabMode() == TabLayout.MODE_SCROLLABLE) {//当为滑动模式的时候TabLayout会有水平方向的滑动
            scrollX += mTabLayout.getScrollX();//计算在TabLayout有滑动的时候，滑块相对的滑动距离
        }
        return scrollX;
    }

    public void setSliderImage(Drawable sliderImage) {
        mSliderImage = sliderImage;
        invalidate();
    }

    public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private final WeakReference<TabLayout> mTabLayoutRef;
        private int mPreviousScrollState;
        private int mScrollState;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            mTabLayoutRef = new WeakReference<>(tabLayout);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mPreviousScrollState = mScrollState;
            mScrollState = state;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            //省略
        }

        @Override
        public void onPageSelected(int position) {
            //省略
        }

        private void reset() {
//            mPreviousScrollState = mScrollState = SCROLL_STATE_IDLE;
        }
    }
}
