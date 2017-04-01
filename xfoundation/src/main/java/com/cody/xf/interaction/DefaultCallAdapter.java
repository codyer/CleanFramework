package com.cody.xf.interaction;

import com.cody.xf.common.SimpleBean;
import com.cody.xf.utils.HttpUtil;

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
        HttpUtil.getResult(mMethod.getTag(),
                mMethod.getRequestMethod().ordinal(),
                mMethod.getDomain() + mMethod.getUrl(),
                mMethod.getParams(), mMethod.getJsonObject(),
                mMethod.getHeaderListener(), new HttpUtil.Callback() {
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
        HttpUtil.getOriginalResult(mMethod.getTag(),
                mMethod.getRequestMethod().ordinal(),
                mMethod.getDomain() + mMethod.getUrl(),
                mMethod.getParams(), mMethod.getJsonObject(),
                mMethod.getClazz(),
                mMethod.getHeaderListener(), new HttpUtil.Callback() {
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
        HttpUtil.getData(mMethod.getTag(),
                mMethod.getRequestMethod().ordinal(),
                mMethod.getDomain() + mMethod.getUrl(),
                mMethod.getParams(), mMethod.getJsonObject(),
                mMethod.getClazz(),
                mMethod.getHeaderListener(), new HttpUtil.Callback() {
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
        HttpUtil.getListData(mMethod.getTag(),
                mMethod.getRequestMethod().ordinal(),
                mMethod.getDomain() + mMethod.getUrl(),
                mMethod.getParams(), mMethod.getJsonObject(),
                mMethod.getClazz(),
                mMethod.getHeaderListener(), new HttpUtil.Callback() {
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
