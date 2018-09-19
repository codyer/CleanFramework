package com.cody.xf.utils;


import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * Created by liuliwei on 2018-08-30.
 */

public class NumberUtil {
    //价格去0
    public static String getPrice(String value) {
        if (TextUtils.isEmpty(value)){
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
