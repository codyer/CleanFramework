package com.cody.app.business;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cody.app.framework.activity.BaseActivity;
import com.cody.xf.XFoundation;
import com.cody.xf.utils.ActivityUtil;

/**
 * 退出app时清空栈里的所有activity,辅助activity
 */
public class AssistActivity extends BaseActivity {
    /**
     * 在其他设备登录，强制退出
     */
    public static void logOutByOtherDevice() {
        XFoundation.getApp().clearHeader();
        Activity activity = ActivityUtil.getCurrentActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, AssistActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityUtil.navigateToThenKill(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finish();
    }
}
