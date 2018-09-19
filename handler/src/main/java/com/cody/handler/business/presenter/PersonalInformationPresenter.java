/*
 * Copyright (c)  Created by Cody.yi on 2016/9/11.
 */

package com.cody.handler.business.presenter;


import com.google.gson.JsonObject;
import com.cody.handler.R;
import com.cody.handler.business.mapper.PersonalInformationModelMapper;
import com.cody.handler.business.viewmodel.PersonalInformationViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.Domain;
import com.cody.repository.business.bean.UploadImageBean;
import com.cody.repository.business.interaction.LongInteraction;
import com.cody.repository.business.interaction.UploadInteraction;
import com.cody.repository.business.interaction.constant.Constant;
import com.cody.repository.business.interaction.constant.LongGuoUrl;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.HttpUtil;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.ResourceUtil;
import com.cody.xf.utils.http.SimpleBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cody.yi on 2016/9/11.
 * 用户信息
 */
public class PersonalInformationPresenter extends Presenter<PersonalInformationViewModel> {

    private UploadInteraction mUploadInteraction;
    private LongInteraction mInteraction;
    private PersonalInformationModelMapper mMapper = new PersonalInformationModelMapper();

    public PersonalInformationPresenter() {
        mUploadInteraction = Repository.getInteraction(UploadInteraction.class);
        mInteraction = Repository.getInteraction(LongInteraction.class);
    }

    public void upLoadUserHeadImage(Object tag, String name, String path) {
        uploadHeadImage(tag, path, Repository.getLocalValue(LocalKey.OPEN_ID));
    }

    public void getUserInfo(final Object tag) {
        if (getView() != null) {
            mMapper.mapper(getViewModel());
        }
    }

    public void modifySelfIntroduction(Object tag) {
        if (getView() != null) {
            JsonObject params = new JsonObject();
            params.addProperty("open_id", Repository.getLocalValue(LocalKey.OPEN_ID));
            params.addProperty("introduction", getViewModel().getSelfIntroduction().get());
            updateUserInfo(tag, params);
        }
    }

    /**
     * 上传头像
     */
    private void uploadHeadImage(final Object tag, String path, String openId) {
        if (getView() != null) {
            Map<String, String> params = new HashMap<>();
            if (getView() != null) {
                params.put("openId", Repository.getLocalValue(LocalKey.OPEN_ID));
            }
            getView().showLoading(ResourceUtil.getString(R.string.h_submit_loading));
            HttpUtil.uploadImageMultipart(tag, Domain.LONG_GUO_URL + LongGuoUrl.UPDATE_SALES_HEAD_PORTRAIT_URL, Constant.UPLOAD_FILES_NAME, path, Repository.getLocalMap
                            (LocalKey.X_TOKEN), params,
                    UploadImageBean.class, new HttpUtil.Callback<UploadImageBean>() {
                        @Override
                        public void onProgress(int progress, int max) {

                        }

                        @Override
                        public void onSuccess(UploadImageBean obj) {
                            if (getView() != null && getViewModel() != null) {
                                /*将图像修改后，获取到的头像url进行更新*/
                                Repository.setLocalValue(LocalKey.PICTURE_URL, obj.getPicUrl());
                                getView().onUpdate(Constant.RequestType.TYPE_1, obj.getPicUrl());
                            }
                        }

                        @Override
                        public void onFailure(SimpleBean result) {
                            LogUtil.d(tag.toString(), "uploadImage onFailure =" + result.toString());
                        }

                        @Override
                        public void onError(SimpleBean simpleBean) {
                            LogUtil.d(tag.toString(), "uploadImage onError =" + simpleBean.toString());
                        }
                    });
           /* mUploadInteraction.uploadFile(tag, path, "longGuoApp", ImageBean.class, new
                    DefaultSubmitCallback<ImageBean>(this) {
                        @Override
                        public void onSuccess(ImageBean obj) {
                            super.onSuccess(obj);
                            if (getView() != null && getViewModel() != null) {
                                *//*将图像修改后，获取到的头像url进行更新*//*
                                Repository.setLocalValue(LocalKey.PICTURE_URL, obj.getFileUrl());
                                getView().onUpdate(Constant.RequestType.TYPE_1, obj.getFileUrl());
                            }
                        }
                    });*/
        }
    }

    /**
     * 更新用户信息
     */
    private void updateUserInfo(final Object tag, JsonObject params) {
        if (getView() != null) {
            mInteraction.updateUserInfo(tag, params, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
                @Override
                public void onSuccess(SimpleBean bean) {
                    super.onSuccess(bean);
                    if (getView() != null) {
                        getView().onUpdate(tag);
                    }
                }
            });
        }
    }
}
