package com.cody.app.business;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ActivityVerifyPhoneNewBinding;
import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.app.utils.CommonUtil;
import com.cody.app.utils.TimeCountUtil;
import com.cody.handler.business.presenter.VerifyPhoneNewPresenter;
import com.cody.handler.business.viewmodel.VerifyPhoneNewViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.KeyConstant;
import com.cody.repository.business.interaction.constant.Constant;
import com.cody.xf.utils.EncryptionUtils;
import com.cody.xf.utils.ToastUtil;
import com.cody.xf.utils.http.SimpleBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emcy-fu ;
 * on data:  2018/7/17 ;
 */

public class VerifyPhoneNewActivity extends WithHeaderActivity<VerifyPhoneNewPresenter, VerifyPhoneNewViewModel,
        ActivityVerifyPhoneNewBinding> {

    public static final String REGISTER_TIME = "register_time";
    private TimeCountUtil timeCountUtil;


    @Override
    protected int getLayoutID() {
        return R.layout.activity_verify_phone_new;
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setLeft(true);
        header.setLineVisible(false);
        header.setLeftResId(R.drawable.xf_ic_close_black);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long current = System.currentTimeMillis();
        long surplus = current - TimeCountUtil.getJointRegister(this, REGISTER_TIME, 0);
        if (surplus < 60000) {
            timeCountUtil = new TimeCountUtil(60000 - surplus, 1000, getBinding().getCode);
            timeCountUtil.start();
        } else {
            timeCountUtil = new TimeCountUtil(60000, 1000, getBinding().getCode);
        }

        //按钮颜色监听
        getListener();
    }

    @Override
    protected VerifyPhoneNewPresenter buildPresenter() {
        return new VerifyPhoneNewPresenter();
    }

    @Override
    protected VerifyPhoneNewViewModel buildViewModel(Bundle savedInstanceState) {
        return new VerifyPhoneNewViewModel();
    }

    @Override
    public void onClick(View v) {
        String phone = getBinding().etPhone.getPhoneText();
        getViewModel().getUserPhone().set(phone);
        String msgCode = getViewModel().getMsgCode().get();
        String password = getViewModel().getUserPwd().get();
        switch (v.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
            case R.id.getCode:
                if (CommonUtil.isMobileNumber(phone)) {
                    timeCountUtil = new TimeCountUtil(60000, 1000, getBinding().getCode);
                    timeCountUtil.start();
                    TimeCountUtil.putJointRegister(this, REGISTER_TIME, System.currentTimeMillis());
                    Map<String, String> map = new HashMap<>();
                    map.put("appId", KeyConstant.APP_ID);
                    map.put("appSecret", KeyConstant.APP_SECRET);
                    map.put("mobile", phone);
                    map.put("smsTemplateId", Constant.MsgType.LOAST_PWD);
                    getPresenter().onSendMsg(TAG, map);
                }
                break;
            case R.id.login_sure:
                if (CommonUtil.isMobileNumber(phone) && CommonUtil.isAuthCode(msgCode) && CommonUtil.isAccordPassword(password)) {
                    Map<String, String> map = new HashMap<>();
                    map.put("appId", KeyConstant.APP_ID);
                    map.put("appSecret", KeyConstant.APP_SECRET);
                    map.put("mobile", phone);
                    map.put("smsCode", msgCode);
                    map.put("smsTemplateId", Constant.MsgType.LOAST_PWD);
                    String encrypt = EncryptionUtils.Encrypt(password);
                    map.put("password", encrypt);
                    getPresenter().verifyLogin(TAG, map);
                }
                break;
        }
    }

    @Override
    public void onUpdate(Object... args) {
        super.onUpdate(args);
        if (Constant.RequestType.TYPE_1 == (int) args[0]) {
            ToastUtil.showToast("找回密码成功");
            LoginNewActivity.logOutByUser();
        } else if (Constant.RequestType.TYPE_2 == (int) args[0]) {
            ToastUtil.showToast("发送短信成功");
        }
    }

    @Override
    public void showError(SimpleBean msg) {
        super.showError(msg);
        timeCountUtil.reset();
    }

    private void getListener() {
        getBinding().etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getBinding().etPhone.length() != 0 && getBinding().loginPwd.length() != 0
                        && getBinding().etCode.length() != 0) {
                    getViewModel().getShowBlue().set(true);
                } else {
                    getViewModel().getShowBlue().set(false);
                }
            }
        });
        getBinding().loginPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getBinding().etPhone.length() != 0 && getBinding().loginPwd.length() != 0
                        && getBinding().etCode.length() != 0) {
                    getViewModel().getShowBlue().set(true);
                } else {
                    getViewModel().getShowBlue().set(false);
                }
            }
        });
        getBinding().etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getBinding().etPhone.length() != 0 && getBinding().loginPwd.length() != 0
                        && getBinding().etCode.length() != 0) {
                    getViewModel().getShowBlue().set(true);
                } else {
                    getViewModel().getShowBlue().set(false);
                }
            }
        });
    }
}
