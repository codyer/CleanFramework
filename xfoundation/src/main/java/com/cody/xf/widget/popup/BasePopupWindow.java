package com.cody.xf.widget.popup;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by dong.wang
 * Date: 2016/7/27
 * Time: 10:43
 * Description:
 */

public abstract class BasePopupWindow extends PopupWindow {
    private static final int TRANSLATE_DURATION = 200;
    private static final int ALPHA_DURATION = 300;
    private View mBg;
    private LinearLayout mPanel;
    protected Context mContext;
    private int layoutId;
    private FrameLayout parent;
    private boolean hasAnimation = true;

    public BasePopupWindow(Context context, int layoutId) {
        this.mContext = context;
        this.layoutId = layoutId;
    }

    protected abstract void callbackView(View view);

    protected int setGravity() {
        return Gravity.CENTER;
    }

    public void showPop() {
        if (layoutId == 0)
            return;
        View view = createView(mContext, layoutId);
        this.setContentView(view);
        this.setHeight(LayoutParams.MATCH_PARENT);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        view.setFocusable(true);
        setClippingEnabled(false);
        view.setFocusableInTouchMode(true);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    popDismiss();
                }
                return false;
            }
        });
        callbackView(view);
        this.showAtLocation(view, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
    }

    private View createView(final Context context, int subView) {
        final View view = LayoutInflater.from(context).inflate(subView, null);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        parent = new FrameLayout(context);
        parent.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        mBg = new View(context);
        mBg.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        mBg.setBackgroundColor(Color.argb(136, 0, 0, 0));
        mBg.setId(View.NO_ID);

        mPanel = new LinearLayout(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = setGravity();
        mPanel.setLayoutParams(params);
        mPanel.setOrientation(LinearLayout.VERTICAL);
        mPanel.setFocusable(true);

//        parent.setPadding(0, 0, 0, getNavBarHeight(mContext));
        parent.setPadding(0, 0, 0, 0);
        parent.addView(mBg);
        parent.addView(mPanel);
        mPanel.addView(view);
        if (hasAnimation) {
            mBg.startAnimation(createAlphaInAnimation());
            mPanel.startAnimation(createTranslationInAnimation());
        }

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDismiss();
            }
        });
        return parent;
    }

    public BasePopupWindow setHasAnimation(boolean hasAnimation) {
        this.hasAnimation = hasAnimation;
        return this;
    }

    public void popDismiss() {
        if (hasAnimation) {
            mPanel.startAnimation(createTranslationOutAnimation());
            mBg.startAnimation(createAlphaOutAnimation());

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, ALPHA_DURATION);
        } else {
            dismiss();
        }
    }

    private Animation createTranslationInAnimation() {
        int type = TranslateAnimation.RELATIVE_TO_SELF;
        TranslateAnimation an = new TranslateAnimation(type, 0, type, 0, type,
                1, type, 0);
        an.setDuration(TRANSLATE_DURATION);
        return an;
    }

    private Animation createAlphaInAnimation() {
        AlphaAnimation an = new AlphaAnimation(0, 1);
        an.setDuration(ALPHA_DURATION);
        return an;
    }

    private Animation createTranslationOutAnimation() {
        int type = TranslateAnimation.RELATIVE_TO_SELF;
        TranslateAnimation an = new TranslateAnimation(type, 0, type, 0, type,
                0, type, 1);
        an.setDuration(TRANSLATE_DURATION);
        an.setFillAfter(true);
        return an;
    }

    private Animation createAlphaOutAnimation() {
        AlphaAnimation an = new AlphaAnimation(1, 0);
        an.setDuration(ALPHA_DURATION);
        an.setFillAfter(true);
        return an;
    }

    private int getNavBarHeight(Activity c) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            boolean hasMenuKey = ViewConfiguration.get(c).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

            if (!hasMenuKey && !hasBackKey) {
                //The device has a navigation bar
                Resources resources = c.getResources();

                int orientation = c.getResources().getConfiguration().orientation;
                int resourceId;
                if (isTablet(c)) {
                    resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
                } else {
                    resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_width", "dimen", "android");
                }

                if (resourceId > 0) {
                    return c.getResources().getDimensionPixelSize(resourceId);
                }
            }
        }
        return result;
    }

    private boolean isTablet(Context c) {
        return (c.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
