package com.cody.handler.business.presenter;

import com.google.gson.JsonObject;
import com.cody.handler.business.viewmodel.MiniAppViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.business.bean.MinAppBean;
import com.cody.repository.business.bean.ShopDetailBean;
import com.cody.repository.business.interaction.MLongGuoInteraction;
import com.cody.repository.business.interaction.RetailInteraction;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;

/**
 * Created by cody.yi on 2018/8/21.
 * 微信小程序二维码
 */
public class MiniAppPresenter extends Presenter<MiniAppViewModel> {
    private RetailInteraction mRetailInteraction;
    private MLongGuoInteraction mMLongGuoInteraction;

    public MiniAppPresenter() {
        mRetailInteraction = Repository.getInteraction(RetailInteraction.class);
        mMLongGuoInteraction = Repository.getInteraction(MLongGuoInteraction.class);
    }

    public void getWxQrCode(String tag, final OnActionListener listener) {
        JsonObject params = new JsonObject();
        params.addProperty("type", "shop");
        params.addProperty("id", Repository.getLocalValue(LocalKey.SHOP_ID));
        mRetailInteraction.getWxQrCode(tag, params, MinAppBean.class, new DefaultCallback<MinAppBean>(this) {
            @Override
            public void onSuccess(MinAppBean bean) {
                super.onSuccess(bean);
                hideLoading();
                if (bean != null) {
                    getViewModel().setMiniAppUrl(bean.getImgStr());
                    getViewModel().setWapUrl(bean.getWapUrl());
                    getViewModel().setGid(bean.getGid());
                    getViewModel().setMiniPage(bean.getPage() + "?" + bean.getShareParam());
                    getViewModel().setShareEvn(bean.getShareEnv());
                }
                if (listener != null) {
                    listener.onSuccess();
                }
            }
        });
    }

    public void getShopDetail(String tag, final OnActionListener listener) {
        mMLongGuoInteraction.getShopDetail(tag, Repository.getLocalValue(LocalKey.SHOP_ID),
                ShopDetailBean.class, new DefaultCallback<ShopDetailBean>(this) {
                    @Override
                    public void onBegin(Object tag) {
                    }

                    @Override
                    public void onSuccess(ShopDetailBean bean) {
                        super.onSuccess(bean);
                        if (bean != null && listener != null) {
                            getViewModel().setShopPic(bean.getShopPic());
                            getViewModel().setShopName(bean.getShopName());
                            getViewModel().setShopAddress(bean.getAddress());
                            listener.onSuccess();
                        }
                    }
                });
    }
}
