package com.cody.app.business;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.cody.app.R;
import com.cody.app.business.easeui.ImManager;
import com.cody.app.databinding.ActivityLoginNewBinding;
import com.cody.app.framework.activity.BaseBindingActivity;
import com.cody.app.framework.activity.HtmlActivity;
import com.cody.app.utils.CommonUtil;
import com.cody.handler.business.presenter.LoginNewPresenter;
import com.cody.handler.business.viewmodel.LoginNewViewModel;
import com.cody.repository.business.bean.UserBean;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.business.interaction.constant.Constant;
import com.cody.repository.business.interaction.constant.H5Url;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.ToastUtil;
import com.cody.xf.utils.http.SimpleBean;

import java.util.List;

/**
 * Created by Emcy-fu ;
 * on data:  2018/7/11 ;
 */

public class LoginNewActivity extends BaseBindingActivity<LoginNewPresenter, LoginNewViewModel, ActivityLoginNewBinding> {
    private static Boolean isExit = false;
    private static boolean flag; // 登录界面只能启动一次（登录超时的时候会启动多个登录界面）

    /**
     * 登出函数 token过期的情况
     */
    public static synchronized void logOutByTime() {
        if (flag) {
            return;
        }
        flag = true;

        Repository.setLocalValue(LocalKey.SHOP_GUIDE_PATH, "");
        Repository.setLocalValue(LocalKey.SHOP_GUIDE_PATH, "");
        Repository.setLocalValue(LocalKey.USER_ROLE, "");
        ActivityUtil.navigateTo(LoginNewActivity.class);
    }

    /**
     * 登出函数 正常登出
     */
    public static void logOutByUser() {
        ImManager.getInstance().logout(true, null);
        Repository.setLocalValue(LocalKey.SHOP_GUIDE_PATH, "");
        Repository.setLocalValue(LocalKey.SHOP_GUIDE_PATH, "");
        Repository.setLocalValue(LocalKey.USER_ROLE, "");
        Repository.setLocalInt(LocalKey.USER_STATUS, 0);
        UserInfoManager.setImId("");
        Activity activity = ActivityUtil.getCurrentActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, LoginNewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityUtil.navigateToThenKill(intent);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login_new;
    }

    @Override
    protected LoginNewPresenter buildPresenter() {
        return new LoginNewPresenter();
    }

    @Override
    protected LoginNewViewModel buildViewModel(Bundle savedInstanceState) {
        return new LoginNewViewModel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 将ViewModel和mBinding进行绑定
         */
        InputFilter[] spaceFilters = {CommonUtil.getSpaceFilter()};
        getBinding().loginPwd.setFilters(spaceFilters);
        getBinding().etPhone.post(new Runnable() {
            @Override
            public void run() {
                getBinding().etPhone.setSelection(getBinding().etPhone.getText().length());
            }
        });
        //检验用户是否可以免登 用户角色和用户token不能为空
        if (!TextUtils.isEmpty(Repository.getLocalValue(LocalKey.USER_ROLE))) {
            switch (Repository.getLocalValue(LocalKey.USER_ROLE)) {
                default:
                    ActivityUtil.navigateToThenKill(MainActivity.class);
                    break;
            }
        }

        //按钮颜色监听
        getListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_sure:
                String phone = getBinding().etPhone.getPhoneText();
                getViewModel().getUserPhone().set(phone);
                String loginPwd = getViewModel().getUserPwd().get();
                if (CommonUtil.isMobileNumber(phone) && CommonUtil.isAccordPassword(loginPwd)) {
                    getPresenter().onGetLoginClick(TAG);
                }
                break;
            case R.id.tv_forget:
                ActivityUtil.navigateTo(VerifyPhoneNewActivity.class);
                break;
            case R.id.tv_guide:
                HtmlActivity.startHtml("向导", H5Url.GUIDE);
                break;
        }
    }

    @Override
    public void onUpdate(Object... args) {
        super.onUpdate(args);
        if (Constant.RequestType.TYPE_1 == (int) args[0]) {
            List<UserBean.GroupListBean> groupListBean = getViewModel().getGroupListBeen();
            if (groupListBean != null && groupListBean.size() > 0) {
                // 登录IM
                ImManager.getInstance().isReconnectIM();
                for (int i = 0; i < groupListBean.size(); i++) {
                    if (Constant.Role.SALE.equals(groupListBean.get(i).getGroupCode())) {
                        Repository.setLocalValue(LocalKey.USER_ROLE, Constant.Role.SALE);
                        /*个人中心登录完成之后才能登录im，但是我们获取聊天对象列表是在登录成功后，会造成imid为旧值所以为产生问题*/
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                    }
                }
            } else {
                ToastUtil.showToast("用户不存在!");
            }
        }
    }

    @Override
    public void showError(SimpleBean msg) {
        super.showError(msg);
        Repository.setLocalValue(LocalKey.USER_ROLE, "");
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
                if (getBinding().etPhone.length() != 0 && getBinding().loginPwd.length() != 0) {
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
                if (getBinding().etPhone.length() != 0 && getBinding().loginPwd.length() != 0) {
                    getViewModel().getShowBlue().set(true);
                } else {
                    getViewModel().getShowBlue().set(false);
                }
            }
        });
    }
}
