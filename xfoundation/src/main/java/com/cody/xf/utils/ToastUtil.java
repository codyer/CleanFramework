package com.cody.xf.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.cody.xf.XFoundation;
import com.cody.xf.common.SimpleBean;


/**
 * Created by cody.yi on 2016/8/4.
 * TODO 统一样式
 */
public class ToastUtil {
//    private static Context sContext = Foundation.getContext();

    /**
     * 解析并显示http code
     * Toast 统一显示为服务器返回的信息
     *
     * @param simpleBean http返回结果
     */
    public static void showToast(SimpleBean simpleBean) {
//        if (simpleBean != null && !"null".equals(simpleBean.getMessage())) {
        showToast(simpleBean.getMessage());
//        } else {
//            showFailure(R.string.foundation_http_code_request_err);
//        }
        /* int message = R.string.foundation_http_code_unknown;
       if (simpleBean != null) {
            switch (simpleBean.getCode()) {
                case Constant.HttpCode.REQUEST_ERROR:
                    message = R.string.foundation_http_code_request_err;
                    break;
                case Constant.HttpCode.NETWORK_DISCONNECTED:
                    message = R.string.foundation_http_code_no_network_err;
                    break;
                case Constant.HttpCode.PARAMETER_ERROR:
                    message = R.string.foundation_http_code_parameter_err;
                    break;
                case Constant.HttpCode.UN_LOGIN:
                    message = R.string.foundation_http_code_nor1;
                    break;
                case Constant.HttpCode.SUCCESS:
                    message = R.string.foundation_http_code_200;
                    break;
                case Constant.HttpCode.SERVER_ERROR:
                    message = R.string.foundation_http_code_500;
                    break;
                case Constant.HttpCode.NOT_FOUND:
                    message = R.string.foundation_http_code_40003;
                    break;
            }
            //显示系统提示信息
            if (Constant.TOAST_DEBUG) {
                showFailure(simpleBean.getMessage());
//                showFailure(simpleBean.toString());
                return;
            }
        }
         showFailure(message);*/
    }

    public static void showToast(String text) {
        showToast(XFoundation.getContext(), text);
    }

    public static void showToast(int resId) {
        showToast(XFoundation.getContext(), resId);
    }

    private static void showToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private static void showToast(Context context, int resId) {
        Toast toast = Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
