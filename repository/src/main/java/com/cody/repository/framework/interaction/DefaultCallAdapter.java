package com.cody.repository.framework.interaction;

import com.cody.repository.framework.Repository;
import com.cody.repository.framework.local.BaseLocalKey;
import com.cody.xf.common.SimpleBean;
import com.cody.xf.utils.HttpUtil;

import java.util.Map;

/**
 * Created by cody.yi on 2017/3/29.
 * 默认请求适配器
 */
@SuppressWarnings("unchecked")
public class DefaultCallAdapter extends CallAdapter {

    public DefaultCallAdapter(InteractionMethod interactionMethod) {
        super(interactionMethod);
        if (interactionMethod == null) {
            throw new IllegalArgumentException("interactionMethod cannot be null.", null);
        }
    }

    @Override
    public void invokeSimple() {
        if (mMethod.getCallback() == null) return;

        mMethod.getCallback().onBegin(mMethod.getTag());
        Map<String, String> token = Repository.getLocalMap(BaseLocalKey.HEADERS);
        HttpUtil.getResult(mMethod.getTag(),
                mMethod.getRequestMethod().ordinal(),
                mMethod.getDomain() + mMethod.getUrl(),
                token,
                mMethod.getParams(), mMethod.getJsonObject(),
                mMethod.getHeaderListener(),
                new HttpUtil.Callback() {
                    @Override
                    public void onSuccess(Object data) {
                        mMethod.getCallback().onSuccess(data);
                    }

                    @Override
                    public void onFailure(SimpleBean result) {
                        mMethod.getCallback().onFailure(result);
                    }

                    @Override
                    public void onError(SimpleBean error) {
                        mMethod.getCallback().onError(error);
                    }
                });
    }

    @Override
    public void invokeOriginal() {
        if (mMethod.getCallback() == null) return;

        mMethod.getCallback().onBegin(mMethod.getTag());
        Map<String, String> token = Repository.getLocalMap(BaseLocalKey.HEADERS);
        HttpUtil.getOriginalResult(mMethod.getTag(),
                mMethod.getRequestMethod().ordinal(),
                mMethod.getDomain() + mMethod.getUrl(),
                token,
                mMethod.getParams(), mMethod.getJsonObject(),
                mMethod.getClazz(),
                mMethod.getHeaderListener(),
                new HttpUtil.Callback() {
                    @Override
                    public void onSuccess(Object data) {
                        mMethod.getCallback().onSuccess(data);
                    }

                    @Override
                    public void onFailure(SimpleBean result) {
                        mMethod.getCallback().onFailure(result);
                    }

                    @Override
                    public void onError(SimpleBean error) {
                        mMethod.getCallback().onError(error);
                    }
                });
    }

    @Override
    public void invokeBean() {
        if (mMethod.getCallback() == null) return;
        mMethod.getCallback().onBegin(mMethod.getTag());
        Map<String, String> token = Repository.getLocalMap(BaseLocalKey.HEADERS);

        HttpUtil.getData(mMethod.getTag(),
                mMethod.getRequestMethod().ordinal(),
                mMethod.getDomain() + mMethod.getUrl(),
                token, mMethod.getParams(),
                mMethod.getJsonObject(), mMethod.getClazz(),
                mMethod.getHeaderListener(),
                new HttpUtil.Callback() {
                    @Override
                    public void onSuccess(Object data) {
                        mMethod.getCallback().onSuccess(data);
                    }

                    @Override
                    public void onFailure(SimpleBean result) {
                        mMethod.getCallback().onFailure(result);
                    }

                    @Override
                    public void onError(SimpleBean error) {
                        mMethod.getCallback().onError(error);
                    }
                });
    }

    @Override
    public void invokeListBean() {
        if (mMethod.getCallback() == null) return;

        mMethod.getCallback().onBegin(mMethod.getTag());
        Map<String, String> token = Repository.getLocalMap(BaseLocalKey.HEADERS);
        HttpUtil.getListData(mMethod.getTag(),
                mMethod.getRequestMethod().ordinal(),
                mMethod.getDomain() + mMethod.getUrl(),
                token, mMethod.getParams(),
                mMethod.getJsonObject(), mMethod.getClazz(),
                mMethod.getHeaderListener(),
                new HttpUtil.Callback() {
                    @Override
                    public void onSuccess(Object data) {
                        mMethod.getCallback().onSuccess(data);
                    }

                    @Override
                    public void onFailure(SimpleBean result) {
                        mMethod.getCallback().onFailure(result);
                    }

                    @Override
                    public void onError(SimpleBean error) {
                        mMethod.getCallback().onError(error);
                    }
                });
    }
}
