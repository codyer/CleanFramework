package com.cody.xf.utils;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import com.cody.xf.R;
import com.cody.xf.XFoundation;
import com.cody.xf.utils.http.HttpCode;
import com.cody.xf.utils.http.SimpleBean;


/**
 * Created by cody.yi on 2016/8/4.
 * TODO 统一样式
 */
public class ToastUtil {
    private static Toast mToast;

    /**
     * 解析并显示http code
     * Toast 统一显示为服务器返回的信息
     *
     * @param simpleBean http返回结果
     */
    public static void showSimpleBean(SimpleBean simpleBean) {
        int message = R.string.xf_http_code_unknown;
        if (simpleBean != null) {
            switch (simpleBean.getCode()) {
                case HttpCode.REQUEST_ERROR:
                    message = R.string.xf_http_code_request_err;
                    break;
                case HttpCode.NETWORK_DISCONNECTED:
                    message = R.string.xf_http_code_no_network_err;
                    break;
                case HttpCode.PARAMETER_ERROR:
                    message = R.string.xf_http_code_parameter_err;
                    break;
                case HttpCode.UN_LOGIN:
                    message = R.string.xf_http_code_nor1;
                    break;
                case HttpCode.SUCCESS:
                    message = R.string.xf_http_code_200;
                    break;
                case HttpCode.SERVER_ERROR:
                    message = R.string.xf_http_code_500;
                    break;
                case HttpCode.NOT_FOUND:
                    message = R.string.xf_http_code_40003;
                    break;
                default:
                    message = -1;
                    break;
            }
            //显示系统提示信息
            if (HttpCode.DEBUG) {
                LogUtil.e(simpleBean.toString());
            }
            if (message == -1) {
                showToast(simpleBean.getMessage());
            } else {
                showToast(message);
            }
        } else {
            showToast(message);
        }
    }

    public static void showToast(String text) {
        showToast(XFoundation.getContext(), text);
    }

    public static void showToast(int resId) {
        showToast(XFoundation.getContext(), resId);
    }

    private static void showToast(Context context, String text) {
        if (!isMainThread()) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    private static void showToast(Context context, int resId) {
        if (!isMainThread()) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(context.getString(resId));
        }
        mToast.show();
    }

    /**
     * 是否在主线程
     *
     * @return
     */
    private static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
