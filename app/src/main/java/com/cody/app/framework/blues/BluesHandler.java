package com.cody.app.framework.blues;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.cody.app.BuildConfig;
import com.cody.xf.utils.ToastUtil;

/**
 * Created by cody.yi on 2018/6/6.
 * blues 默认处理类
 */
final public class BluesHandler implements Blues.ExceptionHandler {
    final private static String BLUES_KEY = "BLUES_KEY";
    final private static String BLUES_TOAST = "出现异常\n建议返回重试或重启应用。";
    private Context mContext;

    BluesHandler(Context context) {
        mContext = context;
        // 确保blues初始化在 buglly 之后
        CrashUtil.init(context);
    }

    @Override
    public void handlerException(final Thread thread, final Throwable throwable) {
        if (mContext == null) {
            CrashUtil.postException(throwable);
            return;
        }
//        SwitchConfigBean config = UserDataManager.getSwitchConfig();
//        if (config != null && config.bluesControl == 1 && config.buglyControl == 1) {
//            CrashUtil.postException(mContext, throwable);
//            return;
//        }
        SharedPreferences settings = mContext.getSharedPreferences(BLUES_KEY, Context.MODE_PRIVATE);
        if (throwable.getStackTrace() != null && settings != null) {
            String blues = settings.getString(BLUES_KEY, "Blues");
            String stackTrace = (throwable.getStackTrace())[0].toString();
            showToast("出现异常：\n" + thread + "\n" + throwable.toString());
            if (blues.equals(stackTrace)) {
                //建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
                Log.e("Blues", "--->BluesException:" + thread + "<---", throwable);
            } else {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(BLUES_KEY, stackTrace);
                editor.apply();
                CrashUtil.postException(mContext, throwable);
            }
        }
    }

    private void showToast(final String msg) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    ToastUtil.showToast(BuildConfig.DEBUG ? msg : BLUES_TOAST);
                } catch (Throwable e) {
                    CrashUtil.postException(mContext, e);
                }
            }
        });
    }
}
