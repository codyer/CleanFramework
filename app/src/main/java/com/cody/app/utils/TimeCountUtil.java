/*
 * Copyright (c)  Created by Cody.yi on 2016/9/3.
 */

package com.cody.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by chy on 2016/7/11.
 * 验证手机号倒计时方法
 */
public class TimeCountUtil extends CountDownTimer {
    private TextView tv_count;//按钮

    // 是总的时间millisInFuture，一个是countDownInterval，然后就是你在哪个按钮上做这个是，就把这个按钮传过来就可以了
    public TimeCountUtil(long millisInFuture, long countDownInterval, TextView tv_count) {
        super(millisInFuture, countDownInterval);
        this.tv_count = tv_count;
    }


    @Override
    public void onTick(long millisUntilFinished) {
        tv_count.setEnabled(false);//设置不能点击
        tv_count.setText("获取验证码("+millisUntilFinished / 1000 + "s)");//设置倒计时时间
    }


    @Override
    public void onFinish() {
        tv_count.setText("获取验证码");
        tv_count.setEnabled(true);//重新获得点击
    }

    public void reset() {
        cancel();
        tv_count.setText("获取验证码");
        tv_count.setEnabled(true);//重新获得点击
    }

    public static void putJointRegister(Context context, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getJointRegister(Context context, String key, long defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

}
