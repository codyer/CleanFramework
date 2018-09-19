/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cody.app.framework.zxing.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.cody.app.R;
import com.google.zxing.ResultPoint;
import com.cody.xf.common.NotProguard;
import com.cody.xf.utils.DeviceUtil;

import java.util.Collection;
import java.util.HashSet;


/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 */
@NotProguard
public final class ViewfinderView extends View {
    @SuppressWarnings("unused")
    private static final String TAG = "log";
    private Context context;
    private static final long ANIMATION_DELAY = 5L;
    private static final int OPAQUE = 0xFF;

    private int ScreenRate;

    //二维码边线的长度
    private static final int CORNER_WIDTH = 5;
    private static final int MIDDLE_LINE_WIDTH = 5;
    private static final int MIDDLE_LINE_PADDING = 5;

    private static final int SPEEN_DISTANCE = 10;

    private static float density;
    private static final int TEXT_SIZE = 13;
    private static final int TEXT_PADDING_TOP = 30;

    private Paint paint;

    private int slideTop;

    private int slideBottom;
    //最底层半透明画布
    private Bitmap resultBitmap;
    private final int maskColor;
    private final int resultColor;

    private final int resultPointColor;
    private Collection<ResultPoint> possibleResultPoints;
    private Collection<ResultPoint> lastPossibleResultPoints;

    boolean isFirst;

    private int screenWidth;
    private int screenHeight;

    private Bitmap middleLineBp;

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        density = context.getResources().getDisplayMetrics().density;
        ScreenRate = (int) (16 * density);

        screenWidth = (int) DeviceUtil.getScreenWidth();
        screenHeight = (int) DeviceUtil.getScreenHeight();

        paint = new Paint();
        Resources resources = getResources();
        maskColor = resources.getColor(R.color.transparent_mask);
        resultColor = resources.getColor(R.color.transparent_mask);

        resultPointColor = resources.getColor(R.color.green);
        possibleResultPoints = new HashSet<ResultPoint>(5);
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        // 绘制扫一扫矩形框
        Rect frame = new Rect(screenWidth / 2 - screenWidth * 120 / 375,
                screenHeight / 2 - screenWidth * 180 / 375, screenWidth / 2
                + screenWidth * 120 / 375 , screenHeight / 2
                + screenWidth * 60 / 375);

        if (!isFirst) {
            isFirst = true;
            slideTop = frame.top;
            slideBottom = frame.bottom;
        }

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        paint.setColor(resultBitmap != null ? resultColor : maskColor);

        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
                paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(OPAQUE);
            canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
        } else {

			/*对画布进行清屏*/
            if (canvas != null) {
                Paint paint = new Paint();
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                canvas.drawPaint(paint);
                canvas.drawColor(getResources().getColor(R.color.transparent_mask));
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                canvas.drawRect(frame.left, frame.top, frame.right, frame.bottom, paint);

                paint = null;
            }

            // 绘制扫一扫四边的绿色线条框
            paint.setColor(Color.parseColor("#495AFF"));

            canvas.drawRect(frame.left, frame.top, frame.left + ScreenRate,
                    frame.top + CORNER_WIDTH, paint);
            canvas.drawRect(frame.left, frame.top, frame.left + CORNER_WIDTH,
                    frame.top + ScreenRate, paint);
            canvas.drawRect(frame.right - ScreenRate, frame.top, frame.right,
                    frame.top + CORNER_WIDTH, paint);
            canvas.drawRect(frame.right - CORNER_WIDTH, frame.top, frame.right,
                    frame.top + ScreenRate, paint);
            canvas.drawRect(frame.left, frame.bottom - CORNER_WIDTH, frame.left
                    + ScreenRate, frame.bottom, paint);
            canvas.drawRect(frame.left, frame.bottom - ScreenRate, frame.left
                    + CORNER_WIDTH, frame.bottom, paint);
            canvas.drawRect(frame.right - ScreenRate, frame.bottom
                    - CORNER_WIDTH, frame.right, frame.bottom, paint);
            canvas.drawRect(frame.right - CORNER_WIDTH, frame.bottom
                    - ScreenRate, frame.right, frame.bottom, paint);

            slideTop += SPEEN_DISTANCE;
            if (slideTop >= frame.bottom) {
                slideTop = frame.top;
            }
            canvas.drawRect(frame.left + MIDDLE_LINE_PADDING, slideTop
                            - MIDDLE_LINE_WIDTH / 2, frame.right - MIDDLE_LINE_PADDING,
                    slideTop + MIDDLE_LINE_WIDTH / 2, paint);

            paint.setColor(context.getResources().getColor(R.color.main_white));
            paint.setTextSize(TEXT_SIZE * density);
            paint.setTextAlign(Paint.Align.CENTER);

            //写入二维码框下面的文字
            canvas.drawText("将商品价签二维码放入框内，即可自动扫描",
                    screenWidth / 2,
                    screenHeight / 2 + screenWidth * 85 / 375, paint);

            Collection<ResultPoint> currentPossible = possibleResultPoints;
            Collection<ResultPoint> currentLast = lastPossibleResultPoints;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new HashSet<ResultPoint>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(OPAQUE);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(frame.left + point.getX(), frame.top
                            + point.getY(), 6.0f, paint);
                }
            }
            if (currentLast != null) {
                paint.setAlpha(OPAQUE / 2);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle(frame.left + point.getX(), frame.top
                            + point.getY(), 3.0f, paint);
                }
            }

            postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
                    frame.right, frame.bottom);

        }
    }

    public void drawViewfinder() {
        resultBitmap = null;
        invalidate();
    }

    /**
     * Draw a bitmap with the result points highlighted instead of the live
     * scanning display.
     *
     * @param barcode An image of the decoded barcode.
     */
    public void drawResultBitmap(Bitmap barcode) {
        resultBitmap = barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        possibleResultPoints.add(point);
    }

}
