package com.cody.app.business.launch;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cody.yi on 2017/9/6.
 * some useful information
 */
public class CharacterView extends android.support.v7.widget.AppCompatTextView {
    private Position mCurrentPosition;
    private int mDuration = 2000;

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    private List<Position> mPositions = new ArrayList<>();
    private AnimatorSet mAnimatorSet;

    public Position getLastPosition() {
        return mPositions.get(mPositions.size() - 1);
    }

    public Position getCurrentPosition() {
        return mCurrentPosition;
    }

    public List<Position> getPositions() {
        return mPositions;
    }

    public CharacterView addPosition(Position position) {
        mPositions.add(position);
        return this;
    }

    public CharacterView addPosition(float x, float y) {
        mPositions.add(new Position(x, y));
        return this;
    }

    public CharacterView addLineToX(float x) {
        mPositions.add(new Position(x, getLastPosition().y));
        return this;
    }

    public CharacterView addLineToY(float y) {
        mPositions.add(new Position(getLastPosition().x, y));
        return this;
    }

    public CharacterView(Context context) {
        this(context, null);
    }

    public CharacterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mCurrentPosition == null) {
            mCurrentPosition = new Position(getX(), getY());
            mPositions.clear();
            addPosition(mCurrentPosition);
        }
    }

    public CharacterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startAnimation() {
        if (mAnimatorSet == null) {
            mAnimatorSet = new AnimatorSet();
            ValueAnimator positionAnimator = ValueAnimator.ofObject(new PositionEvaluator(), mPositions.toArray());
            positionAnimator.setDuration(mDuration);
            positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentPosition = (Position) animation.getAnimatedValue();
                    setX(mCurrentPosition.x);
                    setY(mCurrentPosition.y);
                    invalidate();
                }
            });
            positionAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            ValueAnimator scaleAnimator = ValueAnimator.ofFloat(1.0f, 0.5f);
            scaleAnimator.setDuration(mDuration);
            scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float scale = (float) animation.getAnimatedValue();
                    setPivotX(0);
                    setScaleX(scale);
                    setScaleY(scale);
                    invalidate();
                }
            });
            mAnimatorSet.playTogether(positionAnimator, scaleAnimator);
        }
        if (!mAnimatorSet.isStarted()) {
            mAnimatorSet.start();
        }
    }

    public void stopAnimation() {
        if (mAnimatorSet != null && mAnimatorSet.isStarted()) {
            mAnimatorSet.end();
        }
    }
}
