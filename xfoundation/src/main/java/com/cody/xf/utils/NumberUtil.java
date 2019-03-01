package com.cody.xf.utils;


import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by liuliwei on 2018-08-30.
 */

public class NumberUtil {

    /**
     * 获取float类型小数点后有几位
     *
     * @return
     */
    public static int getFloatDotLength(float value) {
        String strValue = String.valueOf(value);
        return strValue.length() - strValue.indexOf(".") - 1;
    }

    /**
     * 四舍五入，保留几位小数
     *
     * @param value
     * @param scale 保留小数点后几位
     * @return
     */
    public static float roundAtPosition(float value, int scale) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }

    //价格去0
    public static String getPrice(String value) {
        if (TextUtils.isEmpty(value)) {
            return "0";
        }

        return getPrice(new BigDecimal(value));

    }

    //价格去0
    public static String getPrice(BigDecimal value) {
        if (value == null) {
            return "0";
        }
        if (value.compareTo(new BigDecimal(0)) == 0) {
            return "0";
        }

        return value.stripTrailingZeros().toPlainString();
    }
}
