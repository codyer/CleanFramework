package com.cody.app.business.launch;

import android.animation.TypeEvaluator;

/**
 * Created by cody.yi on 2017/9/6.
 * some useful information
 */
public class PositionEvaluator implements TypeEvaluator<Position> {

    private Position mPoint;


    public PositionEvaluator() {
    }

    public PositionEvaluator(Position reuse) {
        mPoint = reuse;
    }

    @Override
    public Position evaluate(float fraction, Position startValue, Position endValue) {
        float x = startValue.x + (fraction * (endValue.x - startValue.x));
        float y = startValue.y + (fraction * (endValue.y - startValue.y));

        if (mPoint != null) {
            mPoint.set(x, y);
            return mPoint;
        } else {
            return new Position(x, y);
        }
    }
}
