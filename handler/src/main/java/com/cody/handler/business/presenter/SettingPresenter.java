/*
 * Copyright (c)  Created by Cody.yi on 2016/9/11.
 */

package com.cody.handler.business.presenter;


import com.cody.handler.business.viewmodel.SettingViewModel;
import com.google.gson.JsonObject;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.business.interaction.LogImInteraction;
import com.cody.repository.business.interaction.LoginNewInteraction;
import com.cody.repository.business.interaction.constant.Constant;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.xf.XFoundation;
import com.cody.xf.utils.http.SimpleBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cody.yi on 2016/9/22.
 * 用户信息
 */
public class SettingPresenter extends Presenter<SettingViewModel>{
    private LoginNewInteraction mLoginInteraction;
    private LogImInteraction mImInteraction;

    public SettingPresenter() {
        mLoginInteraction = Repository.getInteraction(LoginNewInteraction.class);
        mImInteraction = Repository.getInteraction(LogImInteraction.class);
    }

    public void online(Object tag, boolean online) {
        if (online) {
            Repository.setLocalInt(LocalKey.USER_STATUS, Constant.Status.online);
        } else {
            Repository.setLocalInt(LocalKey.USER_STATUS, Constant.Status.leave);
        }
        JsonObject params = new JsonObject();
        params.addProperty("imId", UserInfoManager.getImId());
        params.addProperty("userStatus", Repository.getLocalInt(LocalKey.USER_STATUS));
        mImInteraction.updateIMStatus(tag, params, SimpleBean.class, new ICallback<SimpleBean>() {
            @Override
            public void onBegin(Object tag) {

            }

            @Override
            public void onSuccess(SimpleBean bean) {

            }

            @Override
            public void onFailure(SimpleBean simpleBean) {
            }

            @Override
            public void onError(SimpleBean simpleBean) {

            }

            @Override
            public void onProgress(int progress, int max) {

            }
        });
    }

    public void tapLogout(Object tag) {
        if (getView() != null) {
            getView().showLoading(null);
            Map<String, String> params = new HashMap<>();
            params.put("appId", Constant.APP_ID);
            params.put("appSecret", Constant.APP_SECRET);
            doLogout(this, params);
        }
    }

    private void doLogout(final Object tag, final Map<String, String> params) {
        mLoginInteraction.doLogout(tag, params, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                XFoundation.getApp().onLogOutByUser();
            }
        });
    }
}
