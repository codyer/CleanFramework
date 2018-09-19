

package com.cody.handler.business.presenter;

import android.text.TextUtils;

import com.cody.handler.business.mapper.LoginNewMapper;
import com.cody.handler.business.viewmodel.LoginNewViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.KeyConstant;
import com.cody.repository.business.bean.ImInfo;
import com.cody.repository.business.bean.UserBean;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.business.interaction.LogImInteraction;
import com.cody.repository.business.interaction.LoginNewInteraction;
import com.cody.repository.business.interaction.SystemMsgInteraction;
import com.cody.repository.business.interaction.constant.Account;
import com.cody.repository.business.interaction.constant.Constant;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.XFoundation;
import com.cody.xf.utils.EncryptionUtils;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.http.IHeaderListener;
import com.cody.xf.utils.http.SimpleBean;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by chy on 2016/8/31.
 * 登录相关处理 modified by Cody.yi
 */
public class LoginNewPresenter extends Presenter<LoginNewViewModel>{

    private LoginNewInteraction mLoginInteraction;
    private LogImInteraction imInteraction;
    private LoginNewMapper mMapper;

    public LoginNewPresenter() {
        mLoginInteraction = Repository.getInteraction(LoginNewInteraction.class);
        imInteraction = Repository.getInteraction(LogImInteraction.class);
        mMapper = new LoginNewMapper();
    }

    public void onGetLoginClick(Object tag) {
        if (getView() != null) {
            String mobile = getViewModel().getUserPhone().get();
            String password = getViewModel().getUserPwd().get();
            Map<String, String> params = new HashMap<>();
            params.put("appId", KeyConstant.APP_ID);
            params.put("appSecret", KeyConstant.APP_SECRET);
            params.put("mobile", mobile);
            String mPwd1 = EncryptionUtils.Encrypt(password);
            params.put("password", mPwd1);
            userLogin(tag, params);
        }
    }

    private void userLogin(final Object tag, final Map<String, String> params) {
        if (getView() != null) {
            getView().showLoading(null);
            mLoginInteraction.doLogin(tag, params, UserBean.class, new IHeaderListener() {
                @Override
                public void onHeaderResponse(Map<String, String> header) {
                    if (header.containsKey(Account.KEY_TOKEN)) {
                        XFoundation.getApp().onLogin(header);
                    }
                }
            }, new DefaultCallback<UserBean>(this) {
                @Override
                public void onSuccess(UserBean bean) {
                    super.onSuccess(bean);
                    if (bean != null) {
                        mMapper.mapper(getViewModel(), bean);
                        loginIM(tag, bean.getOpenid(), "2");
                    }else {
                        refreshUI();
                    }
                }

                @Override
                public void onFailure(SimpleBean simpleBean) {
                    super.onFailure(simpleBean);
                    getView().showFailure(simpleBean);
                    if ("90004".equals(simpleBean.getCode())) {
                    }
                }

                @Override
                public void onError(SimpleBean simpleBean) {
                    super.onError(simpleBean);
                    getView().showError(simpleBean);
                }
            });
        }
    }

    private void loginIM(Object tag, String openid, String userType) {
        if (getView() != null) {
            Map<String, String> params = new HashMap<>();
            params.put("openId", openid);
            params.put("userType", userType);
            imInteraction.doIMLogin(tag, params, ImInfo.class, new DefaultCallback<ImInfo>(this) {
                @Override
                public void onSuccess(ImInfo bean) {
                    super.onSuccess(bean);
                    if (getView() != null && getViewModel() != null) {
                        UserInfoManager.setImId(bean.getImId());
                        if (Repository.getLocalInt(LocalKey.USER_STATUS) == 0) {
                            Repository.setLocalInt(LocalKey.USER_STATUS, Constant.Status.online);
                        }
                        Repository.setLocalValue(LocalKey.SHOP_ID, bean.getShopId() + ""); // 用户所属的店铺id
                        Repository.setLocalValue(LocalKey.USER_NAME, bean.getUserName()); // 系统生成的名称
                        Repository.setLocalValue(LocalKey.REAL_NAME, bean.getName()); // 用户真实姓名
                        Repository.setLocalValue(LocalKey.NICK_NAME, bean.getNickName()); // 用户昵称
                        Repository.setLocalValue(LocalKey.PICTURE_URL, bean.getAvatar()); // 用户头像
                    }
                    getView().onUpdate(Constant.RequestType.TYPE_1);
                }

                @Override
                public void onFailure(SimpleBean simpleBean) {
                    super.onFailure(simpleBean);
                }

                @Override
                public void onError(SimpleBean simpleBean) {
                    super.onError(simpleBean);
                }
            });
        }
    }
}
