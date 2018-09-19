/*
 * Copyright (c)  Created by Cody.yi on 2016/9/3.
 */

package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

/**
 * Created by chy on 2016/9/3.
 * 验证手机号验证码的ViewModel
 */
public class VerifyPhoneNewViewModel extends WithHeaderViewModel {

    private final ObservableField<String> msgCode = new ObservableField<>();
    private final ObservableBoolean showBlue = new ObservableBoolean();
    private final ObservableField<String> userPhone = new ObservableField<>("");
    private final ObservableField<String> textPhone = new ObservableField<>("");
    private final ObservableField<String> userPwd = new ObservableField<>("");

    public ObservableField<String> getUserPhone() {
        return userPhone;
    }

    public ObservableField<String> getTextPhone() {
        return textPhone;
    }

    public ObservableBoolean getShowBlue() {
        return showBlue;
    }

    public ObservableField<String> getUserPwd() {
        return userPwd;
    }

    public ObservableField<String> getMsgCode() {
        return msgCode;
    }
}
