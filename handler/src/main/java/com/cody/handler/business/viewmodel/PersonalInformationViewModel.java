/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

/**
 * Created by cody.yi on 2016/8/19.
 * 导购员 个人信息
 */
public class PersonalInformationViewModel extends WithHeaderViewModel {
    private final ObservableField<String> mMonth = new ObservableField<>("1");//数据报表月份
    private final ObservableField<String> mRealName = new ObservableField<>("Cody.A");//真名
    private final ObservableField<String> mGender = new ObservableField<>("男");//性别 ，0：未设定 2：女 1：男
    private final ObservableField<String> mQrCodeUrl = new ObservableField<>("http:qr.code");//二维码地址
    private final ObservableField<String> mImageUrl = new ObservableField<>("");//头像地址
    private final ObservableField<String> mNickName = new ObservableField<>("Cody");//昵称
    private final ObservableField<String> mTelPhone = new ObservableField<>("18888888888");//手机号
    private final ObservableField<String> mSelfIntroduction = new ObservableField<>("自我介绍");//自我介绍
    private final ObservableField<String> mVerifyCode = new ObservableField<>("2566");//验证码
    private final ObservableBoolean mSalesDataChecked = new ObservableBoolean(true);

    private final ObservableBoolean mImStatus = new ObservableBoolean(false);//IM 在线状态
    private final ObservableBoolean mImStatus2 = new ObservableBoolean(true);//IM 在线状态
    private final ObservableBoolean mImStatus3 = new ObservableBoolean(true);//IM 在线状态

    public ObservableField<String> getMonth() {
        return mMonth;
    }

    public ObservableField<String> getRealName() {
        return mRealName;
    }

    public ObservableField<String> getGender() {
        return mGender;
    }

    public ObservableField<String> getQrCodeUrl() {
        return mQrCodeUrl;
    }

    public ObservableField<String> getImageUrl() {
        return mImageUrl;
    }

    public ObservableField<String> getNickName() {
        return mNickName;
    }

    public ObservableField<String> getTelPhone() {
        return mTelPhone;
    }

    public ObservableField<String> getSelfIntroduction() {
        return mSelfIntroduction;
    }

    public ObservableField<String> getVerifyCode() {
        return mVerifyCode;
    }

    public ObservableBoolean getSalesDataChecked() {
        return mSalesDataChecked;
    }

    public ObservableBoolean getImStatus() {
        return mImStatus;
    }

    public ObservableBoolean getImStatus2() {
        return mImStatus2;
    }

    public ObservableBoolean getImStatus3() {
        return mImStatus3;
    }

}
