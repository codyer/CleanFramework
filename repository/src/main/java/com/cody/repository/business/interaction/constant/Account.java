package com.cody.repository.business.interaction.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by cody.yi on 2017/5/16.
 * 监理账户
 */
public class Account {
    public final static String KEY_TOKEN = "x-auth-token";
    public final static String SMS_TEMPLATE_QUICK_LOGIN_ID = "10000";// 快捷登录
    public final static String SMS_TEMPLATE_REGISTER_ID = "10012";// 注册
    public final static String SMS_TEMPLATE_PASSWORD_ID = "10013";// 找回密码

    public final static int DRAFT = 1;// 草稿
    public final static int REJECT = 2;// 驳回
    public final static int PASS = 3;// 审核通过
    public final static int UNDER_REVIEW = 4;// 审核中

    public final static int FINISHED = 1;
    public final static int UN_FINISHED = -1;

    public final static int SETTLED = 1; // 入驻
    public final static int UN_SETTLED = 0; // 未入驻

    /**
     * 审核状态
     */
    @IntDef({DRAFT, REJECT, PASS, UNDER_REVIEW})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    /**
     * 资料填写情况
     */
    @IntDef({FINISHED, UN_FINISHED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Profile {
    }

    /**
     * 入驻情况
     */
    @IntDef({SETTLED, UN_SETTLED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Settled {
    }
}
