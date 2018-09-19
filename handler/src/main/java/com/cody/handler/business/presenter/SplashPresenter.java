/*
 * Copyright (c)  Created by Cody.yi on 2016/8/31.
 */

package com.cody.handler.business.presenter;


import com.google.gson.JsonObject;
import com.cody.handler.R;
import com.cody.handler.business.mapper.UpdateMapper;
import com.cody.handler.business.viewmodel.SplashViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.business.bean.order.UpdateWrapBean;
import com.cody.repository.business.interaction.AppConfigInteraction;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.http.SimpleBean;

/**
 * Created by chy on 2016/
 * 例程 TODO
 */
public class SplashPresenter extends Presenter<SplashViewModel>  {

    private AppConfigInteraction mInteraction;
    private UpdateMapper mMapper;

    public SplashPresenter() {
        mInteraction = Repository.getInteraction(AppConfigInteraction.class);
        mMapper = new UpdateMapper();
    }

    public void checkVersion(final Object tag, final OnActionListener listener) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("fromObject", "longguo");
        jsonObject.addProperty("platform", "android");
        mInteraction.checkVersion(tag, jsonObject, UpdateWrapBean.class, new DefaultCallback<UpdateWrapBean>(this) {
            @Override
            protected int loadingMsg() {
                return R.string.h_check_version;
            }

            @Override
            public void onFailure(SimpleBean simpleBean) {
                super.onFailure(simpleBean);
                getView().onUpdate(tag);
            }

            @Override
            public void onError(SimpleBean simpleBean) {
                super.onError(simpleBean);
                getView().onUpdate(tag);
            }

            @Override
            public void onSuccess(UpdateWrapBean beans) {
                super.onSuccess(beans);
                if (beans != null && beans.data != null && beans.data.size() != 0) {
                    mMapper.mapper(getViewModel(), beans.data.get(0));
                    listener.onSuccess();
                } else {
                    getView().onUpdate(tag);
                }
            }
        });
    }
}
